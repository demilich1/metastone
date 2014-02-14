package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TempleEnforcer extends MinionCard {

	public TempleEnforcer() {
		super("Temple Enforcer", 6, 6, Rarity.COMMON, HeroClass.PRIEST, 6);
		setDescription("Battlecry: Give a friendly minion +3 Health.");
	}

	@Override
	public Minion summon() {
		Minion templeEnforcer = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new BuffSpell(0, 3), TargetSelection.FRIENDLY_MINIONS);
		templeEnforcer.setTag(GameTag.BATTLECRY, battlecry);
		return templeEnforcer;
	}

}
