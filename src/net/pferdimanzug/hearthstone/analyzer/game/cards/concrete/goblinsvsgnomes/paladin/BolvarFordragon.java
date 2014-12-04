package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class BolvarFordragon extends MinionCard implements IGameEventListener {

	private EntityReference hostReference;

	public BolvarFordragon() {
		super("Bolvar Fordragon", 1, 7, Rarity.LEGENDARY, HeroClass.PALADIN, 5);
		setDescription("Whenever a friendly minion dies while this in your hand, gain +1 Attack.");
	}

	@Override
	public Minion summon() {
		Minion bolvarFordragon = createMinion();
		return bolvarFordragon;
	}

	@Override
	public TriggerLayer getLayer() {
		return TriggerLayer.DEFAULT;
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
	}

	@Override
	public void onGameEvent(GameEvent event) {
		KillEvent killEvent = (KillEvent) event;
		if (killEvent.getVictim().getOwner() != getOwner()) {
			return;
		}
		modifyTag(GameTag.ATTACK, +1);
	}

	@Override
	public void onRemove(GameContext context) {
	}

	@Override
	public void reset() {
		setTag(GameTag.ATTACK, getBaseAttack());
	}

	@Override
	public void setHost(Entity host) {
		this.hostReference = host.getReference();
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

}
