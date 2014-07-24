package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class EliteTaurenChieftain extends MinionCard {

	private class IAmMurloc extends SpellCard {

		private class IAmMurlocSpell extends Spell {

			@Override
			protected void onCast(GameContext context, Player player, Entity target) {
				int numberOfMurlocs = ThreadLocalRandom.current().nextInt(3, 6);
				MinionCard murlocCard = new Murloc();
				for (int i = 0; i < numberOfMurlocs; i++) {
					context.getLogic().summon(player.getId(), murlocCard.summon(), null, null, false);
				}
			}

		}

		private class Murloc extends MinionCard {

			public Murloc() {
				super("Murloc", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
				setRace(Race.MURLOC);
				setCollectible(false);
			}

			@Override
			public Minion summon() {
				return createMinion();
			}

		}

		public IAmMurloc() {
			super("I Am Murloc", Rarity.FREE, HeroClass.ANY, 4);
			setDescription("Summon three, four, or five 1/1 Murlocs.");
			setCollectible(false);

			setSpell(new IAmMurlocSpell());
			setTargetRequirement(TargetSelection.NONE);
		}

	}

	private class PowerOfTheHorde extends SpellCard {

		public PowerOfTheHorde() {
			super("Power of the Horde", Rarity.FREE, HeroClass.ANY, 4);
			setDescription("Summon a random Horde Warrior.");
			setCollectible(false);

			setSpell(new SummonRandomSpell(new FrostwolfGrunt(), new TaurenWarrior(), new SenjinShieldmasta(), new ThrallmarFarseer(),
					new SilvermoonGuardian(), new CairneBloodhoof()));
			setTargetRequirement(TargetSelection.NONE);
		}

	}

	private class RoguesDoIt extends SpellCard {

		public RoguesDoIt() {
			super("Rogues Do it...", Rarity.FREE, HeroClass.ANY, 4);
			setDescription("Deal 4 damage. Draw a card.");
			setCollectible(false);

			setSpell(new MetaSpell(new DamageSpell(4), new DrawCardSpell()));
			setTargetRequirement(TargetSelection.ANY);
		}

	}

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
		Spell randomRockCard = new ReceiveRandomCardSpell(TargetPlayer.BOTH, new IAmMurloc(), new PowerOfTheHorde(), new RoguesDoIt());
		eliteTaurenChieftain.setBattlecry(Battlecry.createBattlecry(randomRockCard));
		return eliteTaurenChieftain;
	}
}
