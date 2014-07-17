package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FrostElemental extends MinionCard {

	public FrostElemental() {
		super("Frost Elemental", 5, 5, Rarity.COMMON, HeroClass.ANY, 6);
		setDescription("Battlecry: Freeze a character.");
	}

	@Override
	public int getTypeId() {
		return 129;
	}



	@Override
	public Minion summon() {
		Minion frostElemental = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new ApplyTagSpell(GameTag.FROZEN), TargetSelection.ANY);
		frostElemental.setBattlecry(battlecry);
		return frostElemental;
	}
}
