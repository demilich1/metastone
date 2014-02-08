package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Nightblade extends MinionCard {
	
	public static final int BATTLECRY_DAMAGE = 3;

	public Nightblade() {
		super("Nightblade", 4, 4, Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion nightblade = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new DamageSpell(BATTLECRY_DAMAGE), TargetSelection.ENEMY_HERO);
		nightblade.setTag(GameTag.BATTLECRY, battlecry);
		return nightblade;
	}

}
