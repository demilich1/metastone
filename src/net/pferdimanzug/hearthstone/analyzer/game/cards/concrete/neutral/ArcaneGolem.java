package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ModifyMaxManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

public class ArcaneGolem extends MinionCard {

	public ArcaneGolem() {
		super("Arcane Golem", 4, 2, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Charge. Battlecry: Give your opponent a Mana Crystal.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 87;
	}
	
	@Override
	public Minion summon() {
		Minion arcaneGolem = createMinion();
		Spell battlecrySpell = new ModifyMaxManaSpell(1, TargetPlayer.OPPONENT);
		Battlecry battlecry = Battlecry.createBattlecry(battlecrySpell);
		arcaneGolem.setBattlecry(battlecry);
		return arcaneGolem;
	}
}
