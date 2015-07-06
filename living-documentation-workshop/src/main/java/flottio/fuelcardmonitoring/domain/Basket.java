/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import flottio.annotations.ValueObject;

/**
 * What's being purchased
 */
@ValueObject
public class Basket {
	private final double fuelQuantity;
	private final Money priceByLitre;

	public Basket(double fuelQuantity, Money priceByLitre) {
		this.fuelQuantity = fuelQuantity;
		this.priceByLitre = priceByLitre;
	}

	public double getFuelQuantity() {
		return fuelQuantity;
	}

	public Money getPriceByLitre() {
		return priceByLitre;
	}

	@Override
	public int hashCode() {
		return (int) (Double.doubleToLongBits(fuelQuantity) >>> 32 ^ priceByLitre.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Basket)) {
			return false;
		}
		Basket other = (Basket) obj;
		return Double.doubleToLongBits(fuelQuantity) == Double.doubleToLongBits(other.fuelQuantity)
				&& priceByLitre.equals(other.priceByLitre);
	}

	@Override
	public String toString() {
		return "Basket [fuelQuantity=" + fuelQuantity + ", priceByLitre=" + priceByLitre + "]";
	}

}
