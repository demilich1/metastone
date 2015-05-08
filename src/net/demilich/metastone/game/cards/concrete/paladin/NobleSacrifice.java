package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.cards.concrete.tokens.paladin.Defender;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonNewAttackTargetSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SummonNewTargetTrigger;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;

public class NobleSacrifice extends SecretCard {

	public NobleSacrifice() {
		super("Noble Sacrifice", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When an enemy attacks, summon a 2/1 Defender as the new target.");
		
		SpellDesc decoySpell = SummonNewAttackTargetSpell.create(new Defender());
		// set actionType to ActionType.PHYSICAL_ATTACK
		setSecret(new Secret(new SummonNewTargetTrigger(null), decoySpell, this));
	}
	
	@Override
	public int getTypeId() {
		return 253;
	}
}
