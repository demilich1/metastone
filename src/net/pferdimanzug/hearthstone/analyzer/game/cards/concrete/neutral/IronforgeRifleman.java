package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class IronforgeRifleman extends MinionCard {

	public IronforgeRifleman() {
		super("Ironforge Rifleman", 2, 2, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Battlecry: Deal 1 damage.");
	}

	@Override
	public Minion summon() {
		Minion ironforgeRifleman = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new DamageSpell(1), TargetSelection.ANY);
		ironforgeRifleman.setBattlecry(battlecry);
		return ironforgeRifleman;
	}

}
