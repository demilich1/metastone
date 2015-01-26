package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class LootHoarder extends MinionCard {

	public LootHoarder() {
		super("Loot Hoarder", 2, 1, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Deathrattle: Draw a card.");
	}

	@Override
	public int getTypeId() {
		return 156;
	}

	@Override
	public Minion summon() {
		SpellDesc deathrattle = DrawCardSpell.create();
		Minion lootHoarder = createMinion();
		lootHoarder.addDeathrattle(deathrattle);
		return lootHoarder;
	}
}
