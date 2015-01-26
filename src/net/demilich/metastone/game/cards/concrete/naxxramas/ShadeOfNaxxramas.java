package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
