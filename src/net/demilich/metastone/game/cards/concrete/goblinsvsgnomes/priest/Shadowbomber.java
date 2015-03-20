package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc damageOpponent = DamageSpell.create(EntityReference.ENEMY_HERO, 3);
		SpellDesc damageSelf = DamageSpell.create(EntityReference.FRIENDLY_HERO, 3);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MetaSpell.create(damageOpponent, damageSelf));
		shadowbomber.setBattlecry(battlecry);
		return shadowbomber;
	}
}
