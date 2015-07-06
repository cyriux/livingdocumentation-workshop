package flottio.livingdocumentation;

import static flottio.livingdocumentation.SimpleTemplate.evaluate;
import static flottio.livingdocumentation.SimpleTemplate.readTestResource;
import static flottio.livingdocumentation.SimpleTemplate.write;
import static org.livingdocumentation.dotdiagram.DotStyles.NOTE_EDGE_STYLE;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;
import org.livingdocumentation.dotdiagram.DotGraph;
import org.livingdocumentation.dotdiagram.DotGraph.Digraph;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import flottio.annotations.BoundedContext;
import flottio.annotations.ExternalActor;

/**
 * Living Diagram of the system and its external actors generated out of the
 * code thanks to the package naming conventions.
 */
public class SystemDiagramTest {

	private final DotGraph graph = new DotGraph("System Diagram", "LR");

	@Test
	public void generateDiagram() throws Exception {
		final ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

		final String prefix = "flottio.fuelcardmonitoring";
		final ImmutableSet<ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(prefix);

		final Digraph digraph = graph.getDigraph();
		digraph.setOptions("rankdir=LR");

		final String domainPackageName = prefix + ".domain";

		final ImmutableSet<ClassInfo> domain = classPath.getTopLevelClasses(domainPackageName);
		final Map<ClassInfo, BoundedContext> inventory = new HashMap<ClassPath.ClassInfo, BoundedContext>();
		for (ClassInfo ci : domain) {
			BoundedContext bc = findBoundedContext(ci);
			if (bc != null) {
				inventory.put(ci, bc);
			}
		}

		for (ClassInfo classInfo : inventory.keySet()) {
			final BoundedContext bc = inventory.get(classInfo);
			final String systemName = bc.name();
			final String systemPicture = firstImageIn(bc.links());
			digraph.addNode("system").setLabel(systemName + "\n" + systemPicture)
					.setComment("the system under consideration").addStereotype("System");

			final Stream<ClassInfo> infra = allClasses.stream().filter(notIn("domain"));
			infra.forEach(new Consumer<ClassInfo>() {
				public void accept(ClassInfo ci) {
					printActor(digraph, ci);
				}
			});
		}

		// render into image
		final String template = readTestResource("viz-template.html");

		String title = "System Diagram";
		final String content = graph.render().trim();
		final String text = evaluate(template, title, content);
		write("", "systemdiagram.html", text);
	}

	private static String firstImageIn(String[] strings) {
		for (String link : strings) {
			if (link.toLowerCase().endsWith(".png")) {
				return link;
			}
		}
		return null;
	}

	private BoundedContext findBoundedContext(ClassInfo ci) {
		BoundedContext first = null;
		final BoundedContext[] bc = ci.load().getAnnotationsByType(BoundedContext.class);
		for (BoundedContext boundedContext : bc) {
			first = boundedContext;
		}
		return first;
	}

	protected void printActor(Digraph digraph, ClassInfo ci) {
		final ExternalActor[] actors = ci.load().getAnnotationsByType(ExternalActor.class);
		for (ExternalActor actor : actors) {
			digraph.addNode(ci.getName()).setLabel(actor.name()).setComment(ci.getSimpleName())
					.addStereotype("External Actor");
			switch (actor.direction()) {
			case API:
				digraph.addAssociation(ci.getName(), "system").setLabel("").setOptions(NOTE_EDGE_STYLE);
				break;
			case SPI:
				digraph.addAssociation("system", ci.getName()).setLabel("").setOptions(NOTE_EDGE_STYLE);
				break;
			default:
				digraph.addAssociation("system", ci.getName()).setLabel("").setOptions(NOTE_EDGE_STYLE);
				digraph.addAssociation(ci.getName(), "system").setLabel("").setOptions(NOTE_EDGE_STYLE);
			}
		}
	}

	private Predicate<ClassInfo> notIn(final String packageName) {
		return new Predicate<ClassInfo>() {
			public boolean test(ClassInfo ci) {
				final boolean nameConvention = !ci.getSimpleName().endsWith("Test")
						&& !ci.getSimpleName().endsWith("IT") && !ci.getPackageName().endsWith("." + packageName);
				return nameConvention;
			}

		};
	}
}
