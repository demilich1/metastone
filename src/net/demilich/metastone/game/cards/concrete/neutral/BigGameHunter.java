package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BigGameHunter extends MinionCard {

	public BigGameHunter() {
		super("Big Game Hunter", 4, 2, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("Battlecry: Destroy a minion with an Attack of 7 or more.");
	}

	@Override
	public int getTypeId() {
		return 93;
	}



	@Override
	public Minion summon() {
		Minion bigGameHunter = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DestroySpell.create(), TargetSelection.MINIONS);
		battlecry.setEntityFilter(entity -> {
			if (entity.getEntityType() != EntityType.MINION) {
				return false;
			}
			Minion minion = (Minion) entity;
			return minion.getAttack() >= 7;
		});
		bigGameHunter.setBattlecry(battlecry);
		return bigGameHunter;
	}
}
