package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.custom.ReceiveSparePartSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ClockworkGnome extends MinionCard {

	public ClockworkGnome() {
		super("Clockwork Gnome", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Deathrattle: Put a Spare Part card in your hand.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 507;
	}


	@Override
	public Minion summon() {
		Minion clockworkGnome = createMinion();
		SpellDesc deathrattle = ReceiveSparePartSpell.create(TargetPlayer.SELF);
		clockworkGnome.addDeathrattle(deathrattle);
		return clockworkGnome;
	}
}
