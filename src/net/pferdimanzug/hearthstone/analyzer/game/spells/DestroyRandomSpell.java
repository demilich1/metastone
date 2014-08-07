package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class DestroyRandomSpell extends DestroySpell {

	public static SpellDesc create() {
		return create(null);
	}
	
	public static SpellDesc create(Predicate<Entity> filter) {
		SpellDesc desc = new SpellDesc(DestroyRandomSpell.class);
		if (filter != null) {
			desc.set(SpellArg.ENTITY_FILTER, filter);
		}
		return desc;
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		@SuppressWarnings("unchecked")
		List<Entity> validTargets = getValidTargets(targets, (Predicate<Entity>) desc.get(SpellArg.ENTITY_FILTER));
		if (validTargets == null || validTargets.isEmpty()) {
			return;
		}
		Entity randomTarget = SpellUtils.getRandomTarget(validTargets);
		onCast(context, player, null, randomTarget);
	}

	private List<Entity> getValidTargets(List<Entity> allTargets, Predicate<Entity> filter) {
		if (filter == null) {
			return allTargets;
		}
		return allTargets.stream().filter(filter).collect(Collectors.<Entity> toList());
	}

}
