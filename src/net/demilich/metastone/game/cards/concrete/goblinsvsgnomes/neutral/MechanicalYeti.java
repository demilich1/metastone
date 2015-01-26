package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.custom.ReceiveSparePartSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MechanicalYeti extends MinionCard {

	public MechanicalYeti() {
		super("Mechanical Yeti", 4, 5, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Deathrattle: Give each player a Spare Part.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 530;
	}



	@Override
	public Minion summon() {
		Minion mechanicalYeti = createMinion();
		SpellDesc deathrattle = ReceiveSparePartSpell.create(TargetPlayer.BOTH);
		mechanicalYeti.addDeathrattle(deathrattle);
		return mechanicalYeti;
	}
}
