package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Brawl extends SpellCard {

	private class BrawlSpell extends DestroySpell {
		@Override
		public void cast(GameContext context, Player player, List<Entity> targets) {
			if (targets == null || targets.isEmpty()) {
				return;
			}
			List<Entity> destroyedTargets = new ArrayList<Entity>(targets);
			Entity randomTarget = SpellUtils.getRandomTarget(destroyedTargets);
			destroyedTargets.remove(randomTarget);
			for (Entity entity : destroyedTargets) {
				onCast(context, player, entity);
			}
		}
	}

	public Brawl() {
		super("Brawl", Rarity.EPIC, HeroClass.WARRIOR, 5);
		setDescription("Destroy all minions except one.  (chosen randomly)");

		//TODO: check if this spell can be played with 1 minion on the board
		setSpell(new BrawlSpell());
		setPredefinedTarget(EntityReference.ALL_MINIONS);
		setTargetRequirement(TargetSelection.NONE);

	}



	@Override
	public int getTypeId() {
		return 363;
	}
}
