package net.pferdimanzug.hearthstone.analyzer.game.targeting;

public class CardReference {
	
	private final int playerId;
	private final CardLocation location;
	private final int cardId;
	
	public CardReference(int playerId, CardLocation location, int cardId) {
		this.playerId = playerId;
		this.location = location;
		this.cardId = cardId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public CardLocation getLocation() {
		return location;
	}

	public int getCardId() {
		return cardId;
	}
	
	@Override
	public String toString() {
		return String.format("[CardReference playerId:%d cardLocation:%s cardId:%d]", playerId, location.toString(), cardId);
	}

}
