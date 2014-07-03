package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SunfuryProtector extends MinionCard {

	public SunfuryProtector() {
		super("Sunfury Protector", 2, 3, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Battlecry: Give adjacent minions Taunt.");
	}

	@Override
	public Minion summon() {
		Minion sunfuryProtector = createMinion();
		Spell buffSpell = new ApplyTagSpell(GameTag.TAUNT);
		buffSpell.setTarget(EntityReference.ADJACENT_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell, TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		sunfuryProtector.setBattlecry(battlecry);
		return sunfuryProtector;
	}

}
