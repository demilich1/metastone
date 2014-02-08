package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PriestessOfElune extends MinionCard {

	public PriestessOfElune() {
		super("Priestess Of Elune", 5, 4, Rarity.COMMON, HeroClass.ANY, 6);
	}

	@Override
	public Minion summon() {
		Battlecry battlecry = Battlecry.createBattlecry(new HealingSpell(4), TargetSelection.FRIENDLY_HERO);
		Minion priestessOfElune = createMinion();
		priestessOfElune.setTag(GameTag.BATTLECRY, battlecry);
		return priestessOfElune;
	}

}
