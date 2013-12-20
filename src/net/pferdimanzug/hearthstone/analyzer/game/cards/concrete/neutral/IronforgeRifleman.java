package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class IronforgeRifleman extends MinionCard {

	public IronforgeRifleman() {
		super("Ironforge Rifleman", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion ironforgeRifleman = createMinion(2, 2);
		Battlecry battlecry = Battlecry.createBattlecry(new SingleTargetDamageSpell(1), TargetSelection.ANY);
		ironforgeRifleman.setTag(GameTag.BATTLECRY, battlecry);
		return ironforgeRifleman;
	}

}
