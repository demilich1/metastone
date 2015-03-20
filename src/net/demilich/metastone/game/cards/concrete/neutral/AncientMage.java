package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.AddSpellPowerSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AncientMage extends MinionCard {

	public AncientMage() {
		super("Ancient Mage", 2, 5, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Give adjacent minions Spell Damage +1.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 84;
	}

	@Override
	public Minion summon() {
		Minion ancientMage = createMinion();
		SpellDesc spellpowerSpell = AddSpellPowerSpell.create(EntityReference.ADJACENT_MINIONS, 1);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(spellpowerSpell);
		battlecry.setResolvedLate(true);
		ancientMage.setBattlecry(battlecry);
		return ancientMage;
	}
}
