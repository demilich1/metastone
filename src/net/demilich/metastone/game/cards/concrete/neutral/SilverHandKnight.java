package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Squire;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.SummonSpell;

public class SilverHandKnight extends MinionCard {

	public SilverHandKnight() {
		super("Silver Hand Knight", 4, 4, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Battlecry: Summon a 2/2 Squire.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 199;
	}
	
	@Override
	public Minion summon() {
		Minion silverHandKnight = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(SummonSpell.create(RelativeToSource.RIGHT, new Squire()));
		battlecry.setResolvedLate(true);
		silverHandKnight.setBattlecry(battlecry);
		return silverHandKnight;
	}
}
