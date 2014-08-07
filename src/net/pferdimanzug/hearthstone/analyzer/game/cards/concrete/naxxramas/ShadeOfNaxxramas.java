package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ShadeOfNaxxramas extends MinionCard {

	public ShadeOfNaxxramas() {
		super("Shade of Naxxramas", 2, 2, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("Stealth. At the start of your turn, gain +1/+1");
	}

	@Override
	public int getTypeId() {
		return 397;
	}

	@Override
	public Minion summon() {
		Minion shadeOfNaxxramas = createMinion(GameTag.STEALTHED);
		SpellDesc buffSpell = BuffSpell.create(1, 1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), buffSpell);
		shadeOfNaxxramas.setSpellTrigger(trigger);
		return shadeOfNaxxramas;
	}
}
