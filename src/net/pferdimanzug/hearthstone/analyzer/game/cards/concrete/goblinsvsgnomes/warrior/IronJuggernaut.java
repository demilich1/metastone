package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens.BurrowingMine;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ShuffleToDeckSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class IronJuggernaut extends MinionCard {

	public IronJuggernaut() {
		super("Iron Juggernaut", 6, 5, Rarity.LEGENDARY, HeroClass.WARRIOR, 6);
		setDescription("Battlecry: Shuffle a Mine into your opponent's deck. When drawn, it explodes for 10 damage.");
		setRace(Race.MECH);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 605;
	}



	@Override
	public Minion summon() {
		Minion ironJuggernaut = createMinion();
		SpellDesc burrowingMine = ShuffleToDeckSpell.create(TargetPlayer.OPPONENT, new BurrowingMine());
		Battlecry battlecry = Battlecry.createBattlecry(burrowingMine);
		ironJuggernaut.setBattlecry(battlecry);
		return ironJuggernaut;
	}
}
