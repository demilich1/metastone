package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDeathTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

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
