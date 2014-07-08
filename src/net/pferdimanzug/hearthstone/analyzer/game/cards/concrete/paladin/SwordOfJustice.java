package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ChangeDurabilitySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SwordOfJustice extends WeaponCard {

	public SwordOfJustice() {
		super("Sword of Justice", Rarity.EPIC, HeroClass.PALADIN, 3);
		setDescription("Whenever you summon a minion, give it +1/+1 and this loses 1 Durability.");
	}

	@Override
	public Weapon getWeapon() {
		Weapon swordOfJustice = createWeapon(1, 5);
		Spell buffSpell = new BuffSpell(1, 1);
		buffSpell.setTarget(EntityReference.EVENT_TARGET);
		Spell loseDurability = new ChangeDurabilitySpell(-1);
		loseDurability.setTarget(EntityReference.FRIENDLY_HERO);
		Spell swordOfJusticeSpell = new MetaSpell(buffSpell, loseDurability);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF), swordOfJusticeSpell);
		swordOfJustice.setSpellTrigger(trigger);
		return swordOfJustice;
	}

}
