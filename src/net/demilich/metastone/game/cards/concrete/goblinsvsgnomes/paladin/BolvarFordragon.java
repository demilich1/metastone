package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.KillEvent;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.spells.trigger.TriggerLayer;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.EntityReference;

public class BolvarFordragon extends MinionCard implements IGameEventListener {

	private EntityReference hostReference;

	public BolvarFordragon() {
		super("Bolvar Fordragon", 1, 7, Rarity.LEGENDARY, HeroClass.PALADIN, 5);
		setDescription("Whenever a friendly minion dies while this in your hand, gain +1 Attack.");
	}

	@Override
	public BolvarFordragon clone() {
		BolvarFordragon clone = (BolvarFordragon) super.clone();
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
		return 551;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return eventType == GameEventType.KILL;
	}

	@Override
	public boolean isExpired() {
		return getCardReference().getLocation() != CardLocation.HAND;
	}

	@Override
	public void onAdd(GameContext context) {
		removeTag(GameTag.ATTACK_BONUS);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		KillEvent killEvent = (KillEvent) event;
		if (killEvent.getVictim().getOwner() != getOwner()) {
			return;
		}
		modifyTag(GameTag.ATTACK_BONUS, +1);
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
		Minion bolvarFordragon = createMinion();
		return bolvarFordragon;
	}
}
