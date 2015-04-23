package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.CopyPlayedCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.CardDrawnTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class Chromaggus extends MinionCard {

	public Chromaggus() {
		super("Chromaggus", 6, 8, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("Whenever you draw a card, put another copy into your hand.");
		setRace(Race.DRAGON);
	}

	@Override
	public Minion summon() {
		Minion chromaggus = createMinion();
		SpellTrigger trigger = new SpellTrigger(new CardDrawnTrigger(TargetPlayer.SELF), CopyPlayedCardSpell.create(TargetPlayer.SELF));
		chromaggus.setSpellTrigger(trigger);
		return chromaggus;
	}

	@Override
	public int getTypeId() {
		return 626;
	}
}
