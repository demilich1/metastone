package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.Imp;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ImpMaster extends MinionCard {

	public ImpMaster() {
		super("Imp Master", 1, 5, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("At the end of your turn, deal 1 damage to this minion and summon a 1/1 Imp.");
	}

	@Override
	public int getTypeId() {
		return 144;
	}
	
	@Override
	public Minion summon() {
		Minion impMaster = createMinion();
		SpellDesc damageSelfSpell = DamageSpell.create(1);
		damageSelfSpell.setTarget(EntityReference.SELF);
		SpellDesc summonSpell = SummonSpell.create(new Imp());
		summonSpell.setTarget(EntityReference.NONE);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), MetaSpell.create(damageSelfSpell, summonSpell));
		impMaster.setSpellTrigger(trigger);
		return impMaster;
	}
}
