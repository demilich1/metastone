package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

public class DancingSwords extends MinionCard {

	public DancingSwords() {
		super("Dancing Swords", 4, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Deathrattle: Your opponent draws a card.");
	}

	@Override
	public int getTypeId() {
		return 386;
	}

	@Override
	public Minion summon() {
		Minion dancingSwords = createMinion();
		Spell drawCardSpell = new DrawCardSpell(1, TargetPlayer.OPPONENT);
		dancingSwords.addDeathrattle(drawCardSpell);
		return dancingSwords;
	}
}
