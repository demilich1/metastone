package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ModifyMaxManaSpell;
import net.demilich.metastone.game.spells.TargetPlayer;

public class Felguard extends MinionCard {

	public Felguard() {
		super("Felguard", 3, 5, Rarity.FREE, HeroClass.WARLOCK, 3);
		setDescription("Taunt. Battlecry: Destroy one of your Mana Crystals.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 341;
	}

	@Override
	public Minion summon() {
		Minion felguard = createMinion(GameTag.TAUNT);
		Battlecry battlecry = Battlecry.createBattlecry(ModifyMaxManaSpell.create(-1, TargetPlayer.SELF, false));
		felguard.setBattlecry(battlecry);
		return felguard;
	}
}
