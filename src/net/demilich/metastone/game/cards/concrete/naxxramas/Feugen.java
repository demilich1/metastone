package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.cards.concrete.naxxramas.tokens.Thaddius;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.IfXDiedSummonYSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class Feugen extends MinionCard {

	public Feugen() {
		super("Feugen", 4, 7, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Deathrattle: If Stalagg also died this game, summon Thaddius.");
	}

	@Override
	public int getTypeId() {
		return 406;
	}

	@Override
	public Minion summon() {
		Minion feugen = createMinion();
		feugen.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.FEUGEN);
		SpellDesc deathrattle = IfXDiedSummonYSpell.create(UniqueEntity.STALAGG, new Thaddius());
		feugen.addDeathrattle(deathrattle);
		return feugen;
	}
}
