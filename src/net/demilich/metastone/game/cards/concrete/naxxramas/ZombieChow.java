package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc healEnemyHero = HealingSpell.create(EntityReference.ENEMY_HERO, 5);
		zombieChow.addDeathrattle(healEnemyHero);
		return zombieChow;
	}
}
