package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SilenceSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class IronbeakOwl extends MinionCard {

	public IronbeakOwl() {
		super("Ironbeak Owl", 2, 1, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Silence a minion.");
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion ironbeakOwl = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new SilenceSpell(), TargetSelection.MINIONS);
		ironbeakOwl.setBattlecry(battlecry);
		return ironbeakOwl;
	}

}
