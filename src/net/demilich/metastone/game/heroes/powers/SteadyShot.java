package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SteadyShot extends HeroPower {

	public static final int DAMAGE = 2;

	public SteadyShot() {
		super("Steady Shot", HeroClass.HUNTER);
		setTargetRequirement(TargetSelection.NONE);
		SpellDesc damage = DamageSpell.create(EntityReference.ENEMY_HERO, DAMAGE);
		setSpell(damage);
	}

	@Override
	public void onWillUse(GameContext context, Player player) {
		super.onWillUse(context, player);
		if (context.getLogic().hasTag(player, GameTag.HERO_POWER_CAN_TARGET_MINIONS)) {
			setTargetRequirement(TargetSelection.ANY);
			//getSpell().setTarget(null);
		} else {
			setTargetRequirement(TargetSelection.NONE);
			//getSpell().setTarget(EntityReference.ENEMY_HERO);
		}
	}
	

}
