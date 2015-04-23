package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DragonsBreath extends SpellCard {

	public DragonsBreath() {
		super("Dragon's Breath", Rarity.COMMON, HeroClass.MAGE, 5);
		setDescription("Deal 4 damage. Costs (1) less for each minion that died this turn.");
		
		setSpell(DamageSpell.create(4));
		
		setTargetRequirement(TargetSelection.ANY);
	}
	
	@Override
	public int getManaCost(GameContext context, Player player) {
		return super.getManaCost(context, player) - SpellUtils.howManyMinionsDiedThisTurn(context);
	}



	@Override
	public int getTypeId() {
		return 630;
	}
}
