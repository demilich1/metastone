package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffWeaponSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class GoblinAutoBarber extends MinionCard {

	public GoblinAutoBarber() {
		super("Goblin Auto-Barber", 3, 2, Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("Battlecry: Give your weapon +1 Attack.");
		setRace(Race.MECH);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 568;
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
