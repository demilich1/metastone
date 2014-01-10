package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;

public class ArgentProtector extends MinionCard {

	public ArgentProtector() {
		super("Argent Protector", Rarity.COMMON, HeroClass.PALADIN, 2);
	}

	@Override
	public Minion summon() {
		Minion argentProtector = createMinion(2, 2);
		Battlecry battlecry = Battlecry.createBattlecry(new ApplyTagSpell(GameTag.DIVINE_SHIELD), TargetSelection.FRIENDLY_MINIONS);
		argentProtector.setTag(GameTag.BATTLECRY, battlecry);
		return argentProtector;
	}

}
