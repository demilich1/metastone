package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.CardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class QuestingAdventurer extends MinionCard {

	public QuestingAdventurer() {
		super("Questing Adventurer", 2, 2, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Whenever you play a card, gain +1/+1.");
	}

	@Override
	public Minion summon() {
		Minion questingAdventurer = createMinion();
		SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(TargetPlayer.SELF), new BuffSpell(1, 1));
		questingAdventurer.setSpellTrigger(trigger);
		return questingAdventurer;
	}

}
