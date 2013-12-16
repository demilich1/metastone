package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;

public class GnomishInventor extends MinionCard {

	public GnomishInventor() {
		super("Gnomish Inventor", Rarity.FREE, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		Minion gnomishInventor = createMinion(2, 4);
		Battlecry battlecry = Battlecry.createBattlecry(new DrawCardSpell(1), TargetRequirement.NONE);
		gnomishInventor.setTag(GameTag.BATTLECRY, battlecry);
		return gnomishInventor;
	}

}
