package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class SiltfinSpiritwalker extends MinionCard {

	public SiltfinSpiritwalker() {
		super("Siltfin Spiritwalker", 2, 5, Rarity.EPIC, HeroClass.SHAMAN, 4);
		setDescription("Whenever another friendly Murloc dies, draw a card. Overload: (1)");
		setRace(Race.MURLOC);
		setTag(GameTag.OVERLOAD, 1);
	}

	@Override
	public int getTypeId() {
		return 580;
	}



	@Override
	public Minion summon() {
		Minion siltfinSpiritwalker = createMinion();
		SpellTrigger trigger = new SpellTrigger(new MinionDeathTrigger(TargetPlayer.SELF, Race.MURLOC), DrawCardSpell.create());
		siltfinSpiritwalker.setSpellTrigger(trigger);
		return siltfinSpiritwalker;
	}
}
