package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.TargetMinionSecret;

public class Snipe extends SecretCard {

	public Snipe() {
		super("Snipe", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Secret: When your opponent plays a minion, deal $4 damage to it.");
		
		Spell damageSpell = new DamageSpell(4);
		setSecret(new TargetMinionSecret(new MinionSummonedTrigger(), damageSpell, this));
	}

}
