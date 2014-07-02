package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MinMaxDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LightningStorm extends SpellCard {

	public LightningStorm() {
		super("Lightning Storm", Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("Deal 2-3 damage to all enemy minions. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);

		Spell lightningStorm = new MinMaxDamageSpell(2, 3);
		lightningStorm.setTarget(EntityReference.ENEMY_MINIONS);
		setSpell(lightningStorm);
		setTargetRequirement(TargetSelection.NONE);
	}

}
