package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EitherOrSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformRandomMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TinkmasterOverspark extends MinionCard {

	private class Devilsaur extends MinionCard {

		public Devilsaur() {
			super("Devilsaur", 5, 5, Rarity.COMMON, HeroClass.ANY, 5);
			setRace(Race.BEAST);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

	private class Squirrel extends MinionCard {

		public Squirrel() {
			super("Squirrel", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
			setRace(Race.BEAST);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

	public TinkmasterOverspark() {
		super("Tinkmaster Overspark", 3, 3, Rarity.LEGENDARY, HeroClass.ANY, 3);
		setDescription("Battlecry: Transform another random minion into a 5/5 Devilsaur or a 1/1 Squirrel.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 219;
	}

	@Override
	public Minion summon() {
		Minion tinkmasterOverspark = createMinion();
		Spell devilsaurSpell = new TransformRandomMinionSpell(new Devilsaur());
		Spell squirellSpell = new TransformRandomMinionSpell(new Squirrel());
		Spell randomTransformSpell = new EitherOrSpell(devilsaurSpell, squirellSpell, (context, player, target) -> context.getLogic()
				.randomBool());
		randomTransformSpell.setTarget(EntityReference.ALL_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(randomTransformSpell);
		tinkmasterOverspark.setBattlecry(battlecry);
		return tinkmasterOverspark;
	}
}
