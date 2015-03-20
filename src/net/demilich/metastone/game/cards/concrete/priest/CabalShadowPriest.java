package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.MindControlSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class CabalShadowPriest extends MinionCard {

	public CabalShadowPriest() {
		super("Cabal Shadow Priest", 4, 5, Rarity.EPIC, HeroClass.PRIEST, 6);
		setDescription("Battlecry: Take control of an enemy minion that has 2 or less Attack.");
	}
	
	@Override
	public int getTypeId() {
		return 260;
	}

	@Override
	public Minion summon() {
		Minion cabalShadowPriest = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MindControlSpell.create(null, false), TargetSelection.ENEMY_MINIONS);
		battlecry.setEntityFilter(entity -> {
			if (entity.getEntityType() != EntityType.MINION) {
				return false;
			}
			Minion minion = (Minion) entity;
			return minion.getAttack() <= 2;
		});
		cabalShadowPriest.setBattlecry(battlecry);
		return cabalShadowPriest;
	}
}
