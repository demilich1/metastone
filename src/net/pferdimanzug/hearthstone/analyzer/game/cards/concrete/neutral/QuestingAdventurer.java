package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.CardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class QuestingAdventurer extends MinionCard {

	public QuestingAdventurer() {
		super("Questing Adventurer", 2, 2, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Whenever you play a card, gain +1/+1.");
	}

	@Override
	public int getTypeId() {
		return 184;
	}

	@Override
	public Minion summon() {
		Minion questingAdventurer = createMinion();
		SpellDesc buffSpell = BuffSpell.create(1, 1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(TargetPlayer.SELF), buffSpell);
		questingAdventurer.setSpellTrigger(trigger);
		return questingAdventurer;
	}
}
