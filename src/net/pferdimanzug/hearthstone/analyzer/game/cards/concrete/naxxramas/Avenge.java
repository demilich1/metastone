package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Avenge extends SecretCard {

	public Avenge() {
		super("Avenge", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When one of your minions dies, give a random friendly minion +3/+2");

		SpellDesc buffRandomSpell = BuffSpell.create(3, 2);
		buffRandomSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		buffRandomSpell.pickRandomTarget(true);
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
