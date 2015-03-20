package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ScarletPurifier extends MinionCard {

	public ScarletPurifier() {
		super("Scarlet Purifier", 4, 3, Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Battlecry: Deal 2 damage to all minions with Deathrattle.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 556;
	}

	@Override
	public Minion summon() {
		Minion scarletPurifier = createMinion();
		SpellDesc damage = DamageSpell.create(EntityReference.ALL_MINIONS, 2, entity -> entity.hasTag(GameTag.DEATHRATTLES), false);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(damage);
		scarletPurifier.setBattlecry(battlecry);
		return scarletPurifier;
	}
}
