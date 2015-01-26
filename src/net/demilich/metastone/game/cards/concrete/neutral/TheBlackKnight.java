package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
