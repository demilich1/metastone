package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class KorkronElite extends MinionCard {

	public KorkronElite() {
		super("Kor'kron Elite", 4, 3, Rarity.FREE, HeroClass.WARRIOR, 4);
		setDescription("Charge");
	}

	@Override
	public int getTypeId() {
		return 375;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
