package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.MinionDamagedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Armorsmith extends MinionCard {

	public Armorsmith() {
		super("Armorsmith", 1, 4, Rarity.RARE, HeroClass.WARRIOR, 2);
		setDescription("Whenever a friendly minion takes damage, gain 1 Armor.");
	}

	@Override
	public int getTypeId() {
		return 361;
	}

	@Override
	public Minion summon() {
		Minion armorsmith = createMinion();
		SpellTrigger trigger = new SpellTrigger(new MinionDamagedTrigger(TargetPlayer.SELF), BuffHeroSpell.create(EntityReference.FRIENDLY_HERO, 0, 1));
		armorsmith.setSpellTrigger(trigger);
		return armorsmith;
	}
}
