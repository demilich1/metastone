package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.events.DrawCardEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc damage = DamageSpell.create(2);
		damage.setTarget(EntityReference.ALL_CHARACTERS);
		damage.setSourceEntity(getReference());
		event.getGameContext().getLogic().castSpell(getOwner(), damage);
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
