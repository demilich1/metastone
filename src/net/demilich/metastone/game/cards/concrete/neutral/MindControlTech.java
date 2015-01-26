package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.MindControlRandomSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MindControlTech extends MinionCard {

	public MindControlTech() {
		super("Mind Control Tech", 3, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: If your opponent has 4 or more minions, take control of one at random.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 166;
	}

	@Override
	public Minion summon() {
		Minion mindControlTech = createMinion();
		SpellDesc mindControlSpell = MindControlRandomSpell.create();
		mindControlSpell.setTarget(EntityReference.ENEMY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(mindControlSpell);
		battlecry.setCondition((context, player)-> {
			Player opponent = context.getOpponent(player);
			return context.getMinionCount(opponent) >= 4;
		});
		mindControlTech.setBattlecry(battlecry);
		return mindControlTech;
	}
}
