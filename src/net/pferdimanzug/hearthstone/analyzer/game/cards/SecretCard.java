package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSecretSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.SecretTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SecretCard extends SpellCard {

	public SecretCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
	}

	public boolean canBeCast(GameContext context, Player player) {
		return context.getLogic().canPlaySecret(player, this);
	}

	public void setTriggerAndEffect(GameEventTrigger trigger, Spell effect) {
		SecretTrigger secretTrigger = new SecretTrigger(trigger);
		setSecret(new Secret(secretTrigger, effect, this));
	}
	
	public void setSecret(Secret secret) {
		Spell spell = new AddSecretSpell(secret);
		spell.setTarget(EntityReference.FRIENDLY_HERO);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(spell);
	}

}
