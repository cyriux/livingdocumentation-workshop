/**
 * 
 */
package flottio.annotations;

/**
 * The list of all principles the team agrees on.
 */
public enum Codex {

	/**
	 * We have no clue to explain this decision
	 */
	NO_CLUE("Nobody"),

	/**
	 * There must be only one single authoritative place for each piece of data
	 */
	SINGLE_GOLDEN_SOURCE("Team"),

	/** Keep your middleware dumb, and keep the smarts in the endpoints. */
	DUMP_MIDDLEWARE("Sam Newman");

	private final String author;

	private Codex(String author) {
		this.author = author;
	}
}
