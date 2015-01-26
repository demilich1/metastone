package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.ChooseOneCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
