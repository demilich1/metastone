package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndAndControlSecretTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class EtherealArcanist extends MinionCard {

	public EtherealArcanist() {
		super("Ethereal Arcanist", 3, 3, Rarity.RARE, HeroClass.MAGE, 4);
		setDescription("If you control a Secret at the end of your turn, gain +2/+2.");
	}

	@Override
	public int getTypeId() {
		return 58;
	}

	@Override
	public Minion summon() {
		Minion etherealArcanist = createMinion();
		SpellDesc buffSpell = BuffSpell.create(2, 2);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndAndControlSecretTrigger(), buffSpell);
		etherealArcanist.setSpellTrigger(trigger);
		return etherealArcanist;
	}
}
