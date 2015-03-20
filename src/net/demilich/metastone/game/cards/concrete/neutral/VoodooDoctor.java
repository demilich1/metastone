package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class VoodooDoctor extends MinionCard {

	public VoodooDoctor() {
		super("Voodoo Doctor", 2, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Battlecry: Restore 2 Health.");
	}

	@Override
	public int getTypeId() {
		return 223;
	}



	@Override
	public Minion summon() {
		Minion voodooDoctor = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(HealingSpell.create(2), TargetSelection.ANY);
		voodooDoctor.setBattlecry(battlecry);
		return voodooDoctor;
	}
}
