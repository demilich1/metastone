package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DestroyWeaponSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AcidicSwampOoze extends MinionCard {

	public AcidicSwampOoze() {
		super("Acidic Swamp Ooze", 3, 2, Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Battlecry: Destroy your opponent's weapon.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 78;
	}

	@Override
	public Minion summon() {
		Minion acidicSwampOoze = createMinion();
		SpellDesc destroyWeaponSpell = DestroyWeaponSpell.create(EntityReference.ENEMY_HERO);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(destroyWeaponSpell);
		acidicSwampOoze.setBattlecry(battlecry);
		return acidicSwampOoze;
	}
}
