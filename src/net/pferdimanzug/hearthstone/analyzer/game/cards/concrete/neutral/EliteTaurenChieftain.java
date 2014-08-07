package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.IAmMurlocSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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

	private class IAmMurloc extends SpellCard {

		public IAmMurloc() {
			super("I Am Murloc", Rarity.FREE, HeroClass.ANY, 4);
			setDescription("Summon three, four, or five 1/1 Murlocs.");
			setCollectible(false);

			setSpell(IAmMurlocSpell.create());
			setTargetRequirement(TargetSelection.NONE);
		}

	}

	private class PowerOfTheHorde extends SpellCard {

		public PowerOfTheHorde() {
			super("Power of the Horde", Rarity.FREE, HeroClass.ANY, 4);
			setDescription("Summon a random Horde Warrior.");
			setCollectible(false);

			setSpell(SummonRandomSpell.create(new FrostwolfGrunt(), new TaurenWarrior(), new SenjinShieldmasta(), new ThrallmarFarseer(),
					new SilvermoonGuardian(), new CairneBloodhoof()));
			setTargetRequirement(TargetSelection.NONE);
		}

	}

	private class RoguesDoIt extends SpellCard {

		public RoguesDoIt() {
			super("Rogues Do it...", Rarity.FREE, HeroClass.ANY, 4);
			setDescription("Deal 4 damage. Draw a card.");
			setCollectible(false);

			setSpell(MetaSpell.create(DamageSpell.create(4), DrawCardSpell.create()));
			setTargetRequirement(TargetSelection.ANY);
		}

	}
}
