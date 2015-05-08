package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class TroggzorTheEarthinator extends MinionCard {

	public TroggzorTheEarthinator() {
		super("Troggzor the Earthinator", 6, 6, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("Whenever your opponent casts a spell, summon a Burly Rockjaw Trogg.");
	}

	@Override
	public int getTypeId() {
		return 550;
	}

	@Override
	public Minion summon() {
		Minion troggzor = createMinion();
		SpellDesc summonTrogg = SummonSpell.create(new BurlyRockjawTrogg());
		//SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.OPPONENT), summonTrogg);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(null), summonTrogg);
		troggzor.setSpellTrigger(trigger);
		return troggzor;
	}
}
