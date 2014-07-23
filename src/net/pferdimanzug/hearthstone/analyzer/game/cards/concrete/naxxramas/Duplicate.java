package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Duplicate extends SecretCard {

	public Duplicate() {
		super("Duplicate", Rarity.COMMON, HeroClass.MAGE, 3);
		setDescription("Secret: When a friendly minion dies, put 2 copies of it in your hand.");

		Spell duplicate = new DuplicateSpell();
		duplicate.setTarget(EntityReference.EVENT_TARGET);
		setTriggerAndEffect(new MinionDeathTrigger(TargetPlayer.SELF), duplicate);
	}

	private class DuplicateSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			System.out.println("Casting duplicate on " + target + " spell target is " + getTarget());
			Minion minion = (Minion) target;

			Card sourceCard = minion.getSourceCard();
			context.getLogic().receiveCard(player.getId(), sourceCard.clone());
			context.getLogic().receiveCard(player.getId(), sourceCard.clone());
		}
		
	}

	@Override
	public int getTypeId() {
		return 389;
	}
}
