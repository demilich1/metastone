package net.demilich.metastone.game.cards.concrete.neutral;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class StampedingKodo extends MinionCard {

	public StampedingKodo() {
		super("Stampeding Kodo", 3, 5, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Battlecry: Destroy a random enemy minion with 2 or less Attack.");
		setRace(Race.BEAST);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 205;
	}

	@Override
	public Minion summon() {
		Minion stampedingKodo = createMinion();
		Predicate<Entity> targetFilter = entity -> ((Minion) entity).getAttack() <= 2;
		SpellDesc destroySpell = DestroySpell.create(EntityReference.ENEMY_MINIONS, targetFilter, true);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(destroySpell);
		stampedingKodo.setBattlecry(battlecry);
		return stampedingKodo;
	}
}
