package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ReceiveSparePartSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
