package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue;

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
		SpellDesc stealth = AddAttributeSpell.create(EntityReference.SELF, GameTag.STEALTH);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF, Race.PIRATE), stealth);
		oneEyedCheat.setSpellTrigger(trigger);
		return oneEyedCheat;
	}
}
