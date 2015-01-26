package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
