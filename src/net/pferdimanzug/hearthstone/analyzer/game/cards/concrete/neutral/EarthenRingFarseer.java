package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetHealingSpell;

public class EarthenRingFarseer extends MinionCard {

	public EarthenRingFarseer() {
		super("Earthen Ring Farseer", Rarity.COMMON, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion earthenRingFarseer = createMinion(3, 3);
		Battlecry battlecry = Battlecry.createBattlecry(new SingleTargetHealingSpell(3), TargetRequirement.ANY);
		earthenRingFarseer.setTag(GameTag.BATTLECRY, battlecry);
		return earthenRingFarseer;
	}

}
