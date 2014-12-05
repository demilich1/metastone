package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

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

public class OneEyedCheat extends MinionCard {

	public OneEyedCheat() {
		super("One-eyed Cheat", 4, 1, Rarity.RARE, HeroClass.ROGUE, 2);
		setDescription("Whenever you summon a Pirate, gain Stealth.");
		setRace(Race.PIRATE);
	}

	@Override
	public int getTypeId() {
		return 571;
	}



	@Override
	public Minion summon() {
		Minion oneEyedCheat = createMinion();
		SpellDesc stealth = ApplyTagSpell.create(GameTag.STEALTHED);
		stealth.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF, Race.PIRATE), stealth);
		oneEyedCheat.setSpellTrigger(trigger);
		return oneEyedCheat;
	}
}
