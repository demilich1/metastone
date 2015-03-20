package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.weapons.BattleAxe;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.EquipWeaponSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArathiWeaponsmith extends MinionCard {

	public ArathiWeaponsmith() {
		super("Arathi Weaponsmith", 3, 3, Rarity.COMMON, HeroClass.WARRIOR, 4);
		setDescription("Battlecry: Equip a 2/2 weapon.");
	}

	@Override
	public int getTypeId() {
		return 359;
	}
	
	@Override
	public Minion summon() {
		Minion arathiWeaponsmith = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(EquipWeaponSpell.create(new BattleAxe()), TargetSelection.NONE);
		arathiWeaponsmith.setBattlecry(battlecry);
		return arathiWeaponsmith;
	}
}
