package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.CastRandomSpellSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.WindfurySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class EnhanceOMechano extends MinionCard {

	public EnhanceOMechano() {
		super("Enhance-o Mechano", 3, 2, Rarity.EPIC, HeroClass.ANY, 4);
		setDescription("Battlecry: Give your other minions Windfury, Taunt, or Divine Shield. (at random)");
		setRace(Race.MECH);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 510;
	}



	@Override
	public Minion summon() {
		Minion enhanceOMechano = createMinion();
		SpellDesc randomBuff = CastRandomSpellSpell.create(WindfurySpell.create(), ApplyTagSpell.create(GameTag.TAUNT),
				ApplyTagSpell.create(GameTag.DIVINE_SHIELD));
		randomBuff.setTarget(EntityReference.FRIENDLY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(randomBuff);
		enhanceOMechano.setBattlecry(battlecry);
		return enhanceOMechano;
	}
}
