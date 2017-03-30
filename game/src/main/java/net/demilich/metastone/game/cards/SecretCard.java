package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.desc.SecretCardDesc;
import net.demilich.metastone.game.spells.AddSecretSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.types.Secret;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SecretCard extends SpellCard {

	public SecretCard(SecretCardDesc desc) {
		super(desc);
		GameEventTrigger trigger = desc.trigger.create();
		setSecret(new Secret(trigger, desc.spell, this));
		setAttribute(Attribute.SECRET);
	}

	public boolean canBeCast(GameContext context, Player player) {
		return context.getLogic().canPlaySecret(player, this);
	}

	public void setSecret(Secret secret) {
		SpellDesc spell = AddSecretSpell.create(secret);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(spell);
	}

}
