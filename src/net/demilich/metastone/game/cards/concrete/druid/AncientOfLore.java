package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.ChooseBattlecryCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AncientOfLore extends ChooseBattlecryCard {

	public AncientOfLore() {
		super("Ancient of Lore", 5, 5, Rarity.RARE, HeroClass.DRUID, 7);
		setDescription("Choose One - Draw 2 cards; or Restore 5 Health.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	protected String getAction1Suffix() {
		return "Draw 2 cards";
	}

	@Override
	protected String getAction2Suffix() {
		return "Restore 5 Health";
	}

	@Override
	protected BattlecryAction getBattlecry1() {
		return BattlecryAction.createBattlecry(DrawCardSpell.create(2));
	}

	@Override
	protected BattlecryAction getBattlecry2() {
		return BattlecryAction.createBattlecry(HealingSpell.create(5), TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 1;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
