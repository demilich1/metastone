package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DarkCultist extends MinionCard {

	public DarkCultist() {
		super("Dark Cultist", 3, 4, Rarity.COMMON, HeroClass.PRIEST, 3);
		setDescription("Deathrattle: Give a random friendly minion +3 Health.");
	}

	@Override
	public int getTypeId() {
		return 387;
	}

	@Override
	public Minion summon() {
		Minion darkCultist = createMinion();
		SpellDesc randomBuffSpell = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, 0, 3, true);
		darkCultist.addDeathrattle(randomBuffSpell);
		return darkCultist;
	}
}
