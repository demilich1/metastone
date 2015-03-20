package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.DrawCardEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.spells.trigger.TriggerLayer;
import net.demilich.metastone.game.targeting.EntityReference;

public class FlameLeviathan extends MinionCard implements IGameEventListener {

	private EntityReference hostReference;
	private boolean fired;

	public FlameLeviathan() {
		super("Flame Leviathan", 7, 7, Rarity.LEGENDARY, HeroClass.MAGE, 7);
		setDescription("When you draw this, deal 2 damage to all characters.");
		setRace(Race.MECH);
	}

	@Override
	public FlameLeviathan clone() {
		FlameLeviathan clone = (FlameLeviathan) super.clone();
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
		return 494;
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
		SpellDesc damage = DamageSpell.create(EntityReference.ALL_CHARACTERS, 2);
		event.getGameContext().getLogic().castSpell(getOwner(), damage, getReference(), EntityReference.NONE);
	}

	@Override
	public void onRemove(GameContext context) {
	}

	@Override
	public void setHost(Entity host) {
		this.hostReference = host.getReference();
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
