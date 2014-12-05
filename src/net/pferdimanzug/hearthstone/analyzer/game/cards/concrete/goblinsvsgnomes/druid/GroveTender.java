package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.druid;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ModifyMaxManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

public class GroveTender extends ChooseBattlecryCard {

	public GroveTender() {
		super("Grove Tender", 2, 4, Rarity.RARE, HeroClass.DRUID, 3);
		setDescription("Choose One - Give each player a Mana Crystal; or Each player draws a card.");
	}

	@Override
	protected String getAction1Suffix() {
		return "Mana Crystal";
	}

	@Override
	protected String getAction2Suffix() {
		return "Draw";
	}

	@Override
	protected Battlecry getBattlecry1() {
		Battlecry battlecry = Battlecry.createBattlecry(ModifyMaxManaSpell.create(+1, TargetPlayer.BOTH));
		return battlecry;
	}

	@Override
	protected Battlecry getBattlecry2() {
		Battlecry battlecry = Battlecry.createBattlecry(DrawCardSpell.create(1, TargetPlayer.BOTH));
		return battlecry;
	}

	@Override
	public int getTypeId() {
		return 479;
	}



	@Override
	public Minion summon() {
		Minion groveTender = createMinion();
		return groveTender;
	}
}
