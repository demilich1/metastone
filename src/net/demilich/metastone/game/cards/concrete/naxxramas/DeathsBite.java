package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DeathsBite extends WeaponCard {

	public DeathsBite() {
		super("Death's Bite", Rarity.COMMON, HeroClass.WARRIOR, 4);
		setDescription("Deathrattle: Deal 1 damage to all minions.");
	}

	@Override
	public int getTypeId() {
		return 388;
	}

	@Override
	public Weapon getWeapon() {
		Weapon deathsBite = createWeapon(4, 2);
		SpellDesc deathrattle = DamageSpell.create(1);
		deathrattle.setTarget(EntityReference.ALL_MINIONS);
		deathsBite.addDeathrattle(deathrattle);
		return deathsBite;
	}
}
