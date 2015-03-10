package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.PutRandomMinionOnBoardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class Deathlord extends MinionCard {

	public Deathlord() {
		super("Deathlord", 2, 8, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Taunt. Deathrattle: Your opponent puts a minion from their deck into the battlefield.");
	}

	@Override
	public int getTypeId() {
		return 405;
	}

	@Override
	public Minion summon() {
		Minion deathlord = createMinion(GameTag.TAUNT);
		SpellDesc deathlordSpell = PutRandomMinionOnBoardSpell.create(null, CardLocation.DECK);
		deathlordSpell.setTargetPlayer(TargetPlayer.OPPONENT);
		deathlord.addDeathrattle(deathlordSpell);
		return deathlord;
	}

}
