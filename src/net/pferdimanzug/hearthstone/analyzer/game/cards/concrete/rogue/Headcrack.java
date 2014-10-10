package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalEffectSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Headcrack extends SpellCard {

	public Headcrack() {
		super("Headcrack", Rarity.RARE, HeroClass.ROGUE, 2);
		setDescription("Deal $2 damage to the enemy hero. Combo: Return this to your hand next turn.");

		SpellDesc noComboSpell = DamageSpell.create(2);
		noComboSpell.setTarget(EntityReference.ENEMY_HERO);
		SpellDesc comboSpell = ReceiveCardSpell.create(this);
		SpellDesc headCrack = ConditionalEffectSpell.create(noComboSpell, comboSpell, (context, player, target) -> player.getHero().hasStatus(GameTag.COMBO));
		setSpell(headCrack);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 296;
	}
}
