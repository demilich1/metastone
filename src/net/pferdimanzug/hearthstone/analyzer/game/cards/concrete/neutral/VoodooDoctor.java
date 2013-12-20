package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;

public class VoodooDoctor extends MinionCard {

	public VoodooDoctor() {
		super("Voodoo Doctor", Rarity.FREE, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Minion voodooDoctor = createMinion(2, 1);
		Battlecry battlecry = Battlecry.createBattlecry(new SingleTargetHealingSpell(2), TargetSelection.ANY);
		voodooDoctor.setTag(GameTag.BATTLECRY, battlecry);
		return voodooDoctor;
	}

}
