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

public class BurlyRockjawTrogg extends MinionCard {

	public BurlyRockjawTrogg() {
		super("Burly Rockjaw Trogg", 3, 5, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Each time your opponent cast a spell, gain +2 Attack.");
	}

	@Override
	public int getTypeId() {
		return 505;
	}

	@Override
	public Minion summon() {
		Minion burlyRockjawTrogg = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, +2, 0);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.OPPONENT), buffSpell);
		burlyRockjawTrogg.setSpellTrigger(trigger);
		return burlyRockjawTrogg;
	}
}
