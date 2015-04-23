package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.CopyPlayedCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class LorewalkerCho extends MinionCard {

	public LorewalkerCho() {
		super("Lorewalker Cho", 0, 4, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setDescription("Whenever a player casts a spell, put a copy into the other player's hand.");
	}

	@Override
	public int getTypeId() {
		return 158;
	}

	@Override
	public Minion summon() {
		Minion lorewalkerCho = createMinion();
		SpellDesc copySpell = CopyPlayedCardSpell.create();
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.BOTH), copySpell);
		lorewalkerCho.setSpellTrigger(trigger);
		return lorewalkerCho;
	}
}


	
