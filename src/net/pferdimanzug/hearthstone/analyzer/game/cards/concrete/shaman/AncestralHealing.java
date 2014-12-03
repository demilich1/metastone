package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealToFullSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AncestralHealing extends SpellCard {

	public AncestralHealing() {
		super("Ancestral Healing", Rarity.FREE, HeroClass.SHAMAN, 0);
		setDescription("Restore a minion to full Health and give it Taunt.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(MetaSpell.create(HealToFullSpell.create(), ApplyTagSpell.create(GameTag.TAUNT)));
	}

	@Override
	public int getTypeId() {
		return 310;
	}
}
