package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.VoidTerrorBuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class VoidTerror extends MinionCard {

	public VoidTerror() {
		super("Void Terror", 3, 3, Rarity.RARE, HeroClass.WARLOCK, 3);
		setDescription("Battlecry: Destroy the minions on either side of this minion and gain their Attack and Health.");
		setRace(Race.DEMON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 357;
	}

	@Override
	public Minion summon() {
		Minion voidTerror = createMinion();
		SpellDesc buffSpell = VoidTerrorBuffSpell.create();
		buffSpell.setTarget(EntityReference.SELF);
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.ADJACENT_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(MetaSpell.create(buffSpell, destroySpell));
		battlecry.setResolvedLate(true);
		voidTerror.setBattlecry(battlecry);
		return voidTerror;
	}

	
}
