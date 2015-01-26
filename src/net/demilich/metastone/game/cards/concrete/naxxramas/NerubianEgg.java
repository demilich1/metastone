package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.naxxramas.tokens.Nerubian;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class NerubianEgg extends MinionCard {

	public NerubianEgg() {
		super("Nerubian Egg", 0, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Deathrattle: Summon a 4/4 Nerubian.");
	}

	@Override
	public int getTypeId() {
		return 394;
	}

	@Override
	public Minion summon() {
		Minion nerubianEgg = createMinion();
		SpellDesc deathrattle = SummonSpell.create(new Nerubian());
		nerubianEgg.addDeathrattle(deathrattle);
		return nerubianEgg;
	}
}
