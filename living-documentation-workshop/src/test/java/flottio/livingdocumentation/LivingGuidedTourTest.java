package flottio.livingdocumentation;

import static flottio.livingdocumentation.SimpleTemplate.readTestResource;
import static flottio.livingdocumentation.SimpleTemplate.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotatedElement;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;

public class LivingGuidedTourTest {

	private static final String SEP = "\n\n";

	private static final String CONTEXT_PREFIX = "flottio.fuelcardmonitoring";

	private static final String ANNOTATION_NAME = "flottio.annotations.GuidedTour";
	private static final String REPO_LINKS_PREFIX = "https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop";

	private PrintWriter writer;

	private final Map<String, Tour> tours = new HashMap<String, Tour>();

	private static class Tour {
		private final SortedMap<Integer, String> sites = new TreeMap<Integer, String>();

		public String put(int step, String describtion) {
			return sites.put(step, describtion);
		}

		@Override
		public String toString() {
			return sites.toString();
		}

	}

	private static class TourStep {
		private final String name;
		private final String description;
		private final int step;

		public String name() {
			return name;
		}

		public String description() {
			return description;
		}

		public int step() {
			return step;
		}

		public TourStep(String name, String description, int step) {
			this.name = name;
			this.description = description;
			this.step = step;
		}

	}

	@Test
	public void test() {
		try {
			JavaProjectBuilder builder = new JavaProjectBuilder();
			// Adding all .java files in a source tree (recursively).
			builder.addSourceTree(new File("src/main/java"));
			printAll(builder);

			final String template = readTestResource("strapdown-template.html");
			for (String tourName : tours.keySet()) {
				writeSightSeeingTour(tourName, template);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeSightSeeingTour(String tourName, final String template) throws UnsupportedEncodingException,
			FileNotFoundException {
		final StringWriter out = new StringWriter();
		writer = new PrintWriter(out);
		final Tour tour = tours.get(tourName);
		int count = 1;
		for (String step : tour.sites.values()) {
			writer.println(SEP);
			writer.println("## " + count++ + ". " + step);
		}
		String title = tourName;
		String content = out.toString();
		final String text = MessageFormat.format(template, new Object[] { title, content });
		write("", tourName.replaceAll(" ", "_") + ".html", text);
	}

	private void printAll(JavaProjectBuilder builder) {
		for (JavaPackage p : builder.getPackages()) {
			if (!p.getName().startsWith(CONTEXT_PREFIX)) {
				continue;
			}
			final TourStep step = getQuickDevTourStep(p);
			if (step != null) {
				// process(p);
			}
		}
		for (JavaClass c : builder.getClasses()) {
			if (!c.getPackageName().startsWith(CONTEXT_PREFIX)) {
				continue;
			}
			process(c);
		}

	}

	private TourStep getQuickDevTourStep(JavaAnnotatedElement doc) {
		for (JavaAnnotation annotation : doc.getAnnotations()) {
			if (annotation.getType().getFullyQualifiedName().equals(ANNOTATION_NAME)) {
				final String tourName = (String) annotation.getNamedParameter("name");
				final String step = (String) annotation.getNamedParameter("rank");
				final String desc = (String) annotation.getNamedParameter("description");
				return new TourStep(tourName.replaceAll("\"", ""), desc.replaceAll("\"", ""), Integer.valueOf(step));
			}
		}
		return null;
	}

	protected String title(final String title) {
		return "\n### " + title;
	}

	protected String listItem(final String bullet) {
		return "- " + bullet;
	}

	protected String link(final String name, String url) {
		return "[" + name + "](" + url + ")";
	}

	protected String linkSrcJava(final String name, String qName, int lineNumber) {
		return link(name, REPO_LINKS_PREFIX + "/src/main/java/" + qName.replace('.', '/') + ".java#L" + lineNumber);
	}

	protected void process(JavaClass c) {
		final String comment = blockQuote(c.getComment());
		addTourStep(getQuickDevTourStep(c), c.getName(), c.getFullyQualifiedName(), comment, c.getLineNumber());

		if (c.isEnum()) {
			for (JavaField field : c.getEnumConstants()) {
				// printEnumConstant(field);
			}
			for (JavaMethod method : c.getMethods(false)) {
				//
			}
		} else if (c.isInterface()) {
			for (JavaClass subClass : c.getDerivedClasses()) {
				// printSubClass(subClass);
			}
		} else {
			for (JavaField field : c.getFields()) {
				// printField(field);
			}
			for (JavaMethod m : c.getMethods(false)) {
				final String name = m.getCallSignature();
				final String qName = c.getFullyQualifiedName();
				final String codeBlock = code(m.getCodeBlock());
				final int lineNumber = m.getLineNumber();
				final TourStep step = getQuickDevTourStep(m);
				addTourStep(step, name, qName, codeBlock, lineNumber);

			}
		}
	}

	private String code(String codeBlock) {
		return "\n```\n" + codeBlock + "\n```";
	}

	private String blockQuote(String quote) {
		return quote == null ? "" : "> " + quote.replaceAll("\n", "\n> ");
	}

	private void addTourStep(final TourStep step, final String name, final String qName, final String comment,
			final int lineNumber) {
		if (step != null) {
			final StringBuilder content = new StringBuilder();
			// content.append(name);
			content.append(linkSrcJava(name, qName, lineNumber));
			if (step.description() != null) {
				content.append(SEP);
				content.append("*" + step.description().trim() + "*");
			}
			if (comment != null) {
				content.append(SEP);
				content.append(comment);
			}
			content.append(SEP);

			getTourNamed(step.name()).put(step.step(), content.toString());
		}
	}

	private Tour getTourNamed(String name) {
		Tour tour = tours.get(name);
		if (tour == null) {
			tour = new Tour();
			tours.put(name, tour);
		}
		return tour;
	}
}
