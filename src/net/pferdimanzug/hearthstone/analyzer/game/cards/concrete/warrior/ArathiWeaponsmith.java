package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArathiWeaponsmith extends MinionCard {

	private class BattleAxe extends WeaponCard {

		public BattleAxe() {
			super("Battle Axe", Rarity.FREE, HeroClass.WARRIOR, 1);
		}

		@Override
		public Weapon getWeapon() {
			return createWeapon(2, 2);
		}
		
	}

	public ArathiWeaponsmith() {
		super("Arathi Weaponsmith", 3, 3, Rarity.COMMON, HeroClass.WARRIOR, 4);
	}
	
	@Override
	public Minion summon() {
		Minion arathiWeaponsmith = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new EquipWeaponSpell(new BattleAxe()), TargetSelection.NONE);
		arathiWeaponsmith.setTag(GameTag.BATTLECRY, battlecry);
		return arathiWeaponsmith;
	}

}
