package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc damageEnemyHero = DamageSpell.create(BATTLECRY_DAMAGE);
		damageEnemyHero.setTarget(EntityReference.ENEMY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(damageEnemyHero);
		nightblade.setBattlecry(battlecry);
		return nightblade;
	}
}
