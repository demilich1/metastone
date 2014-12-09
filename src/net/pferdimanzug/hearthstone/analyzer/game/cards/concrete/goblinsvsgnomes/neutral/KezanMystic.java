package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.StealRandomSecretSpell;

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
