package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class YseraAwakens extends SpellCard {

	public YseraAwakens() {
		super("Ysera Awakens", Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Deal 5 damage to all characters except Ysera.");
		SpellDesc yseraAwakens = DamageSpell.create(
				(context, player, target) -> target.getTag(GameTag.UNIQUE_MINION) == UniqueMinion.YSERA ? 0 : 5);
		yseraAwakens.setTarget(EntityReference.ALL_CHARACTERS);
		setSpell(yseraAwakens);
		setTargetRequirement(TargetSelection.NONE);
	}

}