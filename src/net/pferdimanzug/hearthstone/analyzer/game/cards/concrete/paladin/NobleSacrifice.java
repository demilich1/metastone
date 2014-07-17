package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonNewAttackTargetSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SummonNewTargetTrigger;

public class NobleSacrifice extends SecretCard {

	private class Defender extends MinionCard {

		public Defender() {
			super("Defender", 2, 1, Rarity.COMMON, HeroClass.PALADIN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}
	
	public NobleSacrifice() {
		super("Noble Sacrifice", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When an enemy attacks, summon a 2/1 Defender as the new target.");
		
		Spell decoySpell = new SummonNewAttackTargetSpell(new Defender());
		setTriggerAndEffect(new SummonNewTargetTrigger(ActionType.PHYSICAL_ATTACK), decoySpell);
	}
	



	@Override
	public int getTypeId() {
		return 253;
	}
}
