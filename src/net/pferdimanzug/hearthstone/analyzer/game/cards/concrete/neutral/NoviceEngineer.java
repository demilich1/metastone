package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;

public class NoviceEngineer extends MinionCard {

	public NoviceEngineer() {
		super("Novice Engineer", Rarity.FREE, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion noviceEngineer = createMinion(1, 2);
		Battlecry battlecry = Battlecry.createBattlecry(new DrawCardSpell(1), TargetRequirement.NONE);
		noviceEngineer.setTag(GameTag.BATTLECRY, battlecry);
		return noviceEngineer;
	}
}
