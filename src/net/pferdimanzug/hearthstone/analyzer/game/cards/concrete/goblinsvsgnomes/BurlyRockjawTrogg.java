package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

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

public class BurlyRockjawTrogg extends MinionCard {

	public BurlyRockjawTrogg() {
		super("Burly Rockjaw Trogg", 3, 5, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Each time your opponent cast a spell, gain +2 Attack.");
	}

	@Override
	public Minion summon() {
		Minion burlyRockjawTrogg = createMinion();
		SpellDesc buffSpell = BuffSpell.create(+2);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.OPPONENT), buffSpell);
		burlyRockjawTrogg.setSpellTrigger(trigger);
		return burlyRockjawTrogg;
	}

}
