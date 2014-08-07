package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
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
		List<UniqueMinion> availableTotems = new ArrayList<UniqueMinion>(4);
		availableTotems.add(UniqueMinion.HEALING_TOTEM);
		availableTotems.add(UniqueMinion.SEARING_TOTEM);
		availableTotems.add(UniqueMinion.STONECLAW_TOTEM);
		availableTotems.add(UniqueMinion.WRATH_OF_AIR_TOTEM);
		for (Minion minion : player.getMinions()) {
			availableTotems.remove(minion.getTag(GameTag.UNIQUE_MINION));
		}
		return !availableTotems.isEmpty();
	}

}
