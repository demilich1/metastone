package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class BloodsailRaider extends MinionCard {

	public BloodsailRaider() {
		super("Bloodsail Raider", 2, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Gain Attack equal to the Attack of your weapon.");
		setRace(Race.PIRATE);
	}

	@Override
	public Minion summon() {
		Minion bloodsailRaider = createMinion();
		Spell buffSpell = new BuffSpell((context, player, target) -> player.getHero().getWeapon().getWeaponDamage(), null);
		buffSpell.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell);
		battlecry.setCondition((context, player) -> player.getHero().getWeapon() != null);
		battlecry.setResolvedLate(true);
		bloodsailRaider.setBattlecry(battlecry);
		return bloodsailRaider;
	}

}
