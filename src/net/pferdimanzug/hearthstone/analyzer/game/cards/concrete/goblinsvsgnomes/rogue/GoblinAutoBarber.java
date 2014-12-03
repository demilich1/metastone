package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class GoblinAutoBarber extends MinionCard {

	public GoblinAutoBarber() {
		super("Goblin Auto-Barber", 3, 2, Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("Battlecry: Give your weapon +1 Attack.");
		setRace(Race.MECH);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion goblinAutoBarber = createMinion();
		SpellDesc buffWeaponSpell = BuffWeaponSpell.create(+1);
		buffWeaponSpell.setTarget(EntityReference.NONE);
		Battlecry battlecry = Battlecry.createBattlecry(buffWeaponSpell);
		battlecry.setCondition((context, player) -> player.getHero().getWeapon() != null);
		goblinAutoBarber.setBattlecry(battlecry);
		return goblinAutoBarber;
	}

}
