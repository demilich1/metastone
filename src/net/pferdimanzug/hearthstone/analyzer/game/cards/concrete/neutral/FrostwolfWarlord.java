package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class FrostwolfWarlord extends MinionCard {

	public FrostwolfWarlord() {
		super("Frostwolf Warlord", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion frostwolfWarlord = createMinion(4, 4);
		frostwolfWarlord.setTag(GameTag.BATTLECRY, new Leadership(frostwolfWarlord));
		return frostwolfWarlord;
	}

	private class Leadership extends GameAction {

		private Entity source;

		public Leadership(Entity source) {
			setActionType(ActionType.MINION_ABILITY);
			setEffectHint(EffectHint.POSITIVE);
			this.source = source;
		}

		@Override
		public void execute(GameContext context, Player player) {
			int minionCount = player.getMinions().size();
			source.modifyTag(GameTag.ATTACK_BONUS, +minionCount);
			source.modifyTag(GameTag.HP_BONUS, +minionCount);
		}

	}

}
