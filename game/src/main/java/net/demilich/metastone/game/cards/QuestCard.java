package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.desc.QuestCardDesc;
import net.demilich.metastone.game.spells.AddQuestSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.types.Quest;
import net.demilich.metastone.game.targeting.TargetSelection;

public class QuestCard extends SpellCard {

	public QuestCard(QuestCardDesc desc) {
		super(desc);
		GameEventTrigger trigger = desc.quest.create();
		setQuest(new Quest(trigger, desc.spell, this));
		setAttribute(Attribute.QUEST);
	}

	public boolean canBeCast(GameContext context, Player player) {
		return context.getLogic().canPlayQuest(player, this);
	}

	public void setQuest(Quest quest) {
		SpellDesc spell = AddQuestSpell.create(quest);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(spell);
	}

}
