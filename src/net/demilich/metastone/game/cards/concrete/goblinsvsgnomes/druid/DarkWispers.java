package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.ChooseOneCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.neutral.Wisp;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DarkWispers extends ChooseOneCard {

	public DarkWispers() {
		super("Dark Wispers", CardType.SPELL, Rarity.EPIC, HeroClass.DRUID, 6);
		setDescription("Choose One - Summon 5 Wisps; or Give a minion +5/+5 and Taunt.");
		setCard1(new SummonWisps());
		setCard2(new BuffAndTaunt());
	}

	@Override
	public int getTypeId() {
		return 477;
	}
	
	private class BuffAndTaunt extends SpellCard {

		public BuffAndTaunt() {
			super("Dark Wispers (+5/+5 and Taunt)", Rarity.EPIC, HeroClass.DRUID, 6);
			setSpell(MetaSpell.create(BuffSpell.create(5, 5), ApplyTagSpell.create(GameTag.TAUNT)));
			setTargetRequirement(TargetSelection.MINIONS);
		}
	}



	private class SummonWisps extends SpellCard {

		public SummonWisps() {
			super("Dark Wispers (5 Wisps)", Rarity.EPIC, HeroClass.DRUID, 6);

			setSpell(SummonSpell.create(new Wisp(), new Wisp(), new Wisp(), new Wisp(), new Wisp()));
			setTargetRequirement(TargetSelection.NONE);
		}
	}
}
