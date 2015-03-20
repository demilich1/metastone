package net.demilich.metastone.game.cards.concrete.warlock;

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
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PowerOverwhelming extends SpellCard {

	public PowerOverwhelming() {
		super("Power Overwhelming", Rarity.COMMON, HeroClass.WARLOCK, 1);
		setDescription("Give a friendly minion +4/+4 until end of turn. Then, it dies. Horribly.");
		SpellDesc destroySpell = DestroySpell.create(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), destroySpell);
		setSpell(MetaSpell.create(null, BuffSpell.create(4, 4), AddSpellTriggerSpell.create(trigger), ApplyTagSpell.create(GameTag.MARKED_FOR_DEATH), false));
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 347;
	}
}
