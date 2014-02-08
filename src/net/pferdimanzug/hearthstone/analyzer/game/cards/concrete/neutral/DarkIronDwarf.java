package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DarkIronDwarf extends MinionCard {

	private static final int ATTACK_BONUS = 2;

	public DarkIronDwarf() {
		super("Dark Iron Dwarf", 4, 4, Rarity.COMMON, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		Minion darkIronDwarf = createMinion();
		BuffSpell buff = new BuffSpell(+ATTACK_BONUS);
		buff.setTemporary(true);
		Battlecry battlecry = Battlecry.createBattlecry(buff, TargetSelection.MINIONS);
		darkIronDwarf.setTag(GameTag.BATTLECRY, battlecry);
		return darkIronDwarf;
	}

}
