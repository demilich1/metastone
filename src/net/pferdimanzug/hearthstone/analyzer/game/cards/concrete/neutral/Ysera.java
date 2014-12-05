package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.EmeraldDrake;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.LaughingSister;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells.Dream;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells.Nightmare;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells.YseraAwakens;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

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
