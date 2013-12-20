package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class FireElemental extends MinionCard {
	
	private static final int BATTLECRY_DAMAGE = 3;
	
	public FireElemental() {
		super("Fire Elemental", Rarity.FREE, HeroClass.SHAMAN, 6);
	}

	@Override
	public Minion summon() {
		Minion fireElemental = createMinion(6, 5);
		Battlecry battlecry = Battlecry.createBattlecry(new SingleTargetDamageSpell(BATTLECRY_DAMAGE), TargetSelection.ANY);
		fireElemental.setTag(GameTag.BATTLECRY, battlecry);
		return fireElemental;
	}

}
