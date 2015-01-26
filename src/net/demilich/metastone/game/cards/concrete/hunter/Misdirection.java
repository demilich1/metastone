package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.spells.custom.MisdirectSpell;
import net.demilich.metastone.game.spells.trigger.TargetAcquisitionTrigger;

public class Misdirection extends SecretCard {

	public Misdirection() {
		super("Misdirection", Rarity.RARE, HeroClass.HUNTER, 2);
		setDescription("Secret: When a character attacks your hero, instead he attacks another random character.");

		setTriggerAndEffect(new MisdirectionTrigger(), MisdirectSpell.create());
	}

	@Override
	public int getTypeId() {
		return 40;
	}

	private class MisdirectionTrigger extends TargetAcquisitionTrigger {

		public MisdirectionTrigger() {
			super(ActionType.PHYSICAL_ATTACK);
		}

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}

			GameContext context = event.getGameContext();
			if (context.getMinionCount(context.getPlayer1()) == 0 && context.getMinionCount(context.getPlayer2()) == 0) {
				return false;
			}

			TargetAcquisitionEvent targetAcquisitionEvent = (TargetAcquisitionEvent) event;
			return targetAcquisitionEvent.getTarget().getEntityType() == EntityType.HERO;
		}

	}
	
}
