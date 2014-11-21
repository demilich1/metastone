package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ReceiveSparePartSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class Toshley extends MinionCard {

	public Toshley() {
		super("Toshley", 5, 7, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Battlecry and Deathrattle: Add a Spare Part to your hand.");
		setTag(GameTag.BATTLECRY);
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
