package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Nightblade extends MinionCard {

	public static final int BATTLECRY_DAMAGE = 3;

	public Nightblade() {
		super("Nightblade", 4, 4, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Battlecry: Deal 3 damage to the enemy hero.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 175;
	}

	@Override
	public Minion summon() {
		Minion nightblade = createMinion();
		Spell damageEnemyHero = new DamageSpell(BATTLECRY_DAMAGE);
		damageEnemyHero.setTarget(EntityReference.ENEMY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(damageEnemyHero);
		nightblade.setBattlecry(battlecry);
		return nightblade;
	}
}
