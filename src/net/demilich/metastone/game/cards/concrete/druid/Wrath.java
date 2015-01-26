package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.ChooseOneCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Wrath extends ChooseOneCard {

	public Wrath() {
		super("Wrath", CardType.SPELL, Rarity.COMMON, HeroClass.DRUID, 2);
		setDescription("Choose One - Deal $3 damage to a minion; or $1 damage and draw a card.");
		setCard1(new WrathDamage());
		setCard2(new WrathDamageAndDraw());
	}
	
	@Override
	public int getTypeId() {
		return 25;
	}
	
	private class WrathDamage extends SpellCard {

		public WrathDamage() {
			super("Wrath (3 damage)", Rarity.COMMON, HeroClass.DRUID, 2);
			setDescription("Deal $3 damage to a minion");
			setSpell(DamageSpell.create(3));
			setTargetRequirement(TargetSelection.MINIONS);
		}
		
	}

	private class WrathDamageAndDraw extends SpellCard {

		public WrathDamageAndDraw() {
			super("Wrath (1 damage + draw card)", Rarity.COMMON, HeroClass.DRUID, 2);
			setDescription("Deal $1 damage to a minion and draw a card.");
			setSpell(MetaSpell.create(DamageSpell.create(1), DrawCardSpell.create()));
			setTargetRequirement(TargetSelection.MINIONS);
		}
		
	}
}
