package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class GuardianOfKings extends MinionCard {

	public GuardianOfKings() {
		super("Guardian of Kings", Rarity.FREE, HeroClass.PALADIN, 7);
	}

	@Override
	public Minion summon() {
		Minion guardianOfKings = createMinion(5, 6);
		Battlecry battlecry = Battlecry.createBattlecry(new HealingSpell(6), TargetSelection.FRIENDLY_HERO);
		guardianOfKings.setTag(GameTag.BATTLECRY, battlecry);
		return guardianOfKings;
	}
	

}
