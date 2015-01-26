package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddSpellTriggerSpell;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Nightmare extends SpellCard {

	public Nightmare() {
		super("Nightmare", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Give a minion +5/+5. At the start of your next turn, destroy it.");

		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), destroySpell, true);
		setSpell(MetaSpell.create(BuffSpell.create(5, 5), AddSpellTriggerSpell.create(trigger), ApplyTagSpell.create(GameTag.MARKED_FOR_DEATH)));
		setTargetRequirement(TargetSelection.MINIONS);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 466;
	}
}
