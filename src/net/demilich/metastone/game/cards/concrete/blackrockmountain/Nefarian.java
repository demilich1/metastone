package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Nefarian extends MinionCard {

	public Nefarian() {
		super("Nefarian", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Battlecry: Add 2 random spells to your hand (from your opponent's class).");
		setRace(Race.DRAGON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 640;
	}



	@Override
	public Minion summon() {
		Minion nefarian = createMinion();
//		SpellDesc copyOpponentSpells = DrawCardAndDoSomethingSpell.create((context, player) -> {
//			Player opponent = context.getOpponent(player);
//			return CardCatalogue.query(CardType.SPELL, null, opponent.getHero().getHeroClass()).getRandom();
//		}, (context, player, card) -> {
//			context.getLogic().receiveCard(player.getId(), card);
//		},
//		2);
//		BattlecryAction battlecry = BattlecryAction.createBattlecry(copyOpponentSpells);
//		nefarian.setBattlecry(battlecry);
		return nefarian;
	}
}
