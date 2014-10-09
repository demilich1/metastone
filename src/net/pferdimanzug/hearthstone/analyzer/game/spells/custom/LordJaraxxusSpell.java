package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons.BloodFury;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.LordJaraxxusHero;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class LordJaraxxusSpell extends DestroySpell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(LordJaraxxusSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		context.getLogic().changeHero(player, new LordJaraxxusHero());
		SpellDesc equipWeaponSpell = EquipWeaponSpell.create(new BloodFury());
		context.getLogic().castSpell(player.getId(), equipWeaponSpell);
		super.onCast(context, player, desc, target);
	}

}
