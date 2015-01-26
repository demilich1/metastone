package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Emboldener3000;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.HomingChicken;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Poultryizer;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.RepairBot;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonRandomSpell;

public class GelbinMekkatorque extends MinionCard {

	public GelbinMekkatorque() {
		super("Gelbin Mekkatorque", 6, 6, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Battlecry: Summon an AWESOME invention.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 133;
	}

	@Override
	public Minion summon() {
		Minion gelbinMekkatorque = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonRandomSpell.create(new Emboldener3000(), new HomingChicken(), new Poultryizer(),
				new RepairBot()));
		gelbinMekkatorque.setBattlecry(battlecry);
		return gelbinMekkatorque;
	}
}
