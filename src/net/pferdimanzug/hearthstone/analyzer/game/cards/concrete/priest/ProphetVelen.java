package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ProphetVelen extends MinionCard {

	public ProphetVelen() {
		super("Prophet Velen", 7, 7, Rarity.LEGENDARY, HeroClass.PRIEST, 7);
		setDescription("Double the damage and healing of your spells and Hero Power.");
	}

	@Override
	public int getTypeId() {
		return 276;
	}



	@Override
	public Minion summon() {
		Minion prophetVelen = createMinion();
		prophetVelen.setTag(GameTag.SPELL_AMPLIFY_MULTIPLIER, 1);
		return prophetVelen;
	}
}
