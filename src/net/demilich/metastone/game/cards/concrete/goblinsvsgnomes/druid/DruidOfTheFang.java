package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.CobraForm;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DruidOfTheFang extends MinionCard {

	public DruidOfTheFang() {
		super("Druid of the Fang", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
		setDescription("Battlecry: If you have a Beast, transform this minion into a 7/7.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 478;
	}

	@Override
	public Minion summon() {
		Minion druidOfTheFang = createMinion();
		SpellDesc transformSpell = TransformMinionSpell.create(EntityReference.SELF, new CobraForm(), false);
		BattlecryAction battleCry = BattlecryAction.createBattlecry(transformSpell);
		//battleCry.setCondition((context, player) -> SpellUtils.hasMinionOfRace(player, Race.BEAST));
		druidOfTheFang.setBattlecry(battleCry);
		return druidOfTheFang;
	}
}
