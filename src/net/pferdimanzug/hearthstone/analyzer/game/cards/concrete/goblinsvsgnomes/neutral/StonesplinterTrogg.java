package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class StonesplinterTrogg extends MinionCard {

	public StonesplinterTrogg() {
		super("Stonesplinter Trogg", 2, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Whenever your opponent casts a spell, gain +1 Attack.");
	}

	@Override
	public int getTypeId() {
		return 546;
	}



	@Override
	public Minion summon() {
		Minion stonesplinterTrogg = createMinion();
		SpellDesc buffSpell = BuffSpell.create(+1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.OPPONENT), buffSpell);
		stonesplinterTrogg.setSpellTrigger(trigger);
		return stonesplinterTrogg;
	}
}
