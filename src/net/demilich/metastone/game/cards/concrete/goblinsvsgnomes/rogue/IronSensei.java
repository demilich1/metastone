package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class IronSensei extends MinionCard {

	public IronSensei() {
		super("Iron Sensei", 2, 2, Rarity.RARE, HeroClass.ROGUE, 3);
		setDescription("At the end of your turn, give another friendly Mech +2/+2.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 569;
	}

	@Override
	public Minion summon() {
		Minion ironSensai = createMinion();
		Predicate<Entity> targetFilter = target -> target.getTag(GameTag.RACE) == Race.MECH;
		SpellDesc buffSpell = BuffSpell.create(EntityReference.OTHER_FRIENDLY_MINIONS, +2, +2, targetFilter, true);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), buffSpell);
		ironSensai.setSpellTrigger(trigger);
		return ironSensai;
	}
}
