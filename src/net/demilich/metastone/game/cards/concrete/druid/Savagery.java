package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Savagery extends SpellCard {

	public Savagery() {
		super("Savagery", Rarity.RARE, HeroClass.DRUID, 1);
		setDescription("Deal damage equal to your hero's Attack to a minion.");
		
		setSpell(DamageSpell.create((context, player, target) -> player.getHero().getAttack()));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (!super.canBeCast(context, player)) {
			return false;
		}
		
		return player.getHero().getAttack() > 0;
	}

	@Override
	public int getTypeId() {
		return 19;
	}
}
