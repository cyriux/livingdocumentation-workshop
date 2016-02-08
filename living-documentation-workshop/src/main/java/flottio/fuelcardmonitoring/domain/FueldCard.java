package flottio.fuelcardmonitoring.domain;

import flottio.annotations.CoreConcept;
import flottio.annotations.ValueObject;

/**
 * A fuel card with its type, id, holder name
 */
@ValueObject
@CoreConcept
public class FueldCard {

	private final String id;
	private final String name;
	private final String type;

	public FueldCard(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return id.hashCode() + name.hashCode() ^ type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FueldCard)) {
			return false;
		}
		FueldCard other = (FueldCard) obj;
		return other.id.equals(other.id) && other.name.equals(other.name) && other.type.equals(other.type);
	}

	@Override
	public String toString() {
		return "FueldCard [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

}
