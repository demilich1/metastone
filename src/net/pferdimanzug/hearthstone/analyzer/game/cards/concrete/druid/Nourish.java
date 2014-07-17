package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ModifyMaxManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Nourish extends ChooseOneCard {

	private class NourishDrawCard extends SpellCard {

		public NourishDrawCard() {
			super("Nourish (Mana)", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Draw 3 cards");
			setCollectible(false);

			setSpell(new DrawCardSpell(3));
			setTargetRequirement(TargetSelection.NONE);
		}
	}

	private class NourishManaCard extends SpellCard {

		public NourishManaCard() {
			super("Nourish (Mana)", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Gain 2 Mana Crystals");
			setCollectible(false);

			setSpell(new ModifyMaxManaSpell(2, TargetPlayer.SELF));
			setTargetRequirement(TargetSelection.NONE);
		}
	}

	public Nourish() {
		super("Nourish", CardType.SPELL, Rarity.RARE, HeroClass.DRUID, 5);
		setDescription("Choose One - Gain 2 Mana Crystals; or Draw 3 cards.");

		setCard1(new NourishManaCard());
		setCard2(new NourishDrawCard());
	}



	@Override
	public int getTypeId() {
		return 16;
	}
}
