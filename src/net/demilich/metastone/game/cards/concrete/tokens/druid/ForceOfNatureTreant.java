package net.demilich.metastone.game.cards.concrete.tokens.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class ForceOfNatureTreant extends MinionCard {

	public ForceOfNatureTreant() {
		super("Treant", 2, 2, Rarity.COMMON, HeroClass.DRUID, 1);
		setDescription("Charge. At the end of the turn, destroy this minion.");
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 417;
	}

	@Override
	public Minion summon() {
		Minion treant = createMinion(GameTag.CHARGE);
		treant.setTag(GameTag.MARKED_FOR_DEATH);
		SpellDesc destroySpell = DestroySpell.create(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), destroySpell);
		treant.setSpellTrigger(trigger);
		return treant;
	}
}
