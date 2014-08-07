package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Doomsayer extends MinionCard {

	public Doomsayer() {
		super("Doomsayer", 0, 7, Rarity.EPIC, HeroClass.ANY, 2);
		setDescription("At the start of your turn, destroy ALL minions.");
	}

	@Override
	public int getTypeId() {
		return 118;
	}



	@Override
	public Minion summon() {
		Minion doomsayer = createMinion();
		SpellDesc endOfWorldSpell = DestroySpell.create();
		endOfWorldSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), endOfWorldSpell);
		doomsayer.setSpellTrigger(trigger);
		return doomsayer;
	}
}
