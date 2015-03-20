package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DestroyWeaponSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BladeFlurry extends SpellCard {

	public BladeFlurry() {
		super("Blade Flurry", Rarity.RARE, HeroClass.ROGUE, 2);
		setDescription("Destroy your weapon and deal its damage to all enemies.");

		SpellDesc damageSpell = DamageSpell.create(EntityReference.ENEMY_CHARACTERS, (context, player, target) -> player.getHero().getWeapon().getWeaponDamage());
		SpellDesc destroyWeaponSpell = DestroyWeaponSpell.create(EntityReference.FRIENDLY_HERO);
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
