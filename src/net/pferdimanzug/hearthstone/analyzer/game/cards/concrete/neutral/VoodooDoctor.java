package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;

public class VoodooDoctor extends MinionCard {

	public VoodooDoctor() {
		super("Voodoo Doctor", Rarity.FREE, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Minion voodooDoctor = createMinion(2, 1);
		Battlecry battlecry = Battlecry.createBattlecry(new SingleTargetHealingSpell(2), TargetRequirement.ANY);
		voodooDoctor.setTag(GameTag.BATTLECRY, battlecry);
		return voodooDoctor;
	}

}
