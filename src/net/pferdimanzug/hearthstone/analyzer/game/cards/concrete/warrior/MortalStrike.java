package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MortalStrike extends SpellCard {

	public MortalStrike() {
		super("Mortal Strike", Rarity.RARE, HeroClass.WARRIOR, 4);
		setDescription("Deal 4 damage. If you have 12 or less Health, deal 6 instead.");
		
		setSpell(DamageSpell.create((context, player, target) -> player.getHero().getHp() > 12 ? 4 : 6));
		setTargetRequirement(TargetSelection.ANY);
	}
	


	@Override
	public int getTypeId() {
		return 376;
	}
}
