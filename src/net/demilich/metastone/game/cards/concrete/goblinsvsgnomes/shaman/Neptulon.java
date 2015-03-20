package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ReceiveRandomRaceCardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class Neptulon extends MinionCard {

	public Neptulon() {
		super("Neptulon", 7, 7, Rarity.LEGENDARY, HeroClass.SHAMAN, 7);
		setDescription("Battlecry: Add 4 random Murlocs to your hand. Overload: (3)");
		setTag(GameTag.OVERLOAD, 3);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 578;
	}



	@Override
	public Minion summon() {
		Minion neptulon = createMinion();
		SpellDesc murlocs = ReceiveRandomRaceCardSpell.create(Race.MURLOC, 4);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(murlocs);
		neptulon.setBattlecry(battlecry);
		return neptulon;
	}
}
