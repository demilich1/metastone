package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class InjuredBlademaster extends MinionCard {

	public InjuredBlademaster() {
		super("Injured Blademaster", 4, 7, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: Deal 4 damage to HIMSELF.");
	}

	@Override
	public Minion summon() {
		Minion injuredBlademaster = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new DamageSpell(4), TargetSelection.SELF);
		injuredBlademaster.setBattlecry(battlecry);
		return injuredBlademaster;
	}

}
