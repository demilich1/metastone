package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BlackwindCorruptor extends MinionCard {

	public BlackwindCorruptor() {
		super("Blackwind Corruptor", 5, 4, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Battlecry: If you're holding a Dragon, deal 3 damage.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion blackwindCorruptor = createMinion();
		SpellDesc damageSpell = DamageSpell.create(3);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(damageSpell, TargetSelection.ANY);
		//battlecry.setCondition((context, player) -> SpellUtils.holdsMinionOfRace(player, Race.DRAGON));
		blackwindCorruptor.setBattlecry(battlecry);
		return blackwindCorruptor;
	}



	@Override
	public int getTypeId() {
		return 625;
	}
}
