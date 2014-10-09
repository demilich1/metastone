package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons.BattleAxe;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
		Battlecry battlecry = Battlecry.createBattlecry(EquipWeaponSpell.create(new BattleAxe()), TargetSelection.NONE);
		arathiWeaponsmith.setBattlecry(battlecry);
		return arathiWeaponsmith;
	}
}
