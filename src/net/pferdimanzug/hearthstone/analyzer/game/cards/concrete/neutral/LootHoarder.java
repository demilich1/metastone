package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

public class LootHoarder extends MinionCard {

	public LootHoarder() {
		super("Loot Hoarder", 2, 1, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Deathrattle: Draw a card.");
	}

	@Override
	public Minion summon() {
		Spell deathrattle = new DrawCardSpell();
		Minion lootHoarder = createMinion();
		lootHoarder.addDeathrattle(deathrattle);
		return lootHoarder;
	}

}
