package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Devilsaur;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Squirrel;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.EitherOrSpell;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc devilsaurSpell = TransformMinionSpell.create(new Devilsaur());
		devilsaurSpell.pickRandomTarget(true);
		devilsaurSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellDesc squirellSpell = TransformMinionSpell.create(new Squirrel());
		squirellSpell.pickRandomTarget(true);
		squirellSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellDesc randomTransformSpell = EitherOrSpell.create(devilsaurSpell, squirellSpell, (context, player, target) -> context.getLogic()
				.randomBool());
		randomTransformSpell.setTarget(EntityReference.NONE);
		Battlecry battlecry = Battlecry.createBattlecry(randomTransformSpell);
		tinkmasterOverspark.setBattlecry(battlecry);
		return tinkmasterOverspark;
	}
}
