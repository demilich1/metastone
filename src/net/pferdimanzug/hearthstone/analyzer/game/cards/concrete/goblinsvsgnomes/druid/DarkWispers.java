package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Wisp;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DarkWispers extends ChooseOneCard {

	public DarkWispers() {
		super("Dark Wispers", CardType.SPELL, Rarity.EPIC, HeroClass.DRUID, 6);
		setDescription("Choose One - Summon 5 Wisps; or Give a minion +5/+5 and Taunt.");
		setCard1(new SummonWisps());
		setCard2(new BuffAndTaunt());
	}

	private class SummonWisps extends SpellCard {

		public SummonWisps() {
			super("Dark Wispers (5 Wisps)", Rarity.EPIC, HeroClass.DRUID, 6);

			setSpell(SummonSpell.create(new Wisp(), new Wisp(), new Wisp(), new Wisp(), new Wisp()));
			setTargetRequirement(TargetSelection.NONE);
		}
	}
	
	private class BuffAndTaunt extends SpellCard {

		public BuffAndTaunt() {
			super("Dark Wispers (+5/+5 and Taunt)", Rarity.EPIC, HeroClass.DRUID, 6);
			setSpell(MetaSpell.create(BuffSpell.create(5, 5), ApplyTagSpell.create(GameTag.TAUNT)));
			setTargetRequirement(TargetSelection.MINIONS);
		}
	}

}
