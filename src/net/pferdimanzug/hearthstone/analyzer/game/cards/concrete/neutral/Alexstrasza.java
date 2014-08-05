package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alexstrasza extends MinionCard {
	
	private static final Logger logger = LoggerFactory.getLogger(AlexstraszaSpell.class);

	public Alexstrasza() {
		super("Alexstrasza", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Battlecry: Set a hero's remaining Health to 15.");
		setRace(Race.DRAGON);
	}

	@Override
	public int getTypeId() {
		return 81;
	}
	
	@Override
	public Minion summon() {
		Minion alexstrasza = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new AlexstraszaSpell(), TargetSelection.HEROES);
		alexstrasza.setBattlecry(battlecry);
		return alexstrasza;
	}

	private class AlexstraszaSpell extends Spell {
		
		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Actor actor = (Actor) target;
			actor.setHp(15);
			logger.debug("{}'s Hp have been set to {}", actor, actor.getHp());
		}
	}
}
