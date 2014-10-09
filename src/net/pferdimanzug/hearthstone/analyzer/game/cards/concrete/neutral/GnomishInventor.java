package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class GnomishInventor extends MinionCard {

	public GnomishInventor() {
		super("Gnomish Inventor", 2, 4, Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Battlecry: Draw a card.");
	}

	@Override
	public int getTypeId() {
		return 134;
	}

	@Override
	public Minion summon() {
		Minion gnomishInventor = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DrawCardSpell.create(1), TargetSelection.NONE);
		gnomishInventor.setBattlecry(battlecry);
		return gnomishInventor;
	}
}
