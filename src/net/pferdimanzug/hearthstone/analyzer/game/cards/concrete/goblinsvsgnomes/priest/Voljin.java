package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.VoljinSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Voljin extends MinionCard {

	public Voljin() {
		super("Voljin", 6, 2, Rarity.LEGENDARY, HeroClass.PRIEST, 5);
		setDescription("Battlecry: Swap Health with another minion.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 566;
	}



	@Override
	public Minion summon() {
		Minion voljin = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(VoljinSpell.create(), TargetSelection.MINIONS);
		voljin.setBattlecry(battlecry);
		return voljin;
	}
}
