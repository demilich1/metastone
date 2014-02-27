package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArgentProtector extends MinionCard {

	public ArgentProtector() {
		super("Argent Protector", 2, 2, Rarity.COMMON, HeroClass.PALADIN, 2);
		setDescription("Battlecry: Give a friendly minion Divine Shield.");
	}

	@Override
	public Minion summon() {
		Minion argentProtector = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new ApplyTagSpell(GameTag.DIVINE_SHIELD), TargetSelection.FRIENDLY_MINIONS);
		argentProtector.setBattlecry(battlecry);
		return argentProtector;
	}

}
