package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class InnerFire extends SpellCard {

	private class InnerFireSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Actor targetActor = (Actor) target;
			int buffAmount = targetActor.getHp() - targetActor.getAttack();
			target.modifyTag(GameTag.ATTACK_BONUS, buffAmount);
		}
		
	}
	
	public InnerFire() {
		super("Inner Fire", Rarity.COMMON, HeroClass.PRIEST, 1);
		setDescription("Change a minion's Attack to be equal to its Health.");
		setSpell(new InnerFireSpell());
		setTargetRequirement(TargetSelection.MINIONS);
		
	}

}
