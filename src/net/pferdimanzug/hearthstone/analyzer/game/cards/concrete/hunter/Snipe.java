package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionCardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Snipe extends SecretCard {

	public Snipe() {
		super("Snipe", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Secret: When your opponent plays a minion, deal $4 damage to it.");
		
		Spell damageSpell = new DamageSpell(4);
		damageSpell.setTarget(EntityReference.EVENT_TARGET);
		setTriggerAndEffect(new MinionCardPlayedTrigger(), damageSpell);
	}



	@Override
	public int getTypeId() {
		return 45;
	}
}
