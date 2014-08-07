package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.SwapAttackAndHpSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
