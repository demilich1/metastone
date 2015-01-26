package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.custom.ReceiveSparePartSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class Toshley extends MinionCard {

	public Toshley() {
		super("Toshley", 5, 7, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Battlecry and Deathrattle: Add a Spare Part to your hand.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 549;
	}



	@Override
	public Minion summon() {
		Minion toshley = createMinion();
		SpellDesc sparePartSpell = ReceiveSparePartSpell.create(TargetPlayer.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(sparePartSpell);
		toshley.setBattlecry(battlecry);
		toshley.addDeathrattle(sparePartSpell);
		return toshley;
	}
}
