package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.HeroAttackedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.TargetAttackerSecret;

public class Vaporize extends SecretCard {

	public Vaporize() {
		super("Vaporize", Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Secret: When a minion attacks your hero, destroy it.");
		
		Spell destroySpell = new DestroySpell();
		Secret secret = new TargetAttackerSecret(new HeroAttackedTrigger(), destroySpell, this);
		setSecret(secret);
	}

}
