package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Starfire extends SpellCard {

	public Starfire() {
		super("Starfire", Rarity.FREE, HeroClass.DRUID, 6);
		setDescription("Deal $5 damage.");
		setSpell(MetaSpell.create(DamageSpell.create(5), DrawCardSpell.create()));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 22;
	}
}
