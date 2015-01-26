package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.custom.SwapAttackAndHpSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class CrazedAlchemist extends MinionCard {

	public CrazedAlchemist() {
		super("Crazed Alchemist", 2, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Battlecry: Swap the Attack and Health of a minion.");
	}

	@Override
	public int getTypeId() {
		return 109;
	}
	
	@Override
	public Minion summon() {
		Minion crazedAlchemist = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SwapAttackAndHpSpell.create(), TargetSelection.MINIONS);
		crazedAlchemist.setBattlecry(battlecry);
		return crazedAlchemist;
	}

	
}
