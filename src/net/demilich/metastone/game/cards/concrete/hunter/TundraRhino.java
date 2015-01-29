package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.AuraApplyTag;
import net.demilich.metastone.game.targeting.EntityReference;

public class TundraRhino extends MinionCard {

	public TundraRhino() {
		super("Tundra Rhino", 2, 5, Rarity.FREE, HeroClass.HUNTER, 5);
		setDescription("Your Beasts have Charge.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 49;
	}

	@Override
	public Minion summon() {
		Minion tundraRhino = createMinion(GameTag.CHARGE);
		Aura aura = new AuraApplyTag(GameTag.CHARGE, EntityReference.FRIENDLY_MINIONS);
		aura.setRaceRestriction(Race.BEAST);
		tundraRhino.setSpellTrigger(aura);
		return tundraRhino;
	}
}
