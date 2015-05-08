package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.AnyMinionAttacksTrigger;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;

public class SnakeTrap extends SecretCard {

	public SnakeTrap() {
		super("Snake Trap", Rarity.EPIC, HeroClass.HUNTER, 2);
		setDescription("Secret: When one of your minions is attacked, summon three 1/1 Snakes.");
		//SpellDesc summonSpell = SummonSpell.create(new Snake(), new Snake(), new Snake());
		SpellDesc summonSpell = SummonSpell.create();
		setSecret(new Secret(new AnyMinionAttacksTrigger(null), summonSpell, this));
	}
	
	@Override
	public int getTypeId() {
		return 44;
	}
}
