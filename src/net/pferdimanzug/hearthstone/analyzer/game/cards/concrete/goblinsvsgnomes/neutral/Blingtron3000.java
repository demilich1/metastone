package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipRandomWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Blingtron3000 extends MinionCard {

	public Blingtron3000() {
		super("Blingtron 3000", 3, 4, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Equip a random weapon for each player");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion blingtron3000 = createMinion();
		SpellDesc equipRandomWeapons = EquipRandomWeaponSpell.create(TargetPlayer.BOTH);
		equipRandomWeapons.setTarget(EntityReference.NONE);
		Battlecry battlecry = Battlecry.createBattlecry(equipRandomWeapons);
		blingtron3000.setBattlecry(battlecry);
		return blingtron3000;
	}

}
