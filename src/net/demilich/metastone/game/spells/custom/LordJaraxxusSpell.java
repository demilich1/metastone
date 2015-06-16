package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.LordJaraxxusHero;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class LordJaraxxusSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(LordJaraxxusSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.SELF);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		context.getLogic().markAsDestroyed((Actor) target);
		context.getLogic().changeHero(player, new LordJaraxxusHero());
		WeaponCard bloodfury = (WeaponCard) CardCatalogue.getCardById("weapon_bloodfury");
		context.getLogic().equipWeapon(player.getId(), bloodfury.getWeapon());
	}

}
