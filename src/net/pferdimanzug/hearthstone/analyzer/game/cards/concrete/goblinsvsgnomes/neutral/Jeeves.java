package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.JeevesSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

public class Jeeves extends MinionCard {

	public Jeeves() {
		super("Jeeves", 1, 4, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("At the end of each player's turn, that player draws until they have 3 cards.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion jeeves = createMinion();
		SpellDesc drawCardSpell = JeevesSpell.create();
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(TargetPlayer.BOTH), drawCardSpell);
		jeeves.setSpellTrigger(trigger);
		return jeeves;
	}

}
