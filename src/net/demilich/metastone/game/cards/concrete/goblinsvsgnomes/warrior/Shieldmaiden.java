package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffHeroSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Shieldmaiden extends MinionCard {

	public Shieldmaiden() {
		super("Shieldmaiden", 5, 5, Rarity.RARE, HeroClass.WARRIOR, 6);
		setDescription("Battlecry: Gain 5 Armor.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 608;
	}

	@Override
	public Minion summon() {
		Minion shieldmaiden = createMinion();
		SpellDesc gainArmorSpell = BuffHeroSpell.create(EntityReference.FRIENDLY_HERO, 0, 5);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(gainArmorSpell);
		shieldmaiden.setBattlecry(battlecry);
		return shieldmaiden;
	}
}
