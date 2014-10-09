package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.Emboldener3000;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.HomingChicken;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.Poultryizer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.RepairBot;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;

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
