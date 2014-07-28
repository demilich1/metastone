package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionAttacksTrigger;

public class SnakeTrap extends SecretCard {

	private class Snake extends MinionCard {

		public Snake() {
			super("Snake", 1, 1, Rarity.COMMON, HeroClass.HUNTER, 0);
			setRace(Race.BEAST);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}
	
	public SnakeTrap() {
		super("Snake Trap", Rarity.EPIC, HeroClass.HUNTER, 2);
		setDescription("Secret: When one of your minions is attacked, summon three 1/1 Snakes.");
		
		Spell summonSpell = new SummonSpell(new Snake(), new Snake(), new Snake());
		setTriggerAndEffect(new MinionAttacksTrigger(), summonSpell);
	}

	@Override
	public int getTypeId() {
		return 44;
	}
}
