package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaHealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;

public class HolyNova extends SpellCard {
	
	public HolyNova() {
		super("Holy Nova", Rarity.FREE, HeroClass.PRIEST, 5);
		setSpell(new MetaSpell(new AreaDamageSpell(2, TargetSelection.ENEMY_CHARACTERS), new AreaHealingSpell(2, TargetSelection.FRIENDLY_CHARACTERS)));
		setTargetRequirement(TargetSelection.NONE);
	}
	

}
