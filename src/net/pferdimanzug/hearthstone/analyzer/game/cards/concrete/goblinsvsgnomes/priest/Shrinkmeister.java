package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TemporaryAttackSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
