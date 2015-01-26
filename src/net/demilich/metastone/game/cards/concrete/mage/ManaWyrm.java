package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class ManaWyrm extends MinionCard {

	public ManaWyrm() {
		super("Mana Wyrm", 1, 3, Rarity.COMMON, HeroClass.MAGE, 1);
		setDescription("Whenever you cast a spell, gain +1 Attack.");
	}

	@Override
	public int getTypeId() {
		return 67;
	}

	@Override
	public Minion summon() {
		Minion manaWyrm = createMinion();
		SpellDesc buffSpell = BuffSpell.create(1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), buffSpell);
		manaWyrm.setSpellTrigger(trigger);
		return manaWyrm;
	}
}
