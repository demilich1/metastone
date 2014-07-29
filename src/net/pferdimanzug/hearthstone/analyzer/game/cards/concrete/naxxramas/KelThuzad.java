package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class KelThuzad extends MinionCard {

	public KelThuzad() {
		super("Kel'Thuzad", 6, 8, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("At the end of the turn, summon all friendly minions that died this turn.");
	}

	@Override
	public int getTypeId() {
		return 414;
	}
	
	@Override
	public Minion summon() {
		Minion kelThuzad = createMinion();
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), new KelThuzadSpell());
		kelThuzad.setSpellTrigger(trigger);
		return kelThuzad;
	}

	private class KelThuzadSpell extends Spell {
		
		{
			setTarget(EntityReference.NONE);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			int currentTurn = context.getTurn();
			for (Minion deadMinion : player.getGraveyard()) {
				if (deadMinion.getTagValue(GameTag.DIED_ON_TURN) == currentTurn) {
					MinionCard minionCard = (MinionCard) deadMinion.getSourceCard();
					context.getLogic().summon(player.getId(), minionCard.summon(), null, null, false);
				}
			}
		}
		
	}
}
