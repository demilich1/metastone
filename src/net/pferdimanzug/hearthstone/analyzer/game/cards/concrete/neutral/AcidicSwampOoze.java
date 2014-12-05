package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroyWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc destroyWeaponSpell = DestroyWeaponSpell.create();
		destroyWeaponSpell.setTarget(EntityReference.ENEMY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(destroyWeaponSpell);
		acidicSwampOoze.setBattlecry(battlecry);
		return acidicSwampOoze;
	}
}
