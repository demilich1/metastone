package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class DreadCorsair extends MinionCard {

	public DreadCorsair() {
		super("Dread Corsair", 3, 3, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Taunt. Costs (1) less per Attack of your weapon. ");
		setTag(GameTag.RACE, Race.PIRATE);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

	@Override
	public int getManaCost(Player player) {
		Weapon weapon = player.getHero().getWeapon();
		if (weapon == null) {
			return super.getManaCost(player);
		}
		return Math.max(0, super.getManaCost(player) - weapon.getAttack());
	}

}
