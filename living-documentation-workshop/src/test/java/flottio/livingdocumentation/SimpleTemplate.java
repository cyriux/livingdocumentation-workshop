package flottio.livingdocumentation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SimpleTemplate {

	public static String evaluate(final String template, String title, String content) {
		return template.replace("{0}", title).replace("{1}", content);
	}

	/**
	 * @return A String that represents the content of the file
	 */
	public static String readTestResource(final String filename) {
		String lineSep = String.format("%n");
		final StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					DummyTemplatingTest.class.getResourceAsStream(filename)));
			String str = null;
			while ((str = in.readLine()) != null) {
				buffer.append(lineSep);
				buffer.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static void write(String path, String filename, String content) throws UnsupportedEncodingException,
			FileNotFoundException {
		final String outputFileName = path + filename;
		final String outputEncoding = "ISO-8859-1";
		final FileOutputStream fos = new FileOutputStream(outputFileName);
		final PrintWriter w = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, outputEncoding)));

		w.println(content);
		w.flush();
		w.close();
	}

}
