package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class ArgentProtector extends MinionCard {

	public ArgentProtector() {
		super("Argent Protector", Rarity.COMMON, HeroClass.PALADIN, 2);
	}

	@Override
	public Minion summon() {
		Minion argentProtector = createMinion(2, 2);
		Battlecry battlecry = Battlecry.createBattlecry(new BuffSpell(GameTag.DIVINE_SHIELD), TargetRequirement.FRIENDLY_MINIONS);
		argentProtector.setTag(GameTag.BATTLECRY, battlecry);
		return argentProtector;
	}

}
