package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RemoveTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.HealingTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Shadowboxer extends MinionCard {

	public Shadowboxer() {
		super("Shadowboxer", 2, 3, Rarity.COMMON, HeroClass.PRIEST, 2);
		setDescription("Whenever a character is healed, deal 1 damage to a random enemy.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 562;
	}

	@Override
	public Minion summon() {
		Minion shadowboxer = createMinion();
		SpellDesc damageRandomEnemy = DamageSpell.create(1);
		damageRandomEnemy.setTarget(EntityReference.ENEMY_CHARACTERS);
		damageRandomEnemy.pickRandomTarget(true);
		SpellDesc removeStealth = RemoveTagSpell.create(GameTag.STEALTHED);
		removeStealth.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new HealingTrigger(), MetaSpell.create(damageRandomEnemy, removeStealth));
		shadowboxer.setSpellTrigger(trigger);
		return shadowboxer;
	}
}
