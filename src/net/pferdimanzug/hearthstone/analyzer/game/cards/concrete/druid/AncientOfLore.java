package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
	protected Battlecry getBattlecry1() {
		return Battlecry.createBattlecry(new DrawCardSpell(2));
	}

	@Override
	protected Battlecry getBattlecry2() {
		return Battlecry.createBattlecry(new HealingSpell(5), TargetSelection.ANY);
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
