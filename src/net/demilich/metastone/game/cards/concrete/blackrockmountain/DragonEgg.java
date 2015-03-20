package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageReceivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class DragonEgg extends MinionCard {

	public DragonEgg() {
		super("Dragon Egg", 0, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Whenever this minion takes damage, summon a 2/1 Whelp.");
	}

	@Override
	public Minion summon() {
		Minion dragonEgg = createMinion();
		SpellDesc summonSpell = SummonSpell.create(TargetPlayer.SELF, RelativeToSource.RIGHT, new Whelp());
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), summonSpell);
		dragonEgg.setSpellTrigger(trigger);
		return dragonEgg;
	}

}
