package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class RagnarosTheFirelord extends MinionCard {

	public RagnarosTheFirelord() {
		super("Ragnaros the Firelord", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("Can't Attack.  At the end of your turn, deal 8 damage to a random enemy.");
	}

	@Override
	public int getTypeId() {
		return 186;
	}



	@Override
	public Minion summon() {
		Minion ragnaros = createMinion(GameTag.CANNOT_ATTACK);
		Spell damageSpell = new DamageRandomSpell(8, 1);
		damageSpell.setTarget(EntityReference.ENEMY_CHARACTERS);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), damageSpell);
		ragnaros.setSpellTrigger(trigger);
		return ragnaros;
	}
}
