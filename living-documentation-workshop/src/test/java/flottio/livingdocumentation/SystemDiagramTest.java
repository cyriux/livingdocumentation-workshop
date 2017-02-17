package flottio.livingdocumentation;

import static flottio.livingdocumentation.SimpleTemplate.evaluate;
import static flottio.livingdocumentation.SimpleTemplate.readTestResource;
import static flottio.livingdocumentation.SimpleTemplate.write;
import static org.livingdocumentation.dotdiagram.DotStyles.NOTE_EDGE_STYLE;
import static org.livingdocumentation.dotdiagram.DotStyles.STUB_NODE_OPTIONS;

import java.io.File;
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
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotatedElement;

import flottio.annotations.BoundedContext;
import flottio.annotations.ExternalActor;
import flottio.annotations.ExternalActor.ActorType;

/**
 * Living Diagram of the system and its external actors generated out of the
 * code thanks to the package naming conventions.
 */
public class SystemDiagramTest {

	private static final String SCM_BASE_URL = "https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop";
	private static final String PACKAGE_PREFIX = "flottio.fuelcardmonitoring";
	private static final String SOURCE_TREE = "src/main/java";
	private final DotGraph graph = new DotGraph("", "LR");

	@Test
	public void generateDiagram() throws Exception {
		final ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

		final JavaProjectBuilder builder = new JavaProjectBuilder();
		// Adding all .java files in a source tree (recursively).
		builder.addSourceTree(new File(SOURCE_TREE));

		final String prefix = PACKAGE_PREFIX;
		final ImmutableSet<ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(prefix);

		final Digraph digraph = graph.getDigraph();
		digraph.setOptions("rankdir=LR");

		final String domainPackageName = prefix + "." + "domain";

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
			final String systemName = bc.name().trim() + " System";
			// final String systemPicture = firstImageIn(bc.links());
			digraph.addNode("system").setLabel(wrap(systemName, 19)).setComment("the system under consideration")
					.setOptions(STUB_NODE_OPTIONS);

			final Stream<ClassInfo> infra = allClasses.stream().filter(notIn("domain"));
			infra.forEach(new Consumer<ClassInfo>() {
				public void accept(ClassInfo ci) {
					printActor(digraph, ci, builder);
				}
			});
		}

		// render into image
		final String template = readTestResource("viz-template.html");

		String title = "Context Diagram";
		final String content = graph.render().trim();
		final String text = evaluate(template, title, content);
		write("", "context-diagram.html", text);
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

	private final static String wrap(String words, final int length) {
		final StringBuilder sb = new StringBuilder(words);
		int i = 0;
		while (i + length < sb.length() && (i = sb.lastIndexOf(" ", i + length)) != -1) {
			sb.replace(i, i + 1, "\n");
		}
		return sb.toString();
	}

	protected void printActor(Digraph digraph, ClassInfo ci, JavaProjectBuilder builder) {
		// final String options =
		// "shape=box, style=invis, shapefile=\"Turing.png\"";
		final String url = ", URL=\"" + (SCM_BASE_URL + "/" + SOURCE_TREE + "/") + toPath(ci) + "\"";
		final String options = "shape=box, style=filled,fillcolor=\"#C0D0C0\"" + url;

		final ExternalActor[] actors = ci.load().getAnnotationsByType(ExternalActor.class);
		for (ExternalActor actor : actors) {
			digraph.addNode(ci.getName()).setLabel(wrap(actor.name(), 19)).setComment(ci.getSimpleName())
					.setOptions(options); // .addStereotype(actorType(actor.type()))

			final String label = getComment(ci, builder);
			switch (actor.direction()) {
			case API:
				digraph.addAssociation(ci.getName(), "system").setLabel(label).setOptions(NOTE_EDGE_STYLE);
				break;
			case SPI:
				digraph.addAssociation("system", ci.getName()).setLabel(label).setOptions(NOTE_EDGE_STYLE);
				break;
			default:
				digraph.addAssociation("system", ci.getName()).setLabel(label).setOptions(NOTE_EDGE_STYLE);
				digraph.addAssociation(ci.getName(), "system").setLabel(label).setOptions(NOTE_EDGE_STYLE);
			}
		}
	}

	public String toPath(ClassInfo ci) {
		return ci.getResourceName().replace(".class", ".java").replace("package-info.java", "");
	}

	private final static String actorType(ActorType type) {
		switch (type) {
		case PEOPLE:
			return "External User";
		case SYSTEM:
		default:
			return "External System";
		}
	}

	private String getComment(ClassInfo ci, JavaProjectBuilder builder) {
		JavaAnnotatedElement doc = ci.getSimpleName().equals("package-info")
				? builder.getPackageByName(ci.getPackageName()) : builder.getClassByName(ci.getName());
		final String label = doc.getComment() == null ? "" : wrap(doc.getComment(), 30);
		return label;
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
