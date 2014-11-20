package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class CobaltGuardian extends MinionCard {

	public CobaltGuardian() {
		super("Cobalt Guardian", 6, 3, Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Whenever you summon a Mech, gain Divine Shield.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion cobaltGuardian = createMinion();
		SpellDesc gainDivineShield = ApplyTagSpell.create(GameTag.DIVINE_SHIELD);
		gainDivineShield.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF, Race.MECH), gainDivineShield);
		cobaltGuardian.setSpellTrigger(trigger);
		return cobaltGuardian;
	}

}
