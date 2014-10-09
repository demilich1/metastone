package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Emboldener3000 extends MinionCard {

	public Emboldener3000() {
		super("Emboldener 3000", 0, 4, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("At the end of your turn, give a random minion +1/+1.");
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		Minion emboldener3000 = createMinion();
		SpellDesc buffSpell = BuffRandomSpell.create(1, 1);
		buffSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), buffSpell);
		emboldener3000.setSpellTrigger(trigger);
		return emboldener3000;
	}

}