package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.DamageReceivedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;

public class GurubashiBerserker extends MinionCard {
	
	public static final int BASE_ATTACK = 2;
	public static final int ATTACK_BONUS = 3;

	public GurubashiBerserker() {
		super("Gurubashi Berserker", Rarity.FREE, HeroClass.ANY, 5);
	}
	
	@Override
	public Minion summon() {
		Minion gurubashiBerserker = createMinion(BASE_ATTACK, 7);
		Spell buffAttack = new BuffSpell(3, 0);
		buffAttack.setTarget(TargetKey.pointTo(gurubashiBerserker));
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), buffAttack);
		gurubashiBerserker.addSpellTrigger(trigger);
		return gurubashiBerserker;
	}
	

}
