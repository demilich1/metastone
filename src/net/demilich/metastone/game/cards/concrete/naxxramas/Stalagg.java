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
import net.demilich.metastone.game.targeting.EntityReference;

public class Stalagg extends MinionCard {

	public Stalagg() {
		super("Stalagg", 7, 4, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Deathrattle: If Feugan also died this game, summon Thaddius.");
	}

	@Override
	public int getTypeId() {
		return 409;
	}

	@Override
	public Minion summon() {
		Minion stalagg = createMinion();
		stalagg.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.STALAGG);
		SpellDesc deathrattle = IfXDiedSummonYSpell.create(UniqueEntity.FEUGEN, new Thaddius());
		deathrattle.setTarget(EntityReference.NONE);
		stalagg.addDeathrattle(deathrattle);
		return stalagg;
	}
}
