package flottio.fuelcardmonitoring.domain;

import java.util.Date;

import flottio.annotations.CoreConcept;
import flottio.annotations.GuidedTour;
import flottio.annotations.ValueObject;

/**
 * A transaction between a card and a merchant as reported by the fuel card
 * provider
 */
@ValueObject
@CoreConcept
@GuidedTour(name = "Quick Developer Tour", description = "The incoming fuel card transaction", rank = 2)
public class FuelCardTransaction {

	private final Date date;
	private final FueldCard card;
	private final Merchant merchant;
	private final Basket basket;
	private final Money amount;

	public FuelCardTransaction(Date date, FueldCard card, Merchant merchant, Basket basket, Money amount) {
		this.date = date;
		this.card = card;
		this.merchant = merchant;
		this.basket = basket;
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public FueldCard getCard() {
		return card;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public Basket getBasket() {
		return basket;
	}

	public Money getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + amount.hashCode();
		result = prime * result + basket.hashCode();
		result = prime * result + card.hashCode();
		result = prime * result + date.hashCode();
		result = prime * result + merchant.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FuelCardTransaction)) {
			return false;
		}
		final FuelCardTransaction other = (FuelCardTransaction) obj;
		return amount.equals(other.amount) && basket.equals(other.basket) && card.equals(other.card)
				&& date.equals(other.date) && merchant.equals(other.merchant);
	}

	@Override
	public String toString() {
		return "FuelCardTransaction [date=" + date + ", card=" + card + ", merchant=" + merchant + ", basket=" + basket
				+ ", amount=" + amount + "]";
	}

}
