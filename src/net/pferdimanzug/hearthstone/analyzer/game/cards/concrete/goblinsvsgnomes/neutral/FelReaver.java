package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DiscardCardsFromDeckSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.CardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

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
		SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(TargetPlayer.OPPONENT), discard);
		felReaver.setSpellTrigger(trigger);
		return felReaver;
	}
}
