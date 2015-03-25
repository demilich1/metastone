package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Demolisher extends MinionCard {

	public Demolisher() {
		super("Demolisher", 1, 4, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("At the start of your turn, deal 2 damage to a random enemy.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 116;
	}

	@Override
	public Minion summon() {
		Minion demolisher = createMinion();
		SpellDesc randomDamageSpell = DamageSpell.create(EntityReference.ENEMY_CHARACTERS, 2, true);
		SpellDesc removeStealth = RemoveAttributeSpell.create(EntityReference.SELF, GameTag.STEALTHED);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), MetaSpell.create(randomDamageSpell, removeStealth));
		demolisher.setSpellTrigger(trigger);
		return demolisher;
	}
}
