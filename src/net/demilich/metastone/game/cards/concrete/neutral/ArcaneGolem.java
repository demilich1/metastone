package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ModifyMaxManaSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		Minion arcaneGolem = createMinion(GameTag.CHARGE);
		SpellDesc battlecrySpell = ModifyMaxManaSpell.create(1, TargetPlayer.OPPONENT, false);
		Battlecry battlecry = Battlecry.createBattlecry(battlecrySpell);
		arcaneGolem.setBattlecry(battlecry);
		return arcaneGolem;
	}
}
