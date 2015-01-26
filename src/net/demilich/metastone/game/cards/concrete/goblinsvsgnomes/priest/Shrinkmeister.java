package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Shrinkmeister extends MinionCard {

	public Shrinkmeister() {
		super("Shrinkmeister", 3, 2, Rarity.COMMON, HeroClass.PRIEST, 2);
		setDescription("Battlecry: Give a minion -2 Attack this turn.");
	}

	@Override
	public int getTypeId() {
		return 563;
	}



	@Override
	public Minion summon() {
		Minion shrinkmeister = createMinion();
		SpellDesc buff = TemporaryAttackSpell.create(-2);
		Battlecry battlecry = Battlecry.createBattlecry(buff, TargetSelection.MINIONS);
		shrinkmeister.setBattlecry(battlecry);
		return shrinkmeister;
	}
}
