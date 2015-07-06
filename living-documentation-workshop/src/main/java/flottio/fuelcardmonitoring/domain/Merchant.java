/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import flottio.annotations.ValueObject;

/**
 * A merchant (gas station...) with its name and address
 */
@ValueObject
public class Merchant {

	private final String name;
	private final String address;

	public Merchant(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		return address.hashCode() ^ name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Merchant)) {
			return false;
		}
		Merchant other = (Merchant) obj;
		return address.equals(other.address) && name.equals(other.name);
	}

	@Override
	public String toString() {
		return "Merchant [name=" + name + ", address=" + address + "]";
	}

}
