package net.demilich.metastone.tests;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.HeroAttackedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;


public class TestSecretCard extends SecretCard {

	public TestSecretCard() {
		this(1);
	}
	
	public TestSecretCard(int damage) {
		super("Trap", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Secret for unit testing. Deals $" + damage + " damage to all enemies");
		setCollectible(false);
		
		SpellDesc damageSpell = DamageSpell.create(damage);
		damageSpell.setTarget(EntityReference.ENEMY_CHARACTERS);
		setTriggerAndEffect(new HeroAttackedTrigger(), damageSpell);
	}

}
