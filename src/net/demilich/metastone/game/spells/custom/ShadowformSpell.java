package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.heroes.powers.MindShatter;
import net.demilich.metastone.game.heroes.powers.MindSpike;
import net.demilich.metastone.game.spells.ChangeHeroPowerSpell;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ShadowformSpell extends Spell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(ShadowformSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.FRIENDLY_HERO);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Hero hero = (Hero) target;
		HeroPower newHeroPower = hero.getHeroPower() instanceof MindSpike ? new MindShatter() : new MindSpike();
		SpellDesc changeHeroPowerSpell = ChangeHeroPowerSpell.create(newHeroPower);
		EntityReference sourceReference = source != null ? source.getReference() : null;
		context.getLogic().castSpell(player.getId(), changeHeroPowerSpell, sourceReference, hero.getReference());
	}

}
