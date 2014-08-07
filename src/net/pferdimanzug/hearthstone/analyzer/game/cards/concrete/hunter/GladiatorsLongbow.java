package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.WeaponDestroyedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class GladiatorsLongbow extends WeaponCard {

	public GladiatorsLongbow() {
		super("Gladiator's Longbow", Rarity.EPIC, HeroClass.HUNTER, 7);
		setDescription("Your hero is Immune while attacking.");
	}

	@Override
	public int getTypeId() {
		return 35;
	}

	@Override
	public Weapon getWeapon() {
		Weapon gladiatorsLongbow = createWeapon(5, 2);
		SpellDesc immunity = ApplyTagSpell.create(GameTag.IMMUNE_WHILE_ATTACKING, new WeaponDestroyedTrigger());
		immunity.setTarget(EntityReference.FRIENDLY_HERO);
		gladiatorsLongbow.setBattlecry(Battlecry.createBattlecry(immunity));
		return gladiatorsLongbow;
	}
}
