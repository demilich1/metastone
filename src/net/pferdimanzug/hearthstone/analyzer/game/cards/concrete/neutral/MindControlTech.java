package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MindControlRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MindControlTech extends MinionCard {

	public MindControlTech() {
		super("Mind Control Tech", 3, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: If your opponent has 4 or more minions, take control of one at random.");
	}

	@Override
	public Minion summon() {
		Minion mindControlTech = createMinion();
		Spell mindControlSpell = new MindControlRandomSpell();
		mindControlSpell.setTarget(EntityReference.ENEMY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(mindControlSpell);
		battlecry.setCondition((context, player)-> {
			Player opponent = context.getOpponent(player);
			return context.getMinionCount(opponent) >= 4;
		});
		mindControlTech.setBattlecry(battlecry);
		return mindControlTech;
	}

}
