package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroyWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AcidicSwampOoze extends MinionCard {

	public AcidicSwampOoze() {
		super("Acidic Swamp Ooze", 3, 2, Rarity.FREE, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion acidicSwampOoze = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new DestroyWeaponSpell(), TargetSelection.ENEMY_HERO);
		acidicSwampOoze.setTag(GameTag.BATTLECRY, battlecry);
		return acidicSwampOoze;
	}
}
