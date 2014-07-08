package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ChangeHeroPowerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shadowform extends SpellCard {

	public Shadowform() {
		super("Shadowform", Rarity.EPIC, HeroClass.PRIEST, 3);
		setDescription("Your Hero Power becomes 'Deal 2 damage'. If already in Shadowform: 3 damage.");
		
		setSpell(new ShadowformSpell());
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class MindSpike extends HeroPower {

		public MindSpike() {
			super("Mind Spike", HeroClass.PRIEST);
			setTargetRequirement(TargetSelection.ANY);
			setSpell(new DamageSpell(2));
		}
		
	}
	
	private class MindShatter extends HeroPower {

		public MindShatter() {
			super("Mind Shatter", HeroClass.PRIEST);
			setTargetRequirement(TargetSelection.ANY);
			setSpell(new DamageSpell(3));
		}
		
	}
	
	private class ShadowformSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Hero hero = (Hero) target;
			HeroPower newHeroPower = hero.getHeroPower() instanceof MindSpike ? new MindShatter() : new MindSpike();
			ChangeHeroPowerSpell changeHeroPowerSpell = new ChangeHeroPowerSpell(newHeroPower);
			changeHeroPowerSpell.setTarget(hero.getReference());
			context.getLogic().castSpell(player.getId(), changeHeroPowerSpell);
		}
		
	}


}
