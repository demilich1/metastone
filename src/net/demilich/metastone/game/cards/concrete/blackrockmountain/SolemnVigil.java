package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SolemnVigil extends SpellCard {

	public SolemnVigil() {
		super("Solemn Vigil", Rarity.COMMON, HeroClass.PALADIN, 5);
		setDescription("Draw 2 cards. Costs (1) less for each minion that died this turn.");
		
		setSpell(DrawCardSpell.create(2));
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getManaCost(GameContext context, Player player) {
		return super.getManaCost(context, player) - SpellUtils.howManyMinionsDiedThisTurn(context);
	}



	@Override
	public int getTypeId() {
		return 644;
	}
}
