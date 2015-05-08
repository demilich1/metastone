package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.MissilesSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.AfterSpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Flamewaker extends MinionCard {

	public Flamewaker() {
		super("Flamewaker", 2, 4, Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("After you cast a spell, deal 2 damage randomly split among all enemies.");
	}

	@Override
	public Minion summon() {
		Minion flamewaker = createMinion();
		SpellDesc spell = MissilesSpell.create(EntityReference.ENEMY_CHARACTERS, 1, 2);
		//SpellTrigger trigger = new SpellTrigger(new AfterSpellCastedTrigger(TargetPlayer.SELF), spell);
		SpellTrigger trigger = new SpellTrigger(new AfterSpellCastedTrigger(null), spell);
		flamewaker.setSpellTrigger(trigger);
		return flamewaker;
	}

	@Override
	public int getTypeId() {
		return 636;
	}
}
