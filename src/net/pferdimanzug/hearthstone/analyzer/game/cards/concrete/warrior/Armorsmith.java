package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDamagedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class Armorsmith extends MinionCard {

	public Armorsmith() {
		super("Armorsmith", 1, 4, Rarity.RARE, HeroClass.WARRIOR, 2);
		setDescription("Whenever a friendly minion takes damage, gain 1 Armor.");
	}

	@Override
	public int getTypeId() {
		return 361;
	}



	@Override
	public Minion summon() {
		Minion armorsmith = createMinion();
		SpellTrigger trigger = new SpellTrigger(new MinionDamagedTrigger(TargetPlayer.SELF), new BuffHeroSpell(0, 1));
		armorsmith.setSpellTrigger(trigger);
		return armorsmith;
	}
}
