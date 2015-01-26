package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.custom.AlexstraszaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
