package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class RockbiterWeapon extends SpellCard {

	private static final int ATTACK_BONUS = 3;

	public RockbiterWeapon() {
		super("Rockbiter Weapon", Rarity.FREE, HeroClass.SHAMAN, 1);
		setDescription("Give a friendly character +3 Attack this turn.");
		BuffSpell buff = new BuffSpell(+ATTACK_BONUS);
		buff.setTemporary(true);
		setSpell(buff);
		setTargetRequirement(TargetSelection.FRIENDLY_CHARACTERS);
	}

}
