package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DiscardCardsFromDeckSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.CardPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class FelReaver extends MinionCard {

	public FelReaver() {
		super("Fel Reaver", 8, 8, Rarity.EPIC, HeroClass.ANY, 5);
		setDescription("Whenever your opponent plays a card, discard the top 3 cards of your deck.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 512;
	}

	@Override
	public Minion summon() {
		Minion felReaver = createMinion();
		SpellDesc discard = DiscardCardsFromDeckSpell.create(3);
		//SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(TargetPlayer.OPPONENT), discard);
		SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(null), discard);
		felReaver.setSpellTrigger(trigger);
		return felReaver;
	}
}
