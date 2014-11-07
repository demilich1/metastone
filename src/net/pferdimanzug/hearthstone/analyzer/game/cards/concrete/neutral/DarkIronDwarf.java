package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DarkIronDwarf extends MinionCard {

	private static final int ATTACK_BONUS = 2;

	public DarkIronDwarf() {
		super("Dark Iron Dwarf", 4, 4, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Battlecry: Give a minion +2 Attack this turn. ");
	}

	@Override
	public int getTypeId() {
		return 112;
	}

	@Override
	public Minion summon() {
		Minion darkIronDwarf = createMinion();
		SpellDesc buff = BuffSpell.create(+ATTACK_BONUS, 0, true);
		Battlecry battlecry = Battlecry.createBattlecry(buff, TargetSelection.MINIONS);
		darkIronDwarf.setBattlecry(battlecry);
		return darkIronDwarf;
	}
}
