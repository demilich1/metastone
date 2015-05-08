package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.custom.ReceiveSparePartSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class TinkertownTechnician extends MinionCard {

	public TinkertownTechnician() {
		super("Tinkertown Technician", 3, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Battlecry: If you control a Mech, gain +1/+1 and put a Spare Part in your hand.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 548;
	}

	@Override
	public Minion summon() {
		Minion tinkertownTechnician = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, +1, +1);
		SpellDesc sparePartSpell = ReceiveSparePartSpell.create(TargetPlayer.SELF);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MetaSpell.create(buffSpell, sparePartSpell));
		//battlecry.setCondition((context, player) -> SpellUtils.hasMinionOfRace(player, Race.MECH));
		tinkertownTechnician.setBattlecry(battlecry);
		return tinkertownTechnician;
	}
}
