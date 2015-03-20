package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.EquipRandomWeaponSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class Blingtron3000 extends MinionCard {

	public Blingtron3000() {
		super("Blingtron 3000", 3, 4, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Equip a random weapon for each player");
		setRace(Race.MECH);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 503;
	}

	@Override
	public Minion summon() {
		Minion blingtron3000 = createMinion();
		SpellDesc equipRandomWeapons = EquipRandomWeaponSpell.create(TargetPlayer.BOTH);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(equipRandomWeapons);
		blingtron3000.setBattlecry(battlecry);
		return blingtron3000;
	}
}
