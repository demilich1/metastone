package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class HomingChicken extends MinionCard {

	public HomingChicken() {
		super("Homing Chicken", 0, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("At the start of your turn, destroy this minion and draw 3 cards.");
		setRace(Race.MECH);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 442;
	}

	@Override
	public Minion summon() {
		Minion homingChicken = createMinion();
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.SELF);
		SpellDesc homingChickenSpell = MetaSpell.create(DrawCardSpell.create(3), destroySpell);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), homingChickenSpell, true);
		homingChicken.setSpellTrigger(trigger);
		return homingChicken;
	}
}
