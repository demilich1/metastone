package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
