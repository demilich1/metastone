package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.ChooseBattlecryCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.ModifyMaxManaSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
	protected BattlecryAction getBattlecry1() {
		SpellDesc gainManaSpell = ModifyMaxManaSpell.create(TargetPlayer.BOTH, +1, true);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(gainManaSpell);
		return battlecry;
	}

	@Override
	protected BattlecryAction getBattlecry2() {
		BattlecryAction battlecry = BattlecryAction.createBattlecry(DrawCardSpell.create(1, TargetPlayer.BOTH));
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
