package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.DiscardCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Deathwing extends MinionCard {

	public Deathwing() {
		super("Deathwing", 12, 12, Rarity.LEGENDARY, HeroClass.ANY, 10);
		setDescription("Battlecry: Destroy all other minions and discard your hand.");
		setRace(Race.DRAGON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 114;
	}

	@Override
	public Minion summon() {
		Minion deathwing = createMinion();
		SpellDesc destroySpell = DestroySpell.create(EntityReference.ALL_MINIONS);
		SpellDesc discardSpell = DiscardCardSpell.create(DiscardCardSpell.ALL_CARDS);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MetaSpell.create(destroySpell, discardSpell));
		deathwing.setBattlecry(battlecry);
		return deathwing;
	}
}
