package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FrostShock extends SpellCard {

	public FrostShock() {
		super("Frost Shock", Rarity.FREE, HeroClass.SHAMAN, 1);
		setDescription("Deal $1 damage to an enemy character and Freeze it.");
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
		setSpell(new MetaSpell(new DamageSpell(1), new ApplyTagSpell(GameTag.FROZEN)));
	}



	@Override
	public int getTypeId() {
		return 322;
	}
}
