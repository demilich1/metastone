package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.ConditionalAttackBonusSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.BoardChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class CogmastersWrench extends WeaponCard {

	public CogmastersWrench() {
		super("Cogmaster's Wrench", Rarity.EPIC, HeroClass.ROGUE, 3);
		setDescription("Has +2 Attack while you have a Mech.");
	}

	@Override
	public int getTypeId() {
		return 567;
	}

	@Override
	public Weapon getWeapon() {
		Weapon cogmastersWrench = createWeapon(1, 3);
		SpellDesc buffAttack = ConditionalAttackBonusSpell.create((context, player, target) -> SpellUtils.hasMinionOfRace(player, Race.MECH) ? +2 : 0);
		buffAttack.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new BoardChangedTrigger(), buffAttack);
		cogmastersWrench.setSpellTrigger(trigger);
		return cogmastersWrench;
	}
}
