package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.LeperGnome;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class MekgineerThermaplugg extends MinionCard {

	public MekgineerThermaplugg() {
		super("Mekgineer Thermaplugg", 9, 7, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Whenever an enemy minion dies, summon a Leper Gnome.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion mekgineerThermaplugg = createMinion();
		MinionDeathTrigger minionDeathTrigger = new MinionDeathTrigger(TargetPlayer.OPPONENT);
		SpellTrigger trigger = new SpellTrigger(minionDeathTrigger, SummonSpell.create(new LeperGnome()));
		mekgineerThermaplugg.setSpellTrigger(trigger);
		return mekgineerThermaplugg;
	}

}
