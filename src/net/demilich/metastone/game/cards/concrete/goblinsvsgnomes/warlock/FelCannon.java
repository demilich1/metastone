package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class FelCannon extends MinionCard {

	public FelCannon() {
		super("Fel Cannon", 3, 5, Rarity.RARE, HeroClass.WARLOCK, 4);
		setDescription("At the end of your turn, deal 2 damage to a non-Mech minion.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 598;
	}

	@Override
	public Minion summon() {
		Minion felCannon = createMinion();
		SpellDesc damage = DamageSpell.create(2);
		damage.setTarget(EntityReference.ALL_MINIONS);
		damage.pickRandomTarget(true);
		damage.setTargetFilter(entity -> entity.getTag(GameTag.RACE) != Race.MECH);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), damage);
		felCannon.setSpellTrigger(trigger);
		return felCannon;
	}
}
