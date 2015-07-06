package flottio.livingdocumentation;

import static flottio.livingdocumentation.SimpleTemplate.evaluate;
import static flottio.livingdocumentation.SimpleTemplate.readTestResource;
import static flottio.livingdocumentation.SimpleTemplate.write;
import static org.livingdocumentation.dotdiagram.DotStyles.ASSOCIATION_EDGE_STYLE;
import static org.livingdocumentation.dotdiagram.DotStyles.IMPLEMENTS_EDGE_STYLE;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;
import org.livingdocumentation.dotdiagram.DotGraph;
import org.livingdocumentation.dotdiagram.DotGraph.Cluster;
import org.livingdocumentation.dotdiagram.DotGraph.Digraph;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

/**
 * Living Diagram of the Hexagonal Architecture generated out of the code thanks
 * to the package naming conventions.
 */
public class LivingDiagramTest {

	private final DotGraph graph = new DotGraph("Hexagonal Architecture", "LR");

	@Test
	public void generateDiagram() throws Exception {
		final ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

		final String prefix = "flottio.fuelcardmonitoring";
		final ImmutableSet<ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(prefix);

		final Digraph digraph = graph.getDigraph();
		digraph.setOptions("rankdir=LR");

		Stream<ClassInfo> domain = allClasses.stream().filter(filter(prefix, "domain"));
		final Cluster core = digraph.addCluster("hexagon");
		core.setLabel("Core Domain");

		// add all domain model elements first
		domain.forEach(new Consumer<ClassInfo>() {
			public void accept(ClassInfo ci) {
				final Class clazz = ci.load();
				core.addNode(clazz.getName()).setLabel(clazz.getSimpleName()).setComment(clazz.getSimpleName());
			}
		});

		Stream<ClassInfo> infra = allClasses.stream().filter(filterNot(prefix, "domain"));
		infra.forEach(new Consumer<ClassInfo>() {
			public void accept(ClassInfo ci) {
				final Class clazz = ci.load();
				digraph.addNode(clazz.getName()).setLabel(clazz.getSimpleName()).setComment(clazz.getSimpleName());
			}
		});
		infra = allClasses.stream().filter(filterNot(prefix, "domain"));
		infra.forEach(new Consumer<ClassInfo>() {
			public void accept(ClassInfo ci) {
				final Class clazz = ci.load();
				// API
				for (Field field : clazz.getDeclaredFields()) {
					final Class<?> type = field.getType();
					if (!type.isPrimitive()) {
						digraph.addExistingAssociation(clazz.getName(), type.getName(), null, null,
								ASSOCIATION_EDGE_STYLE);
					}
				}

				// SPI
				for (Class intf : clazz.getInterfaces()) {
					digraph.addExistingAssociation(intf.getName(), clazz.getName(), null, null, IMPLEMENTS_EDGE_STYLE);
				}
			}
		});

		// then wire them together
		domain = allClasses.stream().filter(filter(prefix, "domain"));
		domain.forEach(new Consumer<ClassInfo>() {
			public void accept(ClassInfo ci) {
				final Class clazz = ci.load();
				for (Field field : clazz.getDeclaredFields()) {
					final Class<?> type = field.getType();
					if (!type.isPrimitive()) {
						digraph.addExistingAssociation(clazz.getName(), type.getName(), null, null,
								ASSOCIATION_EDGE_STYLE);
					}
				}

				for (Class intf : clazz.getInterfaces()) {
					digraph.addExistingAssociation(intf.getName(), clazz.getName(), null, null, IMPLEMENTS_EDGE_STYLE);
				}
			}
		});

		// render into image
		final String template = readTestResource("viz-template.html");

		String title = "Living Diagram";
		final String content = graph.render().trim();
		final String text = evaluate(template, title, content);
		write("", "livinggdiagram.html", text);
	}

	private Predicate<ClassInfo> filter(final String prefix, final String layer) {
		return new Predicate<ClassInfo>() {
			public boolean test(ClassInfo ci) {
				final boolean nameConvention = ci.getPackageName().startsWith(prefix)
						&& !ci.getSimpleName().endsWith("Test") && !ci.getSimpleName().endsWith("IT")
						&& ci.getPackageName().endsWith("." + layer);
				return nameConvention;
			}

		};
	}

	private Predicate<ClassInfo> filterNot(final String prefix, final String layer) {
		return new Predicate<ClassInfo>() {
			public boolean test(ClassInfo ci) {
				final boolean nameConvention = ci.getPackageName().startsWith(prefix)
						&& !ci.getSimpleName().endsWith("Test") && !ci.getSimpleName().endsWith("IT")
						&& !ci.getPackageName().endsWith("." + layer);
				return nameConvention;
			}

		};
	}
}
