package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MortalStrike extends SpellCard {

	public MortalStrike() {
		super("Mortal Strike", Rarity.RARE, HeroClass.WARRIOR, 4);
		setDescription("Deal 4 damage. If you have 12 or less Health, deal 6 instead.");
		
		//setSpell(DamageSpell.create((context, player, target) -> player.getHero().getHp() > 12 ? 4 : 6));
		setSpell(DamageSpell.create(1));
		setTargetRequirement(TargetSelection.ANY);
	}
	


	@Override
	public int getTypeId() {
		return 376;
	}
}
