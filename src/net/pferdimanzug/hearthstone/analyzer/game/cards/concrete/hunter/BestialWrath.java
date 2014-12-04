package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TemporaryAttackSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BestialWrath extends SpellCard {

	public BestialWrath() {
		super("Bestial Wrath", Rarity.EPIC, HeroClass.HUNTER, 1);
		setDescription("Give a Beast +2 Attack and Immune this turn.");
		
		SpellDesc buffAttackSpell = TemporaryAttackSpell.create(2);
		SpellDesc immunitySpell = ApplyTagSpell.create(GameTag.IMMUNE, new TurnEndTrigger());
		setSpell(MetaSpell.create(buffAttackSpell, immunitySpell));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		if (target.getEntityType() != EntityType.MINION) {
			return false;
		}
		Minion minion = (Minion) target;
		return minion.getRace() == Race.BEAST;
	}


	@Override
	public int getTypeId() {
		return 28;
	}
}
