package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.concrete.tokens.weapons.BloodFury;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.LordJaraxxusHero;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class LordJaraxxusSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(LordJaraxxusSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		context.getLogic().markAsDestroyed((Actor) target);
		context.getLogic().changeHero(player, new LordJaraxxusHero());
		context.getLogic().equipWeapon(player.getId(), new BloodFury().getWeapon());
	}

}
