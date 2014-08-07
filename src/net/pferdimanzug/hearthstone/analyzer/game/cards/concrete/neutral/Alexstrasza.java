package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.AlexstraszaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Alexstrasza extends MinionCard {
	
	public Alexstrasza() {
		super("Alexstrasza", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Battlecry: Set a hero's remaining Health to 15.");
		setRace(Race.DRAGON);
	}

	@Override
	public int getTypeId() {
		return 81;
	}
	
	@Override
	public Minion summon() {
		Minion alexstrasza = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(AlexstraszaSpell.create(), TargetSelection.HEROES);
		alexstrasza.setBattlecry(battlecry);
		return alexstrasza;
	}

	
}
