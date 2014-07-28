package net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public abstract class CardCostModifier implements IGameEventListener {

	private final boolean oneTime;
	private boolean expired;
	private int owner;
	private EntityReference hostReference;
	private int manaModifier;
	private int minValue;
	private CardType cardType;
	private GameTag requiredTag;
	private TargetPlayer targetPlayer = TargetPlayer.SELF;

	public CardCostModifier(CardType cardType, int manaModifier, boolean oneTime) {
		this.cardType = cardType;
		this.manaModifier = manaModifier;
		this.oneTime = oneTime;
	}

	protected boolean appliesTo(Card card) {
		if (getRequiredTag() != null && !card.hasTag(getRequiredTag())) {
			return false;
		}
		switch (getTargetPlayer()) {
		case BOTH:
			break;
		case OPPONENT:
			if (card.getOwner() == getOwner()) {
				return false;
			}
			break;
		case SELF:
			if (card.getOwner() != getOwner()) {
				return false;
			}
			break;
		default:
			break;

		}
		return card.getCardType() == cardType;
	}

	@Override
	public IGameEventListener clone() {
		try {
			CardCostModifier clone = (CardCostModifier) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void expire() {
		expired = true;
	}

	@Override
	public EntityReference getHostReference() {
		return hostReference;
	}

	@Override
	public TriggerLayer getLayer() {
		return TriggerLayer.DEFAULT;
	}

	public int getMinValue() {
		return minValue;
	}

	@Override
	public int getOwner() {
		return owner;
	}

	public GameTag getRequiredTag() {
		return requiredTag;
	}

	public TargetPlayer getTargetPlayer() {
		return targetPlayer;
	}

	@Override
	public boolean isExpired() {
		return expired;
	}

	protected int modifyManaCost(Card card) {
		return manaModifier;
	}

	@Override
	public void onAdd(GameContext context) {
	}

	@Override
	public void onRemove(GameContext context) {
		expired = true;
	}

	public int process(Card card) {
		if (expired || !appliesTo(card)) {
			return 0;
		}

		if (oneTime) {
			expired = true;
		}
		return modifyManaCost(card);
	}

	@Override
	public void reset() {
		expired = false;
	}

	@Override
	public void setHost(Entity host) {
		hostReference = host.getReference();
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	
	@Override
	public void setOwner(int playerIndex) {
		this.owner = playerIndex;
	}

	public void setRequiredTag(GameTag requiredTag) {
		this.requiredTag = requiredTag;
	}

	public void setTargetPlayer(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

}
