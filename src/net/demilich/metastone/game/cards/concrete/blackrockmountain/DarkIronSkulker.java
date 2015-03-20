package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DarkIronSkulker extends MinionCard {

	public DarkIronSkulker() {
		super("Dark Iron Skulker", 4, 3, Rarity.RARE, HeroClass.ROGUE, 5);
		setDescription("Battlecry: Deal 2 damage to all undamaged enemy minions.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion darkIronSkulker = createMinion();
		SpellDesc aoeBackstab = DamageSpell.create(EntityReference.ENEMY_MINIONS, 2, entity -> !((Actor)entity).isWounded(), false);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(aoeBackstab);
		darkIronSkulker.setBattlecry(battlecry);
		return darkIronSkulker;
	}

}
