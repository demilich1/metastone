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
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BlackwingTechnician extends MinionCard {

	public BlackwingTechnician() {
		super("Blackwing Technician", 2, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Battlecry: If you're holding a Dragon, gain +1/+1.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 615;
	}

	@Override
	public Minion summon() {
		Minion blackwingTechnician = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, 1, 1);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell);
		//battlecry.setCondition((context, player) -> SpellUtils.holdsMinionOfRace(player, Race.DRAGON));
		blackwingTechnician.setBattlecry(battlecry);
		return blackwingTechnician;
	}
}
