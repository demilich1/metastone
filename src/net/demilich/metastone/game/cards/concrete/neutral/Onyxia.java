package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Whelp;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class Onyxia extends MinionCard {

	public Onyxia() {
		super("Onyxia", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Battlecry: Summon 1/1 Whelps until your side of the battlefield is full.");
		setRace(Race.DRAGON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 181;
	}

	private MinionCard[] getWhelps() {
		MinionCard[] whelps = new MinionCard[3];
		for (int i = 0; i < whelps.length; i++) {
			whelps[i] = new Whelp();
		}
		return whelps;
	}

	@Override
	public Minion summon() {
		Minion onyxia = createMinion();
		SpellDesc summonLeft = SummonSpell.create(RelativeToSource.LEFT, getWhelps());
		SpellDesc summonRight = SummonSpell.create(RelativeToSource.RIGHT, getWhelps());
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MetaSpell.create(summonLeft, summonRight));
		battlecry.setResolvedLate(true);
		onyxia.setBattlecry(battlecry);
		return onyxia;
	}

}
