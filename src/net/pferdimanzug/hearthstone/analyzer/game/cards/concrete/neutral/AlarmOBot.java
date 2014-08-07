package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.AlarmOBotSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AlarmOBot extends MinionCard {

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
		SpellDesc alarmOBotSpell = AlarmOBotSpell.create();
		alarmOBotSpell.setTarget(EntityReference.SELF);
		alarmOBot.setSpellTrigger(new SpellTrigger(new AlarmOBotTrigger(), alarmOBotSpell));
		return alarmOBot;
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
}
