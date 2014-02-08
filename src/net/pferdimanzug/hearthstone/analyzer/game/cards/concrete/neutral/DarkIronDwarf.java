package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DarkIronDwarf extends MinionCard {

	private static final int ATTACK_BONUS = 2;

	public DarkIronDwarf() {
		super("Dark Iron Dwarf", 4, 4, Rarity.COMMON, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		Minion darkIronDwarf = createMinion();
		SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffSpell(-ATTACK_BONUS));
		Spell buff = new BuffSpell(+ATTACK_BONUS);
		Spell endBuff = new AddSpellTriggerSpell(endBuffTrigger);
		Battlecry battlecry = Battlecry.createBattlecry(new MetaSpell(buff, endBuff), TargetSelection.MINIONS);
		darkIronDwarf.setTag(GameTag.BATTLECRY, battlecry);
		return darkIronDwarf;
	}

}
