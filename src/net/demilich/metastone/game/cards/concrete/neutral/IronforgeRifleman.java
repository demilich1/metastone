package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class IronforgeRifleman extends MinionCard {

	public IronforgeRifleman() {
		super("Ironforge Rifleman", 2, 2, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Battlecry: Deal 1 damage.");
	}

	@Override
	public int getTypeId() {
		return 147;
	}



	@Override
	public Minion summon() {
		Minion ironforgeRifleman = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DamageSpell.create(1), TargetSelection.ANY);
		ironforgeRifleman.setBattlecry(battlecry);
		return ironforgeRifleman;
	}
}
