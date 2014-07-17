package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.HeroAttackedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ExplosiveTrap extends SecretCard {

	public ExplosiveTrap() {
		super("Explosive Trap", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Secret: When your hero is attacked, deal $2 damage to all enemies.");
		
		Spell damageSpell = new DamageSpell(2);
		damageSpell.setTarget(EntityReference.ENEMY_CHARACTERS);
		setTriggerAndEffect(new HeroAttackedTrigger(), damageSpell);
	}



	@Override
	public int getTypeId() {
		return 32;
	}
}
