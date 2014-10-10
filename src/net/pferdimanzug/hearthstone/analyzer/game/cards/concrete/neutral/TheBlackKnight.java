package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TheBlackKnight extends MinionCard {

	public TheBlackKnight() {
		super("The Black Knight", 4, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Battlecry: Destroy an enemy minion with Taunt.");
	}

	@Override
	public int getTypeId() {
		return 216;
	}

	@Override
	public Minion summon() {
		Minion blackKnight = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DestroySpell.create(), TargetSelection.ENEMY_MINIONS);
		battlecry.setEntityFilter(entity -> entity.hasStatus(GameTag.TAUNT));
		blackKnight.setBattlecry(battlecry);
		return blackKnight;
	}
}
