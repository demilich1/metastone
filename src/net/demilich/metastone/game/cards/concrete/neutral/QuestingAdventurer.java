package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.CardPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
