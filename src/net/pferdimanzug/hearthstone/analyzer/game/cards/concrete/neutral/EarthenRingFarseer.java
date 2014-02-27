package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class EarthenRingFarseer extends MinionCard {

	public EarthenRingFarseer() {
		super("Earthen Ring Farseer", 3, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Battlecry: Restore 3 Health.");
	}

	@Override
	public Minion summon() {
		Minion earthenRingFarseer = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new HealingSpell(3), TargetSelection.ANY);
		earthenRingFarseer.setBattlecry(battlecry);
		return earthenRingFarseer;
	}

}
