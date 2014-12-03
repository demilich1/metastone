package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class KingOfBeasts extends MinionCard {

	public KingOfBeasts() {
		super("King of Beasts", 2, 6, Rarity.RARE, HeroClass.HUNTER, 5);
		setDescription("Taunt. Battlecry: Gain +1 Attack for each other Beast you have.");
		setRace(Race.BEAST);
		setTag(GameTag.BATTLECRY);
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
