package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.ChooseOneCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.ModifyMaxManaSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Nourish extends ChooseOneCard {

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

	private class NourishDrawCard extends SpellCard {

		public NourishDrawCard() {
			super("Draw 3", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Draw 3 cards");
			setCollectible(false);

			setSpell(DrawCardSpell.create(3));
			setTargetRequirement(TargetSelection.NONE);
		}
	}

	private class NourishManaCard extends SpellCard {

		public NourishManaCard() {
			super("+2 Mana crystals", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Gain 2 Mana Crystals");
			setCollectible(false);

			setSpell(ModifyMaxManaSpell.create(2, TargetPlayer.SELF));
			setTargetRequirement(TargetSelection.NONE);
		}
	}
}
