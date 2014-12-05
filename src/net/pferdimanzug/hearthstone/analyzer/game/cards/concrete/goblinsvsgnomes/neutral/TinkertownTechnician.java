package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ReceiveSparePartSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TinkertownTechnician extends MinionCard {

	public TinkertownTechnician() {
		super("Tinkertown Technician", 3, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Battlecry: If you control a Mech, gain +1/+1 and put a Spare Part in your hand.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion tinkertownTechnician = createMinion();
		SpellDesc buffSpell = BuffSpell.create(+1, +1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellDesc sparePartSpell = ReceiveSparePartSpell.create(TargetPlayer.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(MetaSpell.create(buffSpell, sparePartSpell));
		battlecry.setCondition((context, player) -> SpellUtils.hasMinionOfRace(player, Race.MECH));
		tinkertownTechnician.setBattlecry(battlecry);
		return tinkertownTechnician;
	}

}
