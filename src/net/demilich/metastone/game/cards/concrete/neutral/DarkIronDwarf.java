package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

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
		SpellDesc buff = TemporaryAttackSpell.create(+ATTACK_BONUS);
		Battlecry battlecry = Battlecry.createBattlecry(buff, TargetSelection.MINIONS);
		darkIronDwarf.setBattlecry(battlecry);
		return darkIronDwarf;
	}
}
