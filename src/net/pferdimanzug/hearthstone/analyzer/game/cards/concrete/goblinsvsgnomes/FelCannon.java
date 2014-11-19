package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class FelCannon extends MinionCard {

	public FelCannon() {
		super("Fel Cannon", 3, 5, Rarity.RARE, HeroClass.WARLOCK, 4);
		setDescription("At the end of your turn, deal 2 damage to a non-Mech minion.");
		setRace(Race.MECH);
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
