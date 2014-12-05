package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas.tokens.Thaddius;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IfXDiedSummonYSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
