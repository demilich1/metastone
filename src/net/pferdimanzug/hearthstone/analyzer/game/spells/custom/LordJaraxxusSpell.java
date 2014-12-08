package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons.BloodFury;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.LordJaraxxusHero;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
