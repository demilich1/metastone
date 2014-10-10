package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ForceOfNatureTreant extends MinionCard {

	public ForceOfNatureTreant() {
		super("Treant", 2, 2, Rarity.COMMON, HeroClass.DRUID, 1);
		setDescription("Charge. At the end of the turn, destroy this minion.");
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 417;
	}

	@Override
	public Minion summon() {
		Minion treant = createMinion(GameTag.CHARGE);
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), destroySpell);
		treant.setSpellTrigger(trigger);
		return treant;
	}
}
