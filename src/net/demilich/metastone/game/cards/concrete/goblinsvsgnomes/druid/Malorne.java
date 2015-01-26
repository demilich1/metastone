package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ShuffleMinionToDeckSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class Malorne extends MinionCard {

	public Malorne() {
		super("Malorne", 9, 7, Rarity.LEGENDARY, HeroClass.DRUID, 7);
		setDescription("Deathrattle: Shuffle this minion into your deck.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 480;
	}



	@Override
	public Minion summon() {
		Minion malorne = createMinion();
		SpellDesc shuffleToDeck = ShuffleMinionToDeckSpell.create(new Malorne());
		malorne.addDeathrattle(shuffleToDeck);
		return malorne;
	}
}
