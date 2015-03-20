package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.targeting.TargetSelection;

public class RendBlackhand extends MinionCard {

	public RendBlackhand() {
		super("Rend Blackhand", 8, 4, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("Battlecry: If you're holding a Dragon, destroy a Legendary minion.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion rendBlackhand = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(DestroySpell.create(), TargetSelection.MINIONS);
		battlecry.setEntityFilter(entity -> {
			Minion minion = (Minion) entity;
			return minion.getSourceCard().getRarity() == Rarity.LEGENDARY;
		});
		battlecry.setCondition((context, player) -> SpellUtils.holdsMinionOfRace(player, Race.DRAGON));
		rendBlackhand.setBattlecry(battlecry);
		return rendBlackhand;
	}

}
