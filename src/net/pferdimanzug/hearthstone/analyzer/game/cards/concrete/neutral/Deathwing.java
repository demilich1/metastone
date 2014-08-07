package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DiscardCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.ALL_MINIONS);
		SpellDesc discardSpell = DiscardCardSpell.create(DiscardCardSpell.ALL_CARDS);
		Battlecry battlecry = Battlecry.createBattlecry(MetaSpell.create(destroySpell, discardSpell));
		deathwing.setBattlecry(battlecry);
		return deathwing;
	}
}
