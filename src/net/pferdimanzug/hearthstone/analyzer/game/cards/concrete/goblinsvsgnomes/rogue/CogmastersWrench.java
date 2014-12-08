package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalAttackBonusSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.BoardChangedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
