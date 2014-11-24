package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class IronSensai extends MinionCard {

	public IronSensai() {
		super("Iron Sensai", 2, 2, Rarity.RARE, HeroClass.ROGUE, 3);
		setDescription("At the end of your turn, give another friendly Mech +2/+2.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion ironSensai = createMinion();
		SpellDesc buffSpell = BuffSpell.create(+2, +2);
		buffSpell.setTargetFilter(target -> target.getTag(GameTag.RACE) == Race.MECH);
		buffSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		buffSpell.pickRandomTarget(true);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), buffSpell);
		ironSensai.setSpellTrigger(trigger);
		return ironSensai;
	}

}
