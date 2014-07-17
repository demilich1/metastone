package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MasterSwordsmith extends MinionCard {

	public MasterSwordsmith() {
		super("Master Swordsmith", 1, 3, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("At the end of your turn, give another random friendly minion +1 Attack.");
	}

	@Override
	public int getTypeId() {
		return 164;
	}



	@Override
	public Minion summon() {
		Minion masterSwordsmith = createMinion();
		Spell randomBuffSpell = new BuffRandomSpell(+1);
		randomBuffSpell.setTarget(EntityReference.OTHER_FRIENDLY_MINIONS);
		masterSwordsmith.setSpellTrigger(new SpellTrigger(new TurnEndTrigger(), randomBuffSpell));
		return masterSwordsmith;
	}
}
