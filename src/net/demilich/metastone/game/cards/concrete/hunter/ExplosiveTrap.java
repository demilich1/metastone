package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.HeroAttackedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class ExplosiveTrap extends SecretCard {

	public ExplosiveTrap() {
		super("Explosive Trap", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Secret: When your hero is attacked, deal $2 damage to all enemies.");
		
		SpellDesc damageSpell = DamageSpell.create(EntityReference.ENEMY_CHARACTERS, 2);
		setTriggerAndEffect(new HeroAttackedTrigger(), damageSpell);
	}

	@Override
	public int getTypeId() {
		return 32;
	}
}
