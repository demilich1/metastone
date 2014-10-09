package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.paladin.Defender;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonNewAttackTargetSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SummonNewTargetTrigger;

public class NobleSacrifice extends SecretCard {

	public NobleSacrifice() {
		super("Noble Sacrifice", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When an enemy attacks, summon a 2/1 Defender as the new target.");
		
		SpellDesc decoySpell = SummonNewAttackTargetSpell.create(new Defender());
		setTriggerAndEffect(new SummonNewTargetTrigger(ActionType.PHYSICAL_ATTACK), decoySpell);
	}
	
	@Override
	public int getTypeId() {
		return 253;
	}
}
