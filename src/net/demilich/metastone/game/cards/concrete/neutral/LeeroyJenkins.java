package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Whelp;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class LeeroyJenkins extends MinionCard {

	public LeeroyJenkins() {
		super("Leeroy Jenkins", 6, 2, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Charge. Battlecry: Summon two 1/1 Whelps for your opponent.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 153;
	}
	
	@Override
	public Minion summon() {
		Minion leeroyJenkins = createMinion(GameTag.CHARGE);
		SpellDesc summonSpell = SummonSpell.create(TargetPlayer.OPPONENT, new Whelp(), new Whelp());
		leeroyJenkins.setBattlecry(BattlecryAction.createBattlecry(summonSpell));
		return leeroyJenkins;
	}
}
