package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IfXDiedSummonYSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		feugen.setTag(GameTag.UNIQUE_MINION, UniqueMinion.FEUGEN);
		SpellDesc deathrattle = IfXDiedSummonYSpell.create(UniqueMinion.STALAGG, new Thaddius());
		deathrattle.setTarget(EntityReference.NONE);
		feugen.addDeathrattle(deathrattle);
		return feugen;
	}
}
