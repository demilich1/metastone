package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.DamageReceivedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class GurubashiBerserker extends MinionCard {
	
	public static final int BASE_ATTACK = 2;
	public static final int ATTACK_BONUS = 3;

	public GurubashiBerserker() {
		super("Gurubashi Berserker", BASE_ATTACK, 7, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Whenever this minion takes damage, gain +3 Attack.");
	}
	
	@Override
	public Minion summon() {
		Minion gurubashiBerserker = createMinion();
		Spell buffAttack = new BuffSpell(ATTACK_BONUS, 0);
		buffAttack.setTarget(EntityReference.pointTo(gurubashiBerserker));
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), buffAttack);
		gurubashiBerserker.setSpellTrigger(trigger);
		return gurubashiBerserker;
	}
	

}
