package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Doomsayer extends MinionCard {

	public Doomsayer() {
		super("Doomsayer", 0, 7, Rarity.EPIC, HeroClass.ANY, 2);
		setDescription("At the start of your turn, destroy ALL minions.");
	}

	@Override
	public int getTypeId() {
		return 118;
	}

	@Override
	public Minion summon() {
		Minion doomsayer = createMinion();
		SpellDesc endOfWorldSpell = DestroySpell.create(EntityReference.ALL_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(null), endOfWorldSpell);
		doomsayer.setSpellTrigger(trigger);
		return doomsayer;
	}
}
