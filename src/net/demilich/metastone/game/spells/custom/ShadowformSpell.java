package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.heroes.powers.MindShatter;
import net.demilich.metastone.game.heroes.powers.MindSpike;
import net.demilich.metastone.game.spells.ChangeHeroPowerSpell;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ShadowformSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ShadowformSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Hero hero = (Hero) target;
		HeroPower newHeroPower = hero.getHeroPower() instanceof MindSpike ? new MindShatter() : new MindSpike();
		SpellDesc changeHeroPowerSpell = ChangeHeroPowerSpell.create(newHeroPower);
		changeHeroPowerSpell.setSourceEntity(desc.getSourceEntity());
		changeHeroPowerSpell.setTarget(hero.getReference());
		context.getLogic().castSpell(player.getId(), changeHeroPowerSpell);
	}

}
