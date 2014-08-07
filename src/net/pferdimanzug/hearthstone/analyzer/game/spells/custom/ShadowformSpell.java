package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ChangeHeroPowerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
		changeHeroPowerSpell.setTarget(hero.getReference());
		context.getLogic().castSpell(player.getId(), changeHeroPowerSpell);
	}

	private class MindShatter extends HeroPower {

		public MindShatter() {
			super("Mind Shatter", HeroClass.PRIEST);
			setTargetRequirement(TargetSelection.ANY);
			setSpell(DamageSpell.create(3));
		}

	}

	private class MindSpike extends HeroPower {

		public MindSpike() {
			super("Mind Spike", HeroClass.PRIEST);
			setTargetRequirement(TargetSelection.ANY);
			setSpell(DamageSpell.create(2));
		}

	}

}
