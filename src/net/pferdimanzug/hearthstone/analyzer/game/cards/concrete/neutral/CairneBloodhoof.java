package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.BaineBloodhoof;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class CairneBloodhoof extends MinionCard {

	public CairneBloodhoof() {
		super("Cairne Bloodhoof", 4, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Deathrattle: Summon a 4/5 Baine Bloodhoof.");
	}

	@Override
	public int getTypeId() {
		return 102;
	}

	@Override
	public Minion summon() {
		Minion cairneBloodhoof = createMinion();
		cairneBloodhoof.addDeathrattle(SummonSpell.create(new BaineBloodhoof()));
		return cairneBloodhoof;
	}
}
