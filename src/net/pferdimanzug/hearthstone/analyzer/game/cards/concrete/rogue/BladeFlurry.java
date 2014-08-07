package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroyWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BladeFlurry extends SpellCard {

	public BladeFlurry() {
		super("Blade Flurry", Rarity.RARE, HeroClass.ROGUE, 2);
		setDescription("Destroy your weapon and deal its damage to all enemies.");

		SpellDesc damageSpell = DamageSpell.create((context, player, target) -> player.getHero().getWeapon().getWeaponDamage());
		damageSpell.setTarget(EntityReference.ENEMY_CHARACTERS);
		SpellDesc destroyWeaponSpell = DestroyWeaponSpell.create();
		destroyWeaponSpell.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(MetaSpell.create(damageSpell, destroyWeaponSpell));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (!super.canBeCast(context, player)) {
			return false;
		}
		return player.getHero().getWeapon() != null;
	}



	@Override
	public int getTypeId() {
		return 288;
	}
}
