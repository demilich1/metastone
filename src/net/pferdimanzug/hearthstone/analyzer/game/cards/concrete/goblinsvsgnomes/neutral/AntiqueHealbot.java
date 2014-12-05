package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AntiqueHealbot extends MinionCard {

	public AntiqueHealbot() {
		super("Antique Healbot", 3, 3, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Battlecry: Restore 8 Health to your hero.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 501;
	}



	@Override
	public Minion summon() {
		Minion antiqueHealbot = createMinion();
		SpellDesc healSpell = HealingSpell.create(8);
		healSpell.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(healSpell);
		antiqueHealbot.setBattlecry(battlecry);
		return antiqueHealbot;
	}
}
