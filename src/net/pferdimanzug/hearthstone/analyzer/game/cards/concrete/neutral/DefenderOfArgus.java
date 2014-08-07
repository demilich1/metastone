package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DefenderOfArgus extends MinionCard {

	public DefenderOfArgus() {
		super("Defender of Argus", 2, 3, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Give adjacent minions +1/+1 and Taunt.");
	}

	@Override
	public int getTypeId() {
		return 115;
	}




	@Override
	public Minion summon() {
		Minion defenderOfArgus = createMinion();
		SpellDesc buffSpell = MetaSpell.create(BuffSpell.create(1, 1), ApplyTagSpell.create(GameTag.TAUNT));
		buffSpell.setTarget(EntityReference.ADJACENT_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell, TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		defenderOfArgus.setBattlecry(battlecry);
		return defenderOfArgus;
	}
}
