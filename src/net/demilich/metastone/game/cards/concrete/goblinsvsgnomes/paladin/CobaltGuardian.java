package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class CobaltGuardian extends MinionCard {

	public CobaltGuardian() {
		super("Cobalt Guardian", 6, 3, Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Whenever you summon a Mech, gain Divine Shield.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 552;
	}



	@Override
	public Minion summon() {
		Minion cobaltGuardian = createMinion();
		SpellDesc gainDivineShield = AddAttributeSpell.create(EntityReference.SELF, GameTag.DIVINE_SHIELD);
		//SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF, Race.MECH), gainDivineShield);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(null), gainDivineShield);
		cobaltGuardian.setSpellTrigger(trigger);
		return cobaltGuardian;
	}
}
