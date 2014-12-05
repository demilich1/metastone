package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MinMaxDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc boom = MinMaxDamageSpell.create(1, 4);
		boom.pickRandomTarget(true);
		boom.setTarget(EntityReference.ENEMY_CHARACTERS);
		boomBot.addDeathrattle(boom);

		return boomBot;
	}
}
