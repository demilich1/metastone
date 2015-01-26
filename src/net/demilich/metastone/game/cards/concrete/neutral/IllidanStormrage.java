package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.FlameOfAzzinoth;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.CardPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class IllidanStormrage extends MinionCard {

	public IllidanStormrage() {
		super("Illidan Stormrage", 7, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Whenever you play a card, summon a 2/1 Flame of Azzinoth.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 143;
	}

	@Override
	public Minion summon() {
		Minion illidanStormrage = createMinion();
		SpellDesc summonSpell = SummonSpell.create(new FlameOfAzzinoth());
		SpellTrigger trigger = new SpellTrigger(new CardPlayedTrigger(TargetPlayer.SELF), summonSpell);
		illidanStormrage.setSpellTrigger(trigger);
		return illidanStormrage;
	}
}
