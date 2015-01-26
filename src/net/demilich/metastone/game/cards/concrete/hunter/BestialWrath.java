package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.TargetSelection;

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
