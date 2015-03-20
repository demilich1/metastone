package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FireElemental extends MinionCard {
	
	private static final int BATTLECRY_DAMAGE = 3;
	
	public FireElemental() {
		super("Fire Elemental", 6, 5, Rarity.FREE, HeroClass.SHAMAN, 6);
		setDescription("Battlecry: Deal 3 damage.");
	}

	@Override
	public int getTypeId() {
		return 319;
	}



	@Override
	public Minion summon() {
		Minion fireElemental = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(DamageSpell.create(BATTLECRY_DAMAGE), TargetSelection.ANY);
		fireElemental.setBattlecry(battlecry);
		return fireElemental;
	}
}
