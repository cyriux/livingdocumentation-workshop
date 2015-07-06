package flottio.livingdocumentation;

import static flottio.livingdocumentation.SimpleTemplate.evaluate;
import static flottio.livingdocumentation.SimpleTemplate.readTestResource;
import static flottio.livingdocumentation.SimpleTemplate.write;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.junit.Ignore;
import org.junit.Test;

public class DummyTemplatingTest {

	@Test
	public void testMarkdownTemplate() throws UnsupportedEncodingException, FileNotFoundException {
		final String template = readTestResource("strapdown-template.html");

		String title = "Living Glossary";
		String content = "# Big Title \n ## Second title \n \n sample text goes here bla blab...";
		final String text = MessageFormat.format(template, new Object[] { title, content });
		write("", "livingglossary.html", text);
	}

	@Test
	public void testGraphvizTemplate() throws UnsupportedEncodingException, FileNotFoundException {
		final String template = readTestResource("viz-template.html");

		String title = "Living Diagram";
		String content = "digraph G {a -> b; b -> c; c -> a;}";
		final String text = evaluate(template, title, content);
		write("", "livinggdiagram.html", text);
	}

	@Test
	@Ignore
	public void testWordCloudTemplate() throws UnsupportedEncodingException, FileNotFoundException {
		final String template = readTestResource("wordcloud-template.html");

		String title = "Word Cloud";
		String content = "{\"text\": \"Cat\", \"size\": 26}, {\"text\": \"Dog\", \"size\": 75}, ";
		final String text = evaluate(template, title, content);
		write("", "wordcloud.html", text);
	}

}
