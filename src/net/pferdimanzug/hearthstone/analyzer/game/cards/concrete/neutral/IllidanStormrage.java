package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.CardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class IllidanStormrage extends MinionCard {

	public IllidanStormrage() {
		super("Illidan Stormrage", 7, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Whenever you play a card, summon a 2/1 Flame of Azzinoth.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 143;
	}

	@Override
	public Minion summon() {
		Minion illidanStormrage = createMinion();
		SpellDesc summonSpell = SummonSpell.create(new FlameOfAzzinoth());
		SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(TargetPlayer.SELF), summonSpell);
		illidanStormrage.setSpellTrigger(trigger);
		return illidanStormrage;
	}

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
}
