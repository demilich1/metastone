package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FireElemental extends MinionCard {
	
	private static final int BATTLECRY_DAMAGE = 3;
	
	public FireElemental() {
		super("Fire Elemental", 6, 5, Rarity.FREE, HeroClass.SHAMAN, 6);
		setDescription("Battlecry: Deal 3 damage.");
	}

	@Override
	public Minion summon() {
		Minion fireElemental = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new DamageSpell(BATTLECRY_DAMAGE), TargetSelection.ANY);
		fireElemental.setTag(GameTag.BATTLECRY, battlecry);
		return fireElemental;
	}

}
