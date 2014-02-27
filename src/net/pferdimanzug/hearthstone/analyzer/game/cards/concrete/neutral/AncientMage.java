package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class AncientMage extends MinionCard {

	public AncientMage() {
		super("Ancient Mage", 2, 5, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Give adjacent minions Spell Damage +1.");
	}

	@Override
	public Minion summon() {
		Minion ancientMage = createMinion();
		//TODO: implement battlecry
		return ancientMage;
	}
	

}
