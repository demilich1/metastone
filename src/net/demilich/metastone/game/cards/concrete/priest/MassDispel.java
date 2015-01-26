package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MassDispel extends SpellCard {

	public MassDispel() {
		super("Mass Dispel", Rarity.RARE, HeroClass.PRIEST, 4);
		setDescription("Silence all enemy minions. Draw a card.");
		
		SpellDesc silenceAll = SilenceSpell.create();
		silenceAll.setTarget(EntityReference.ENEMY_MINIONS);
		SpellDesc draw = DrawCardSpell.create();
		setSpell(MetaSpell.create(silenceAll, draw));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 269;
	}
}
