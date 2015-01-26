package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class KingOfBeasts extends MinionCard {

	public KingOfBeasts() {
		super("King of Beasts", 2, 6, Rarity.RARE, HeroClass.HUNTER, 5);
		setDescription("Taunt. Battlecry: Gain +1 Attack for each other Beast you have.");
		setRace(Race.BEAST);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 489;
	}



	@Override
	public Minion summon() {
		Minion kingOfBeasts = createMinion(GameTag.TAUNT);
		SpellDesc buffSpell = BuffSpell.create((context, player, entity) -> SpellUtils.hasHowManyOfRace(player, Race.BEAST), null);
		buffSpell.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell);
		kingOfBeasts.setBattlecry(battlecry);
		return kingOfBeasts;
	}
}
