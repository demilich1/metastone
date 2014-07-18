package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Reincarnate extends SpellCard {

	public Reincarnate() {
		super("Reincarnate", Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Destroy a minion, then return it to life with full Health.");
		setSpell(new ReincarnateSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	private class ReincarnateSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Minion minion = (Minion) target;
			MinionCard sourceCard = (MinionCard) minion.getSourceCard();
			context.getLogic().destroy(minion);
			context.getLogic().summon(player.getId(), sourceCard.summon(), null, null, false);
		}

	}

}
