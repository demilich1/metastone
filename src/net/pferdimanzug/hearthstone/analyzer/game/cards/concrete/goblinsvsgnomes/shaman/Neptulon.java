package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomRaceCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		Battlecry battlecry = Battlecry.createBattlecry(murlocs);
		neptulon.setBattlecry(battlecry);
		return neptulon;
	}
}
