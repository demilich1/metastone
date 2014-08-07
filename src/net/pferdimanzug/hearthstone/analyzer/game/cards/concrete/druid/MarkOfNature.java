package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MarkOfNature extends ChooseOneCard {

	public MarkOfNature() {
		super("Mark of Nature", CardType.SPELL, Rarity.COMMON, HeroClass.DRUID, 3);
		setDescription("Choose One - Give a minion +4 Attack; or +4 Health and Taunt.");
		setCard1(new AttackBuff());
		setCard2(new HpBuff());
	}
	
	@Override
	public int getTypeId() {
		return 12;
	}
	
	private class AttackBuff extends SpellCard {

		public AttackBuff() {
			super("Mark of Nature (+4 Attack)", Rarity.COMMON, HeroClass.DRUID, 3);
			setSpell(BuffSpell.create(4));
			setTargetRequirement(TargetSelection.MINIONS);
		}
		
	}

	private class HpBuff extends SpellCard {

		public HpBuff() {
			super("Mark of Nature (+4 Hp, Taunt)", Rarity.COMMON, HeroClass.DRUID, 3);
			setSpell(MetaSpell.create(BuffSpell.create(0, 4), ApplyTagSpell.create(GameTag.TAUNT)));
			setTargetRequirement(TargetSelection.MINIONS);
		}
		
	}
}
