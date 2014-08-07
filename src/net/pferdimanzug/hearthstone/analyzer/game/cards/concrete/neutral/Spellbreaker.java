package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SilenceSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Spellbreaker extends MinionCard {

	public Spellbreaker() {
		super("Spellbreaker", 4, 3, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Battlecry: Silence a minion.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 203;
	}

	@Override
	public Minion summon() {
		Minion spellbreaker = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SilenceSpell.create());
		battlecry.setTargetKey(EntityReference.ALL_MINIONS);
		spellbreaker.setBattlecry(battlecry);
		return spellbreaker;
	}
}
