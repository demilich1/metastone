package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.Devilsaur;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.Squirrel;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EitherOrSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformRandomMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TinkmasterOverspark extends MinionCard {

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
		SpellDesc devilsaurSpell = TransformRandomMinionSpell.create(new Devilsaur());
		devilsaurSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellDesc squirellSpell = TransformRandomMinionSpell.create(new Squirrel());
		squirellSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellDesc randomTransformSpell = EitherOrSpell.create(devilsaurSpell, squirellSpell, (context, player, target) -> context.getLogic()
				.randomBool());
		randomTransformSpell.setTarget(EntityReference.NONE);
		Battlecry battlecry = Battlecry.createBattlecry(randomTransformSpell);
		tinkmasterOverspark.setBattlecry(battlecry);
		return tinkmasterOverspark;
	}
}
