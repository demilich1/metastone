package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
