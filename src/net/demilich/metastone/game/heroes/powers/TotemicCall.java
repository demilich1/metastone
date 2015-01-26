package net.demilich.metastone.game.heroes.powers;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.custom.TotemicCallSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
