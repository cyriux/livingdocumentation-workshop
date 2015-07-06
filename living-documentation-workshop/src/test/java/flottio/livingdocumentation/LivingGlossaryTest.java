package flottio.livingdocumentation;

import static flottio.livingdocumentation.SimpleTemplate.readTestResource;
import static flottio.livingdocumentation.SimpleTemplate.write;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;

public class LivingGlossaryTest {

	private static final String CONTEXT_PREFIX = "flottio.dispatching";

	private static final String ANNOTATION_PREFIX = "flottio.annotation";

	private PrintWriter writer;

	@Test
	public void test() {
		try {
			// writer = new PrintWriter("glossary.txt");
			// writer = new PrintWriter(System.out);
			final StringWriter out = new StringWriter();
			writer = new PrintWriter(out);

			writer.println("# " + "Glossary");

			JavaProjectBuilder builder = new JavaProjectBuilder();
			// Adding all .java files in a source tree (recursively).
			builder.addSourceTree(new File("src/main/java"));
			printAll(builder);
			writer.close();

			final String template = readTestResource("strapdown-template.html");

			String title = "Living Glossary";
			String content = out.toString();
			final String text = MessageFormat.format(template, new Object[] { title, content });
			write("", "livingglossary.html", text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printAll(JavaProjectBuilder builder) {
		final Map<JavaPackage, JavaAnnotation> inventory = boundedContextInventory(builder);
		for (Entry<JavaPackage, JavaAnnotation> entry : inventory.entrySet()) {
			final JavaPackage pckge = entry.getKey();
			final JavaAnnotation bc = entry.getValue();
			printOneBoundedContext(pckge, bc);
		}
	}

	/**
	 * Creates an inventory of every package that defines a Bounded Context
	 */
	private static Map<JavaPackage, JavaAnnotation> boundedContextInventory(JavaProjectBuilder builder) {
		final Map<JavaPackage, JavaAnnotation> contexts = new HashMap<JavaPackage, JavaAnnotation>();
		for (JavaPackage p : builder.getPackages()) {
			final JavaAnnotation boundedContext = boundedContextAnnotation(p);
			if (boundedContext != null) {
				contexts.put(p, boundedContext);
			}
		}
		return contexts;
	}

	/**
	 * Extracts the Bounded Context Annotation of the given package, or null if
	 * there's none
	 */
	private static JavaAnnotation boundedContextAnnotation(JavaPackage pkge) {
		for (JavaAnnotation annotation : pkge.getAnnotations()) {
			final JavaClass type = annotation.getType();
			if (type.getFullyQualifiedName().endsWith("BoundedContext")) {
				return annotation;
			}
		}
		return null;
	}

	private void printOneBoundedContext(final JavaPackage pckge, final JavaAnnotation bc) {
		if (pckge.getName().startsWith(CONTEXT_PREFIX)) {
			return;
		}
		printContextDetails(pckge, bc);
		for (JavaClass clss : pckge.getClasses()) {
			if (isBusinessMeaningful(clss)) {
				process(clss);
			}
		}
	}

	private boolean isBusinessMeaningful(JavaClass doc) {
		for (JavaAnnotation annotation : doc.getAnnotations()) {
			if (annotation.getType().getPackageName().startsWith(ANNOTATION_PREFIX)) {
				return true;
			}
		}
		return false;
	}

	private void printContextDetails(JavaPackage pckge, JavaAnnotation bc) {
		println();
		final String bcName = (String) bc.getNamedParameter("name");
		writer.println("## " + bcName.trim().replaceAll("\"", ""));
		println();
		printComment(pckge.getComment());
		final Object link = bc.getNamedParameter("link");
		if (link != null) {
			println();
			printListItem("See: " + link);
		}
		println();
	}

	protected void println() {
		writer.println("");
	}

	protected void printTitle(final String title) {
		writer.println("### " + title);
	}

	protected void printComment(final String comment) {
		writer.println(comment);
	}

	protected void printListItem(final String bullet) {
		writer.println("- " + bullet);
	}

	protected void printListItem(final String name, String comment) {
		writer.println("- *" + name + "*: " + comment);
	}

	protected void process(JavaClass clss) {
		println();
		printTitle(clss.getName());
		printComment(clss.getComment());
		if (clss.isEnum()) {
			println();
			for (JavaField field : clss.getEnumConstants()) {
				// printEnumConstant(field);
			}
			println();
			for (JavaMethod method : clss.getMethods(false)) {
				// printMethod(method);
			}
		} else if (clss.isInterface()) {
			println();
			for (JavaClass subClass : clss.getDerivedClasses()) {
				// printSubClass(subClass);
			}
		} else {
			println();
			for (JavaField field : clss.getFields()) {
				// printField(field);
			}
			println();
			for (JavaMethod method : clss.getMethods(false)) {
				// printMethod(method);
			}
		}
	}
}
