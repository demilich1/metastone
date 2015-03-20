package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.PutRandomMinionOnBoardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class Voidcaller extends MinionCard {

	public Voidcaller() {
		super("Voidcaller", 3, 4, Rarity.COMMON, HeroClass.WARLOCK, 4);
		setDescription("Deathrattle: Put a random Demon from your hand into the battlefield.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 411;
	}

	@Override
	public Minion summon() {
		Minion voidcaller = createMinion();
		SpellDesc voidcallerSpell = PutRandomMinionOnBoardSpell.create(Race.DEMON, CardLocation.HAND);
		voidcaller.addDeathrattle(voidcallerSpell);
		return voidcaller;
	}

}
