package com.hiddenswitch.cardsgen.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class TestConfig implements Serializable {
	private String cardId;
	private String deckIdTest;
	private String deckIdOpponent;

	public boolean isControl() {
		return cardId == null || cardId.isEmpty();
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getDeckIdTest() {
		return deckIdTest;
	}

	public void setDeckIdTest(String deckIdTest) {
		this.deckIdTest = deckIdTest;
	}

	public String getDeckIdOpponent() {
		return deckIdOpponent;
	}

	public void setDeckIdOpponent(String deckIdOpponent) {
		this.deckIdOpponent = deckIdOpponent;
	}

	@Override
	public String toString() {
		return String.format("[TestConfig cardId = %s, deckIdToTest = %s, deckIdOpponent = %s]\n", cardId, deckIdTest, deckIdOpponent);
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(cardId);
		builder.append(deckIdTest);
		builder.append(deckIdOpponent);
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		TestConfig rhs = (TestConfig) obj;
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(cardId, rhs.cardId)
				.append(deckIdTest, rhs.deckIdTest)
				.append(deckIdOpponent, rhs.deckIdOpponent)
				.isEquals();
	}
}
