package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Wrath extends ChooseOneCard {

	public Wrath() {
		super("Wrath", CardType.SPELL, Rarity.COMMON, HeroClass.DRUID, 1);
		setDescription("Choose One - Deal $3 damage to a minion; or $1 damage and draw a card.");
		setCard1(new WrathDamage());
		setCard2(new WrathDamageAndDraw());
	}
	
	private class WrathDamage extends SpellCard {

		public WrathDamage() {
			super("Wrath (3 damage)", Rarity.COMMON, HeroClass.SHAMAN, 1);
			setDescription("Deal $3 damage to a minion");
			setSpell(new DamageSpell(3));
			setTargetRequirement(TargetSelection.MINIONS);
		}
		
	}
	
	private class WrathDamageAndDraw extends SpellCard {

		public WrathDamageAndDraw() {
			super("Wrath (1 damage + draw card)", Rarity.COMMON, HeroClass.SHAMAN, 1);
			setDescription("Deal $1 damage to a minion and draw a card.");
			setSpell(new MetaSpell(new DamageSpell(1), new DrawCardSpell()));
			setTargetRequirement(TargetSelection.MINIONS);
		}
		
	}


}
