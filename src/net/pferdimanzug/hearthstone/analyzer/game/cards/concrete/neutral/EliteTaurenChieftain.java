package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells.IAmMurloc;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells.PowerOfTheHorde;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells.RoguesDoIt;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class EliteTaurenChieftain extends MinionCard {

	public EliteTaurenChieftain() {
		super("Elite Tauren Chieftain", 5, 5, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Give both players the power to ROCK! (with a Power Chord card)");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 122;
	}

	@Override
	public Minion summon() {
		Minion eliteTaurenChieftain = createMinion();
		SpellDesc randomRockCard = ReceiveRandomCardSpell.create(TargetPlayer.BOTH, new IAmMurloc(), new PowerOfTheHorde(), new RoguesDoIt());
		eliteTaurenChieftain.setBattlecry(Battlecry.createBattlecry(randomRockCard));
		return eliteTaurenChieftain;
	}
}
