package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AlarmOBot extends MinionCard {

	private class AlarmOBotSpell extends ReturnMinionToHandSpell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			// return Alarm-o-bot to hand
			super.onCast(context, player, target);
			// summon a random minion and remove the corresponding card
			MinionCard randomMinionCard = (MinionCard) player.getHand().getRandomOfType(CardType.MINION);
			player.getHand().remove(randomMinionCard);
			context.getLogic().summon(player.getId(), randomMinionCard.summon(), null, null, false);
		}

	}

	private class AlarmOBotTrigger extends TurnStartTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			Player owningPlayer = event.getGameContext().getPlayer(host.getOwner());
			return owningPlayer.getHand().hasCardOfType(CardType.MINION);

		}
	}

	public AlarmOBot() {
		super("Alarm-o-bot", 0, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("At the start of your turn, swap this minion with a random one in your hand.");
	}

	@Override
	public int getTypeId() {
		return 80;
	}



	@Override
	public Minion summon() {
		Minion alarmOBot = createMinion();
		Spell alarmOBotSpell = new AlarmOBotSpell();
		alarmOBotSpell.setTarget(EntityReference.SELF);
		alarmOBot.setSpellTrigger(new SpellTrigger(new AlarmOBotTrigger(), alarmOBotSpell));
		return alarmOBot;
	}
}
