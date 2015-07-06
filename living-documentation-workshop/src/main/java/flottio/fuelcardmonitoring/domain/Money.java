package flottio.fuelcardmonitoring.domain;

import java.util.Currency;

import flottio.annotations.ValueObject;

/**
 * Represents an amount of money and its currency
 */
@ValueObject
public class Money {

	private final Currency currency;
	private final int amountInCents;

	public final static Money ZERO_EUR = new Money(0., "EUR");

	public Money(double amount, String currencyCode) {
		this.amountInCents = (int) (amount * 100.);
		this.currency = Currency.getInstance(currencyCode);
	}

	public double getAmount() {
		return amountInCents / 100.;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public int hashCode() {
		return currency.hashCode() ^ amountInCents;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Money)) {
			return false;
		}
		Money other = (Money) obj;
		return amountInCents == other.amountInCents && currency == other.currency;
	}

	@Override
	public String toString() {
		return currency + " " + getAmount();
	}

}
