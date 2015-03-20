package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.BurrowingMine;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ShuffleToDeckSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		BattlecryAction battlecry = BattlecryAction.createBattlecry(burrowingMine);
		ironJuggernaut.setBattlecry(battlecry);
		return ironJuggernaut;
	}
}
