package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.pickRandomTarget(true);
		destroySpell.setTargetFilter(entity -> ((Minion) entity).getAttack() <= 2);
		destroySpell.setTarget(EntityReference.ENEMY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(destroySpell);
		stampedingKodo.setBattlecry(battlecry);
		return stampedingKodo;
	}
}
