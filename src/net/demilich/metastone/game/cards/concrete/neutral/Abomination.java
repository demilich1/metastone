package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Abomination extends MinionCard {

	public Abomination() {
		super("Abomination", 4, 4, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Taunt. Deathrattle: Deal 2 damage to ALL characters.");
	}

	@Override
	public int getTypeId() {
		return 76;
	}

	@Override
	public Minion summon() {
		Minion abomination = createMinion(GameTag.TAUNT);
		SpellDesc deathrattle = DamageSpell.create(2);
		deathrattle.setTarget(EntityReference.ALL_CHARACTERS);
		abomination.addDeathrattle(deathrattle);
		return abomination;
	}
}
