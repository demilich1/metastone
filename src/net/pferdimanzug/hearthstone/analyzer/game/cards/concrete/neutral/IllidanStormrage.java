package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.CardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class IllidanStormrage extends MinionCard {

	private class FlameOfAzzinoth extends MinionCard {

		public FlameOfAzzinoth() {
			super("Flame of Azzinoth", 2, 1, Rarity.FREE, HeroClass.ANY, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

	public IllidanStormrage() {
		super("Illidan Stormrage", 7, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Whenever you play a card, summon a 2/1 Flame of Azzinoth.");
		setRace(Race.DEMON);
	}

	@Override
	public Minion summon() {
		Minion illidanStormrage = createMinion();
		Spell summonSpell = new SummonSpell(new FlameOfAzzinoth());
		SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(TargetPlayer.SELF), summonSpell);
		illidanStormrage.setSpellTrigger(trigger);
		return illidanStormrage;
	}

}
