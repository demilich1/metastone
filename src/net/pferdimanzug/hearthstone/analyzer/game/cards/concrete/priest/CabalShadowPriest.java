package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MindControlSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CabalShadowPriest extends MinionCard {

	public CabalShadowPriest() {
		super("Cabal Shadow Priest", 4, 5, Rarity.EPIC, HeroClass.PRIEST, 6);
		setDescription("Battlecry: Take control of an enemy minion that has 2 or less Attack.");
	}
	
	@Override
	public Minion summon() {
		Minion cabalShadowPriest = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new MindControlSpell(), TargetSelection.ENEMY_MINIONS);
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
