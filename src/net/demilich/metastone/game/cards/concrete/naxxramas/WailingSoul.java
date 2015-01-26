package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class WailingSoul extends MinionCard {

	public WailingSoul() {
		super("Wailing Soul", 3, 5, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Silence your other minions.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 403;
	}

	@Override
	public Minion summon() {
		Minion wailingSoul = createMinion();
		SpellDesc silenceOwnMinions = SilenceSpell.create();
		silenceOwnMinions.setTarget(EntityReference.FRIENDLY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(silenceOwnMinions);
		wailingSoul.setBattlecry(battlecry);
		return wailingSoul;
	}
}
