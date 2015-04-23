package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.heroes.RagnarosHero;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ChangeHeroSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MajordomoExecutus extends MinionCard {

	public MajordomoExecutus() {
		super("Majordomo Executus", 9, 7, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Deathrattle: Replace your hero with Ragnaros, the Firelord.");
	}

	@Override
	public Minion summon() {
		Minion majordomoExecutus = createMinion();
		SpellDesc summonRagnaros = ChangeHeroSpell.create(new RagnarosHero());
		majordomoExecutus.addDeathrattle(summonRagnaros);
		return majordomoExecutus;
	}



	@Override
	public int getTypeId() {
		return 639;
	}
}
