package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DestroyRandomSpell extends DestroySpell {

	private final Predicate<Entity> filter;

	public DestroyRandomSpell() {
		this(null);
	}

	public DestroyRandomSpell(Predicate<Entity> filter) {
		this.filter = filter;
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		List<Entity> validTargets = getValidTargets(targets);
		if (validTargets == null || validTargets.isEmpty()) {
			return;
		}
		Entity randomTarget = SpellUtils.getRandomTarget(validTargets);
		onCast(context, player, randomTarget);
	}

	private List<Entity> getValidTargets(List<Entity> allTargets) {
		if (filter == null) {
			return allTargets;
		}
		return allTargets.stream().filter(filter).collect(Collectors.<Entity> toList());
	}

}
