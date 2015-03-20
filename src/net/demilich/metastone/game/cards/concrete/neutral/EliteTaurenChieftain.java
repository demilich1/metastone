package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.spells.IAmMurloc;
import net.demilich.metastone.game.cards.concrete.tokens.spells.PowerOfTheHorde;
import net.demilich.metastone.game.cards.concrete.tokens.spells.RoguesDoIt;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ReceiveRandomCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		eliteTaurenChieftain.setBattlecry(BattlecryAction.createBattlecry(randomRockCard));
		return eliteTaurenChieftain;
	}
}
