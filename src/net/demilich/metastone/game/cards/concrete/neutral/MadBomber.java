package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageRandomSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MadBomber extends MinionCard {

	public MadBomber() {
		super("Mad Bomber", 3, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Deal 3 damage randomly split between all other characters.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 159;
	}

	@Override
	public Minion summon() {
		Minion madBomber = createMinion();
		SpellDesc spell = DamageRandomSpell.create(1, 3);
		spell.setTarget(EntityReference.ALL_CHARACTERS);
		Battlecry battlecry = Battlecry.createBattlecry(spell, TargetSelection.NONE);
		madBomber.setBattlecry(battlecry);
		return madBomber;
	}
}
