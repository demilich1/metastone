package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffWeaponSpell;

public class CaptainGreenskin extends MinionCard {

	public CaptainGreenskin() {
		super("Captain Greenskin", 5, 4, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Give your weapon +1/+1.");
		setRace(Race.PIRATE);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 103;
	}

	@Override
	public Minion summon() {
		Minion captainGreenskin = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(BuffWeaponSpell.create(1, 1));
		battlecry.setCondition((context, player) -> player.getHero().getWeapon() != null);
		captainGreenskin.setBattlecry(battlecry);
		return captainGreenskin;
	}
}
