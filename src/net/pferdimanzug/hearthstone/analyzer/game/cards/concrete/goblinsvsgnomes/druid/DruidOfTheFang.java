package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens.CobraForm;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DruidOfTheFang extends MinionCard {

	public DruidOfTheFang() {
		super("Druid of the Fang", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
		setDescription("Battlecry: If you have a Beast, transform this minion into a 7/7.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion druidOfTheFang = createMinion();
		SpellDesc transformSpell = TransformMinionSpell.create(new CobraForm());
		transformSpell.setTarget(EntityReference.SELF);
		Battlecry battleCry = Battlecry.createBattlecry(transformSpell);
		battleCry.setCondition((context, player) -> SpellUtils.hasMinionOfRace(player, Race.BEAST));
		druidOfTheFang.setBattlecry(battleCry);
		return druidOfTheFang;
	}

}
