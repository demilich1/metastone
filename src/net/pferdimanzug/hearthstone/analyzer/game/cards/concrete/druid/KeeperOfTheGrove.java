package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SilenceSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class KeeperOfTheGrove extends ChooseOneCard {

	public KeeperOfTheGrove() {
		super("Keeper of the Grove", CardType.MINION, Rarity.RARE, HeroClass.DRUID, 4);
		setDescription("Choose One - Deal 2 damage; or Silence a minion.");

		setCard1(new KeeperDamage());
		setCard2(new KeeperSilence());
		
		setTag(GameTag.BASE_ATTACK, 2);
		setTag(GameTag.BASE_HP, 4);
	}

	private class KeeperDamage extends MinionCard {

		public KeeperDamage() {
			super("Keeper of the Grove (Damage)", 2, 4, Rarity.RARE, HeroClass.DRUID, 4);
		}

		@Override
		public Minion summon() {
			Minion keeperOfTheGrove = new KeeperMinion(this);
			Battlecry battlecry = Battlecry.createBattlecry(new DamageSpell(2), TargetSelection.ANY);
			keeperOfTheGrove.setBattlecry(battlecry);
			return keeperOfTheGrove;
		}

	}

	private class KeeperSilence extends MinionCard {

		public KeeperSilence() {
			super("Keeper of the Grove (Silence)", 2, 4, Rarity.RARE, HeroClass.DRUID, 4);
		}

		@Override
		public Minion summon() {
			Minion keeperOfTheGrove = new KeeperMinion(this);
			Battlecry battlecry = Battlecry.createBattlecry(new SilenceSpell(), TargetSelection.MINIONS);
			keeperOfTheGrove.setBattlecry(battlecry);
			return keeperOfTheGrove;
		}
	}

	private class KeeperMinion extends Minion {

		public KeeperMinion(MinionCard sourceCard) {
			super(sourceCard);
		}

		@Override
		public Card returnToHand() {
			return new KeeperOfTheGrove();
		}

	}

}
