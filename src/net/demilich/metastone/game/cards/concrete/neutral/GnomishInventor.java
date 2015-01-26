package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
