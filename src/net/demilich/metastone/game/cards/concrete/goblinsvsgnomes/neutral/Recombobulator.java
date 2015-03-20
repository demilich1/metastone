package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TransformToMinionWithManaCostSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Recombobulator extends MinionCard {

	public Recombobulator() {
		super("Recombobulator", 3, 2, Rarity.EPIC, HeroClass.ANY, 2);
		setDescription("Battlecry: Transform a friendly minion into a random minion with the same Cost.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 541;
	}

	@Override
	public Minion summon() {
		Minion recombolator = createMinion();
		SpellDesc transformSpell = TransformToMinionWithManaCostSpell.create();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(transformSpell, TargetSelection.FRIENDLY_MINIONS);
		recombolator.setBattlecry(battlecry);
		return recombolator;
	}
}
