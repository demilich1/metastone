package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ModifyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MillhouseManastorm extends MinionCard {

	public MillhouseManastorm() {
		super("Millhouse Manastorm", 4, 4, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setDescription("Battlecry: Enemy spells cost (0) next turn.");
	}

	@Override
	public int getTypeId() {
		return 165;
	}



	@Override
	public Minion summon() {
		Minion millhouseManastorm = createMinion();
		Spell castSpellsForFree = new ModifyTagSpell(GameTag.SPELL_MANA_COST, -99, new TurnStartTrigger());
		castSpellsForFree.setTarget(EntityReference.ENEMY_HERO);
		millhouseManastorm.setBattlecry(Battlecry.createBattlecry(castSpellsForFree));
		return millhouseManastorm;
	}
}
