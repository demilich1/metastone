package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Avenge extends SecretCard {

	public Avenge() {
		super("Avenge", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When one of your minions dies, give a random friendly minion +3/+2");

		SpellDesc buffRandomSpell = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, 3, 2, true);
		setTriggerAndEffect(new AvengeTrigger(), buffRandomSpell);
	}

	@Override
	public int getTypeId() {
		return 385;
	}
	
	private class AvengeTrigger extends MinionDeathTrigger {
		public AvengeTrigger() {
			super(TargetPlayer.SELF);
		}
		
		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			
			Player player = event.getGameContext().getPlayer(getOwner());
			
			return player.getMinions().size() > 1;
		}
	}
}
