package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.BoomBot;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DrBoom extends MinionCard {

	public DrBoom() {
		super("Dr. Boom", 7, 7, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("Battlecry: Summon two 1/1 Boom Bots. WARNING: Bots may explode.");
	}

	@Override
	public int getTypeId() {
		return 509;
	}

	@Override
	public Minion summon() {
		Minion drBoom = createMinion();
		SpellDesc summonSpell1 = SummonSpell.create(RelativeToSource.LEFT, new BoomBot());
		SpellDesc summonSpell2 = SummonSpell.create(RelativeToSource.RIGHT, new BoomBot());
		Battlecry battlecry = Battlecry.createBattlecry(MetaSpell.create(summonSpell1, summonSpell2));
		battlecry.setResolvedLate(true);
		drBoom.setBattlecry(battlecry);
		return drBoom;
	}
}
