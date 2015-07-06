package flottio.livingdocumentation;

import static flottio.livingdocumentation.SimpleTemplate.evaluate;
import static flottio.livingdocumentation.SimpleTemplate.readTestResource;
import static flottio.livingdocumentation.SimpleTemplate.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

public class WordCloudTest {

	@Test
	public void generateWordCloud() throws IOException {
		//final String sourceFolder = "/src/main/java/flottio/dispatching";
		final String sourceFolder = "/src/main/java/flottio/fuelcardmonitoring";
		generateWordCloud(baseDir() + sourceFolder, "wordcloud.html");
	}

	public String baseDir() {
		return new File("").getAbsolutePath();
	}

	public void generateWordCloud(final String sourceFolder, String outputFileName) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		final WordCloud wordCloud = new WordCloud();
		wordCloud.scan(sourceFolder);

		final Multiset<String> bag = wordCloud.getBag();
		final int max = wordCloud.getMax();
		final double scaling = 50. / max;

		final String template = readTestResource("wordcloud-template.html");

		String title = "Word Cloud";
		String content = toJSON(bag, scaling);

		final String text = evaluate(template, title, content);
		write("", outputFileName, text);
	}

	public static String toJSON(final Multiset<String> bag, double scaling) {
		final StringBuilder sb = new StringBuilder();
		for (Entry<String> entry : bag.entrySet()) {
			sb.append("{\"text\": \"" + entry.getElement() + "\", \"size\": " + scaling * entry.getCount() + "}");
			sb.append(", ");
		}
		return sb.toString();
	}

}
