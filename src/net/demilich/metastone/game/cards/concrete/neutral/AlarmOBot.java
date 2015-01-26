package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.custom.AlarmOBotSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class AlarmOBot extends MinionCard {

	public AlarmOBot() {
		super("Alarm-o-bot", 0, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("At the start of your turn, swap this minion with a random one in your hand.");
		setRace(Race.MECH);
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
