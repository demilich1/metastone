package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class DreadCorsair extends MinionCard {

	public DreadCorsair() {
		super("Dread Corsair", 3, 3, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Taunt. Costs (1) less per Attack of your weapon. ");
		setRace(Race.PIRATE);
	}

	@Override
	public int getManaCost(GameContext context, Player player) {
		Weapon weapon = player.getHero().getWeapon();
		if (weapon == null) {
			return super.getManaCost(context, player);
		}
		return super.getManaCost(context, player) - weapon.getWeaponDamage();
	}

	@Override
	public int getTypeId() {
		return 120;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
