package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.TotemicCallSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TotemicCall extends HeroPower {

	public TotemicCall() {
		super("Totemic Call", HeroClass.SHAMAN);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(TotemicCallSpell.create());
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (player.getMinions().size() < 4) {
			return true;
		}
		List<UniqueEntity> availableTotems = new ArrayList<UniqueEntity>(4);
		availableTotems.add(UniqueEntity.HEALING_TOTEM);
		availableTotems.add(UniqueEntity.SEARING_TOTEM);
		availableTotems.add(UniqueEntity.STONECLAW_TOTEM);
		availableTotems.add(UniqueEntity.WRATH_OF_AIR_TOTEM);
		for (Minion minion : player.getMinions()) {
			availableTotems.remove(minion.getTag(GameTag.UNIQUE_ENTITY));
		}
		return !availableTotems.isEmpty();
	}

}
