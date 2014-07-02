package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class EtherealArcanist extends MinionCard {

	private class TurnEndAndControlSecretTrigger extends TurnEndTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}

			Player player = event.getGameContext().getPlayer(host.getOwner());
			return !player.getSecrets().isEmpty();
		}

	}

	public EtherealArcanist() {
		super("Ethereal Arcanist", 3, 3, Rarity.RARE, HeroClass.MAGE, 4);
		setDescription("If you control a Secret at the end of your turn, gain +2/+2.");
	}

	@Override
	public Minion summon() {
		Minion etherealArcanist = createMinion();
		Spell buffSpell = new BuffSpell(2, 2);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndAndControlSecretTrigger(), buffSpell);
		etherealArcanist.setSpellTrigger(trigger);
		return etherealArcanist;
	}

}
