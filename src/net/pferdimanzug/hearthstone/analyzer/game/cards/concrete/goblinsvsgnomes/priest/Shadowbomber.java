package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Shadowbomber extends MinionCard {

	public Shadowbomber() {
		super("Shadowbomber", 2, 1, Rarity.EPIC, HeroClass.PRIEST, 1);
		setDescription("Battlecry: Deal 3 damage to each hero.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 561;
	}



	@Override
	public Minion summon() {
		Minion shadowbomber = createMinion();
		SpellDesc damageOpponent = DamageSpell.create(3);
		damageOpponent.setTarget(EntityReference.ENEMY_HERO);
		SpellDesc damageSelf = DamageSpell.create(3);
		damageSelf.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(MetaSpell.create(damageOpponent, damageSelf));
		shadowbomber.setBattlecry(battlecry);
		return shadowbomber;
	}
}
