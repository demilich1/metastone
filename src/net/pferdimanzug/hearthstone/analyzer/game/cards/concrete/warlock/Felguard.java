package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ModifyMaxManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

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
		Battlecry battlecry = Battlecry.createBattlecry(ModifyMaxManaSpell.create(-1, TargetPlayer.SELF));
		felguard.setBattlecry(battlecry);
		return felguard;
	}
}
