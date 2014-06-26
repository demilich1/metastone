package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ScavengingHyena extends MinionCard {

	public ScavengingHyena() {
		super("Scavenging Hyena", 2, 2, Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Whenever a friendly Beast dies, gain +2/+1.");
	}

	@Override
	public Minion summon() {
		Minion scavengingHyena = createMinion();
		Spell buffAttack = new BuffSpell(2, 1);
		buffAttack.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new MinionDeathTrigger(TargetPlayer.SELF, Race.BEAST), buffAttack);
		scavengingHyena.setSpellTrigger(trigger);
		return scavengingHyena;
	}

}
