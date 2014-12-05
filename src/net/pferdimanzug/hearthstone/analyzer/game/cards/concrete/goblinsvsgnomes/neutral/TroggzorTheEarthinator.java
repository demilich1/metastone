package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class TroggzorTheEarthinator extends MinionCard {

	public TroggzorTheEarthinator() {
		super("Troggzor the Earthinator", 6, 6, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("Whenever your opponent casts a spell, summon a Burly Rockjaw Trogg.");
	}

	@Override
	public Minion summon() {
		Minion troggzor = createMinion();
		SpellDesc summonTrogg = SummonSpell.create(new BurlyRockjawTrogg());
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.OPPONENT), summonTrogg);
		troggzor.setSpellTrigger(trigger);
		return troggzor;
	}

}
