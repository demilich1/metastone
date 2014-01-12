package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Bloodlust extends SpellCard {

	private static final int ATTACK_BONUS = 3;
	
	public Bloodlust() {
		super("Bloodlust", Rarity.FREE, HeroClass.SHAMAN, 5);
		SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffSpell(-ATTACK_BONUS, 0));
		Spell buff = new BuffSpell(+ATTACK_BONUS, 0);
		buff.setTarget(TargetKey.FRIENDLY_MINIONS);
		Spell endBuff = new AddSpellTriggerSpell(endBuffTrigger);
		endBuff.setTarget(TargetKey.FRIENDLY_MINIONS);
		setSpell(new MetaSpell(buff, endBuff));
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return player.getMinions().size() > 0;
	}

}
