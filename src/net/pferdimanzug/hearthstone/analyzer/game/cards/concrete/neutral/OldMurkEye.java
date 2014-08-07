package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.OldMurkEyeSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.BoardChangedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class OldMurkEye extends MinionCard {

	public OldMurkEye() {
		super("Old Murk-Eye", 2, 4, Rarity.LEGENDARY, HeroClass.ANY, 4);
		setDescription("Charge. Has +1 Attack for each other Murloc on the battlefield.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 180;
	}

	@Override
	public Minion summon() {
		Minion oldMurkEye = createMinion(GameTag.CHARGE);
		SpellDesc buffSpell = OldMurkEyeSpell.create();
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new BoardChangedTrigger(), buffSpell);
		oldMurkEye.setSpellTrigger(trigger);
		return oldMurkEye;
	}

	
}
