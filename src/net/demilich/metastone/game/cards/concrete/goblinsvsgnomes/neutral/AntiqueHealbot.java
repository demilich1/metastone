package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc healSpell = HealingSpell.create(EntityReference.FRIENDLY_HERO, 8);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(healSpell);
		antiqueHealbot.setBattlecry(battlecry);
		return antiqueHealbot;
	}
}
