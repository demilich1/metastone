package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ImpMaster extends MinionCard {

	private class Imp extends MinionCard {

		public Imp() {
			super("Imp", 1, 1, Rarity.RARE, HeroClass.ANY, 1);
			setTag(GameTag.RACE, Race.DEMON);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}

	public ImpMaster() {
		super("Imp Master", 1, 5, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("At the end of your turn, deal 1 damage to this minion and summon a 1/1 Imp.");
	}
	
	@Override
	public Minion summon() {
		Minion impMaster = createMinion();
		Spell damageSelfSpell = new DamageSpell(1);
		Spell summonSpell = new SummonSpell(new Imp());
		summonSpell.setTarget(EntityReference.NONE);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), new MetaSpell(damageSelfSpell, summonSpell));
		impMaster.setSpellTrigger(trigger);
		return impMaster;
	}

}
