package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class HomingChicken extends MinionCard {

	public HomingChicken() {
		super("Homing Chicken", 0, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("At the start of your turn, destroy this minion and draw 3 cards.");

		setCollectible(false);
	}

	@Override
	public Minion summon() {
		Minion homingChicken = createMinion();
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.SELF);
		SpellDesc homingChickenSpell = MetaSpell.create(DrawCardSpell.create(3), destroySpell);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), homingChickenSpell);
		homingChicken.setSpellTrigger(trigger);
		return homingChicken;
	}

	@Override
	public int getTypeId() {
		return 442;
	}
}
