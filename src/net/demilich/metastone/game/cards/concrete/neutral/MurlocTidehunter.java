package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.MurlocScout;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MurlocTidehunter extends MinionCard {

	public MurlocTidehunter() {
		super("Murloc Tidehunter", 2, 1, Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Battlecry: Summon a 1/1 Murloc Scout.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 172;
	}

	@Override
	public Minion summon() {
		Minion murlocTidehunter = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(new MurlocScout()), TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		murlocTidehunter.setBattlecry(battlecry);
		return murlocTidehunter;
	}
}
