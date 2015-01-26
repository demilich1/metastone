package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.EmeraldDrake;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.LaughingSister;
import net.demilich.metastone.game.cards.concrete.tokens.spells.Dream;
import net.demilich.metastone.game.cards.concrete.tokens.spells.Nightmare;
import net.demilich.metastone.game.cards.concrete.tokens.spells.YseraAwakens;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ReceiveRandomCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;

public class Ysera extends MinionCard {

	public Ysera() {
		super("Ysera", 4, 12, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("At the end of your turn, draw a Dream Card.");
		setRace(Race.DRAGON);
	}

	@Override
	public int getTypeId() {
		return 233;
	}

	@Override
	public Minion summon() {
		Minion ysera = createMinion();
		ysera.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.YSERA);
		SpellDesc receiveDreamCard = ReceiveRandomCardSpell.create(TargetPlayer.SELF, new Dream(), new EmeraldDrake(), new LaughingSister(),
				new Nightmare(), new YseraAwakens());
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), receiveDreamCard);
		ysera.setSpellTrigger(trigger);
		return ysera;
	}
}
