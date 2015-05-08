package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddSecretSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SecretCard extends SpellCard {

	public SecretCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
		setTag(GameTag.SECRET);
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
