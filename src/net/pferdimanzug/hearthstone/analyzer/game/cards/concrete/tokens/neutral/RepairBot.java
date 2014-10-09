package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class RepairBot extends MinionCard {

	public RepairBot() {
		super("Repair Bot", 0, 3, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("At the end of your turn, restore 6 Health to a damaged character.");

		setCollectible(false);
	}

	@Override
	public Minion summon() {
		Minion repairBot = createMinion();
		SpellDesc healRandomSpell = HealRandomSpell.create(6);
		healRandomSpell.setTarget(EntityReference.ALL_CHARACTERS);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), healRandomSpell);
		repairBot.setSpellTrigger(trigger);
		return repairBot;
	}

	@Override
	public int getTypeId() {
		return 449;
	}
}
