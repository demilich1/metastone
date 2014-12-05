package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ReceiveSparePartSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		deathrattle.setTarget(EntityReference.NONE);
		clockworkGnome.addDeathrattle(deathrattle);
		return clockworkGnome;
	}
}
