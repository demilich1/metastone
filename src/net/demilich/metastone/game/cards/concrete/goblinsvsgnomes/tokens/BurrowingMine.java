package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.events.DrawCardEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.NullSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.spells.trigger.TriggerLayer;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BurrowingMine extends SpellCard implements IGameEventListener {

	private EntityReference hostReference;
	private boolean fired;

	public BurrowingMine() {
		super("Burrowing Mine", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("When you draw this, it explodes. You take 10 damage and draw a card.");
		setCollectible(false);

		setSpell(NullSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public BurrowingMine clone() {
		BurrowingMine clone = (BurrowingMine) super.clone();
		clone.hostReference = hostReference != null ? new EntityReference(hostReference.getId()) : null;
		return clone;
	}

	@Override
	public EntityReference getHostReference() {
		return hostReference;
	}

	@Override
	public TriggerLayer getLayer() {
		return TriggerLayer.DEFAULT;
	}

	@Override
	public int getTypeId() {
		return 591;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return eventType == GameEventType.DRAW_CARD;
	}

	@Override
	public boolean isExpired() {
		return fired;
	}

	@Override
	public void onAdd(GameContext context) {
		fired = false;
	}

	@Override
	public void onGameEvent(GameEvent event) {
		DrawCardEvent cardEvent = (DrawCardEvent) event;
		if (cardEvent.getCard() != this) {
			return;
		}

		fired = true;
		SpellDesc damage = DamageSpell.create(EntityReference.FRIENDLY_HERO,10);
		SpellDesc mineExplodes = MetaSpell.create(damage, DrawCardSpell.create());
		event.getGameContext().getLogic().castSpell(cardEvent.getPlayerId(), mineExplodes, getReference(), EntityReference.NONE);
	}

	@Override
	public void onRemove(GameContext context) {
	}

	@Override
	public void setHost(Entity host) {
		this.hostReference = host.getReference();
	}
}
