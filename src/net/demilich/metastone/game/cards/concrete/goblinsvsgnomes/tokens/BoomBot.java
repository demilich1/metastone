package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.MinMaxDamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BoomBot extends MinionCard {

	public BoomBot() {
		super("Boom Bot", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Deathrattle: Deal 1-4 damage to a random enemy.");
		setRace(Race.MECH);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 590;
	}

	@Override
	public Minion summon() {
		Minion boomBot = createMinion();
		SpellDesc boom = MinMaxDamageSpell.create(EntityReference.ENEMY_CHARACTERS, 1, 4, true);
		boomBot.addDeathrattle(boom);

		return boomBot;
	}
}
