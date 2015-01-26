package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SecretPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Secretkeeper extends MinionCard {

	public Secretkeeper() {
		super("Secretkeeper", 1, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Whenever a Secret is played, gain +1/+1.");
	}

	@Override
	public int getTypeId() {
		return 194;
	}

	@Override
	public Minion summon() {
		Minion secretkeeper = createMinion();
		SpellDesc buffSpell = BuffSpell.create(1, 1);
		buffSpell.setTarget(EntityReference.SELF);
		secretkeeper.setSpellTrigger(new SpellTrigger(new SecretPlayedTrigger(), buffSpell));
		return secretkeeper;
	}
}
