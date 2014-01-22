package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RandomDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MadBomber extends MinionCard {

	public MadBomber() {
		super("Mad Bomber", Rarity.COMMON, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion madBomber = createMinion(3, 2);
		Battlecry battlecry = Battlecry.createBattlecry(new RandomDamageSpell(1, 3), TargetSelection.ANY);
		madBomber.setTag(GameTag.BATTLECRY, battlecry);
		return madBomber;
	}

}
