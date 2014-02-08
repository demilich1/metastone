package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AbusiveSergeant extends MinionCard {
	
	public static final int ATTACK_BONUS = 2;

	public AbusiveSergeant() {
		super("Abusive Sergeant", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
	}
	
	@Override
	public Minion summon() {
		Minion abusiveSergeant = createMinion();
		SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffSpell(-ATTACK_BONUS));
		Spell battlecrySpell = new MetaSpell(new BuffSpell(+ATTACK_BONUS), new AddSpellTriggerSpell(endBuffTrigger));
		Battlecry battlecryAbusive = Battlecry.createBattlecry(battlecrySpell, TargetSelection.MINIONS);
		abusiveSergeant.setTag(GameTag.BATTLECRY, battlecryAbusive);
		return abusiveSergeant;
	}
	

}
