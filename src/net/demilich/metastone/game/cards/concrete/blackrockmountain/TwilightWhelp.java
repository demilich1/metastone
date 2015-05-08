package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.targeting.EntityReference;

public class TwilightWhelp extends MinionCard {

	public TwilightWhelp() {
		super("Twilight Whelp", 2, 1, Rarity.COMMON, HeroClass.PRIEST, 1);
		setDescription("Battlecry: If you're holding a Dragon, gain +2 Health.");
		setRace(Race.DRAGON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion twilightWhelp = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(BuffSpell.create(EntityReference.SELF, 0, 2));
		//battlecry.setCondition((context, player) -> SpellUtils.holdsMinionOfRace(player, Race.DRAGON));
		twilightWhelp.setBattlecry(battlecry);
		return twilightWhelp;
	}



	@Override
	public int getTypeId() {
		return 645;
	}
}
