package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class MekgineerThermaplugg extends MinionCard {

	public MekgineerThermaplugg() {
		super("Mekgineer Thermaplugg", 9, 7, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Whenever an enemy minion dies, summon a Leper Gnome.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 532;
	}

	@Override
	public Minion summon() {
		Minion mekgineerThermaplugg = createMinion();
		//MinionDeathTrigger minionDeathTrigger = new MinionDeathTrigger(TargetPlayer.OPPONENT);
		MinionDeathTrigger minionDeathTrigger = new MinionDeathTrigger(null);
		SpellTrigger trigger = new SpellTrigger(minionDeathTrigger, SummonSpell.create("minion_leper_gnome"));
		mekgineerThermaplugg.setSpellTrigger(trigger);
		return mekgineerThermaplugg;
	}
}
