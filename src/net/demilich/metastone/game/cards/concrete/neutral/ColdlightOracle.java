package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;

public class ColdlightOracle extends MinionCard {

	public ColdlightOracle() {
		super("Coldlight Oracle", 2, 2, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: Each player draws 2 cards.");
		setRace(Race.MURLOC);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 106;
	}

	@Override
	public Minion summon() {
		Minion coldlightOracle = createMinion();
		coldlightOracle.setBattlecry(Battlecry.createBattlecry(DrawCardSpell.create(2, TargetPlayer.BOTH)));
		return coldlightOracle;
	}
}
