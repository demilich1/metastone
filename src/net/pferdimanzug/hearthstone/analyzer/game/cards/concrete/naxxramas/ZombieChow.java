package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ZombieChow extends MinionCard {

	public ZombieChow() {
		super("Zombie Chow", 2, 3, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Deathrattle: Restore 5 Health to the enemy hero.");
	}

	@Override
	public int getTypeId() {
		return 404;
	}



	@Override
	public Minion summon() {
		Minion zombieChow = createMinion();
		Spell healEnemyHero = new HealingSpell(5);
		healEnemyHero.setTarget(EntityReference.ENEMY_HERO);
		zombieChow.addDeathrattle(healEnemyHero);
		return zombieChow;
	}
}
