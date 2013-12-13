package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class Nightblade extends MinionCard {
	
	public static final int BATTLECRY_DAMAGE = 3;

	public Nightblade() {
		super("Nightblade", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion nightblade = createMinion(4, 4);
		Battlecry battlecry = Battlecry.createBattlecry(new SingleTargetDamageSpell(BATTLECRY_DAMAGE), TargetRequirement.ENEMY_HERO);
		nightblade.setTag(GameTag.BATTLECRY, battlecry);
		return nightblade;
	}

}
