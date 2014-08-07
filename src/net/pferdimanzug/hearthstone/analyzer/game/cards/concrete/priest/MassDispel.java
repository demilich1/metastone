package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SilenceSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
