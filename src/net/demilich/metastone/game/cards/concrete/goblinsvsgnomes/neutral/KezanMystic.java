package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.StealRandomSecretSpell;

public class KezanMystic extends MinionCard {

	public KezanMystic() {
		super("Kezan Mystic", 4, 3, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Take control of a random enemy Secret.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 526;
	}

	@Override
	public Minion summon() {
		Minion kezanMystic = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(StealRandomSecretSpell.create());
		kezanMystic.setBattlecry(battlecry);
		return kezanMystic;
	}
}
