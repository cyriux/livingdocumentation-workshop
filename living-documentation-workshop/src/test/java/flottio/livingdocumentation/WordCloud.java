package flottio.livingdocumentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class WordCloud {

	// keywords to be ignored
	private static final String[] KEYWORDS = { "abstract", "continue", "for", "new", "switch", "assert", "default",
			"if", "package", "synchronized", "boolean", "do", "goto", "private", "this", "break", "double",
			"implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum",
			"instanceof", "return", "transient", "catch", "extends", "int", "", "short", "try", "char", "final",
			"interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float",
			"native", "super", "while" };
	
	// lower-case words to be ignored
	private static final String[] STOPWORDS = { "the", "it","is", "to", "with", "what's", "by", "or", "and", "both", "be", "of",
			"in", "obj", "string", "hashcode", "equals", "other", "tostring", "false", "true", "object", "annotations" };
	private static final Set<String> ignore = initialize();

	private final static Set<String> initialize() {
		final Set<String> ignore = new HashSet<String>();
		ignore.addAll(Arrays.asList(STOPWORDS));
		ignore.addAll(Arrays.asList(KEYWORDS));
		return ignore;
	}

	private final Multiset<String> bag = HashMultiset.create();
	private int max = 0;

	public void scan(final String sourceFolder) throws IOException {
		scan(new File(sourceFolder));
	}

	public Multiset<String> getBag() {
		return bag;
	}

	public int getMax() {
		return max;
	}

	private void scan(final File f) throws IOException {
		final File[] listOfFiles = f.listFiles();
		for (File file : listOfFiles) {
			if (file.isDirectory() && !file.getName().endsWith("annotations")) {
				scan(file);
			}
			if (file.isFile() && file.getName().endsWith(".java")) {
				final String content = new String(Files.readAllBytes(file.toPath()));
				filter(content);
			}
		}
	}

	private void filter(final String content) {
		final StringTokenizer st = new StringTokenizer(content, ";:.,?!<><=+-^&|*/\"\t\r\n {}[]()");
		while (st.hasMoreElements()) {
			final String token = (String) st.nextElement();
			if (isMeaningful(token.trim().toLowerCase())) {
				bag.add(token.trim().toLowerCase());
				final int count = bag.count(token);
				max = Math.max(max, count);
			}
		}
	}

	private static boolean isMeaningful(String token) {
		if (token.length() <= 1) {
			return false;
		}
		if (token.startsWith("@")) {
			return false;
		}
		if (Character.isDigit(token.charAt(0))) {
			return false;
		}
		if (ignore.contains(token)) {
			return false;
		}
		return true;
	}

}
