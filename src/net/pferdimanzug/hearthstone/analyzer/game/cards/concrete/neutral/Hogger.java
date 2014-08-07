package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

public class Hogger extends MinionCard {

	public Hogger() {
		super("Hogger", 4, 4, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("At the end of your turn, summon a 2/2 Gnoll with Taunt.");
	}

	@Override
	public int getTypeId() {
		return 141;
	}

	@Override
	public Minion summon() {
		Minion hogger = createMinion();
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), SummonSpell.create(new Gnoll()));
		hogger.setSpellTrigger(trigger);
		return hogger;
	}



	private class Gnoll extends MinionCard {

		public Gnoll() {
			super("Gnoll", 2, 2, Rarity.FREE, HeroClass.ANY, 2);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}

	}
}
