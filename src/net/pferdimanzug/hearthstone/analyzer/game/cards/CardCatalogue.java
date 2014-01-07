package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.Claw;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.HealingTouch;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.Innervate;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.IronbarkProtector;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.MarkOfTheWild;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.Moonfire;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.SavageRoar;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.Starfire;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.Swipe;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.WildGrowth;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.AnimalCompanion;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.ArcaneShot;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.Houndmaster;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.HuntersMark;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.KillCommand;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.MultiShot;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.StarvingBuzzard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.TimberWolf;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.Tracking;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter.TundraRhino;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.ArcaneExplosion;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.ArcaneIntellect;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.ArcaneMissiles;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.Fireball;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.Flamestrike;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.FrostNova;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.Frostbolt;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.MirrorImage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.Polymorph;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.WaterElemental;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AbusiveSergeant;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AcidicSwampOoze;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AcolyteOfPain;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AmaniBerserker;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AncientBrewmaster;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.AngryChicken;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Archmage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.ArgentCommander;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.ArgentSquire;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.BloodfenRaptor;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.BloodsailRaider;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.BluegillWarrior;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.BootyBayBodyguard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.BoulderfistOgre;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.ChillwindYeti;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.CoreHound;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.DalaranMage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.DarkIronDwarf;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.DarkscaleHealer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.DragonlingMechanic;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.EarthenRingFarseer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.ElvenArcher;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.FrostwolfGrunt;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.FrostwolfWarlord;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.GnomishInventor;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.GoldshireFootman;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.GurubashiBerserker;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.IronforgeRifleman;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.IronfurGrizzly;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.KoboldGeomancer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.LordOfTheArena;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.MagmaRager;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.MurlocRaider;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.MurlocTidehunter;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Nightblade;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.NoviceEngineer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.OasisSnapjaw;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.OgreMagi;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.RagingWorgen;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.RaidLeader;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.RazorfenHunter;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.RecklessRocketeer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.RiverCrocolisk;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.SenjinShieldmasta;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.ShatteredSunCleric;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Shieldbearer;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.SilverbackPatriarch;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.StonetuskBoar;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.StormpikeCommando;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.StormwindChampion;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.StormwindKnight;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TaurenWarrior;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.VoodooDoctor;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.WarGolem;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Wisp;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Wolfrider;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.ArgentProtector;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.BlessingOfKings;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.BlessingOfMight;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.BlessingOfWisdom;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.Consecration;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.GuardianOfKings;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.HammerOfWrath;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.HandOfProtection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.HolyLight;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.Humility;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.LightsJustice;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.TruesilverChampion;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.CircleOfHealing;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.DivineSpirit;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.HolyNova;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.HolySmite;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.MindBlast;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.MindControl;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.MindVision;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.NorthshireCleric;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.PowerWordShield;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.ShadowWordDeath;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.ShadowWordPain;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.Assassinate;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.AssassinsBlade;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.Backstab;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.ColdBlood;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.DeadlyPoison;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.FanOfKnives;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.Sap;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.Shiv;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.SinisterStrike;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.Sprint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue.Vanish;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.AncestralHealing;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.Bloodlust;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.FireElemental;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.FrostShock;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.Hex;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.RockbiterWeapon;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.TotemicMight;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.Windfury;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman.Windspeaker;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.BloodImp;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.Corruption;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.DrainLife;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.DreadInfernal;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.Hellfire;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.MortalCoil;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.SacrificialPact;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.ShadowBolt;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.Soulfire;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.Succubus;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock.Voidwalker;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.ArathiWeaponsmith;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.ArcaniteReaper;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.BattleRage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Charge;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Cleave;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.CruelTaskmaster;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Execute;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.FieryWarAxe;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.GrommashHellscream;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.HeroicStrike;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.KorkronElite;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.ShieldBlock;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.WarsongCommander;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Whirlwind;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public class CardCatalogue {
	
	private final static CardCollection<Card> cards = new CardCollection<Card>();

	static {
		cards.add(new Claw());
		cards.add(new HealingTouch());
		cards.add(new Innervate());
		cards.add(new IronbarkProtector());
		cards.add(new MarkOfTheWild());
		cards.add(new Moonfire());
		cards.add(new SavageRoar());
		cards.add(new Starfire());
		cards.add(new Swipe());
		cards.add(new WildGrowth());
		cards.add(new AnimalCompanion());
		cards.add(new ArcaneShot());
		cards.add(new Houndmaster());
		cards.add(new HuntersMark());
		cards.add(new KillCommand());
		cards.add(new MultiShot());
		cards.add(new StarvingBuzzard());
		cards.add(new TimberWolf());
		cards.add(new Tracking());
		cards.add(new TundraRhino());
		cards.add(new ArcaneExplosion());
		cards.add(new ArcaneIntellect());
		cards.add(new ArcaneMissiles());
		cards.add(new Fireball());
		cards.add(new Flamestrike());
		cards.add(new Frostbolt());
		cards.add(new FrostNova());
		cards.add(new MirrorImage());
		cards.add(new Polymorph());
		cards.add(new WaterElemental());
		cards.add(new AbusiveSergeant());
		cards.add(new AcidicSwampOoze());
		cards.add(new AcolyteOfPain());
		cards.add(new AmaniBerserker());
		cards.add(new AncientBrewmaster());
		cards.add(new AngryChicken());
		cards.add(new Archmage());
		cards.add(new ArgentCommander());
		cards.add(new ArgentSquire());
		cards.add(new BloodfenRaptor());
		cards.add(new BloodsailRaider());
		cards.add(new BluegillWarrior());
		cards.add(new BootyBayBodyguard());
		cards.add(new BoulderfistOgre());
		cards.add(new ChillwindYeti());
		cards.add(new CoreHound());
		cards.add(new DalaranMage());
		cards.add(new DarkIronDwarf());
		cards.add(new DarkscaleHealer());
		cards.add(new DragonlingMechanic());
		cards.add(new EarthenRingFarseer());
		cards.add(new ElvenArcher());
		cards.add(new FrostwolfGrunt());
		cards.add(new FrostwolfWarlord());
		cards.add(new GnomishInventor());
		cards.add(new GoldshireFootman());
		cards.add(new GurubashiBerserker());
		cards.add(new IronbarkProtector());
		cards.add(new IronforgeRifleman());
		cards.add(new IronfurGrizzly());
		cards.add(new KoboldGeomancer());
		cards.add(new LordOfTheArena());
		cards.add(new MagmaRager());
		cards.add(new MurlocRaider());
		cards.add(new MurlocTidehunter());
		cards.add(new Nightblade());
		cards.add(new NoviceEngineer());
		cards.add(new OasisSnapjaw());
		cards.add(new OgreMagi());
		cards.add(new RagingWorgen());
		cards.add(new RaidLeader());
		cards.add(new RazorfenHunter());
		cards.add(new RecklessRocketeer());
		cards.add(new RiverCrocolisk());
		cards.add(new SenjinShieldmasta());
		cards.add(new ShatteredSunCleric());
		cards.add(new Shieldbearer());
		cards.add(new SilverbackPatriarch());
		cards.add(new StonetuskBoar());
		cards.add(new StormpikeCommando());
		cards.add(new StormwindChampion());
		cards.add(new StormwindKnight());
		cards.add(new TaurenWarrior());
		cards.add(new TheCoin());
		cards.add(new VoodooDoctor());
		cards.add(new WarGolem());
		cards.add(new Wisp());
		cards.add(new Wolfrider());
		cards.add(new ArgentProtector());
		cards.add(new BlessingOfKings());
		cards.add(new BlessingOfMight());
		cards.add(new BlessingOfWisdom());
		cards.add(new Consecration());
		cards.add(new GuardianOfKings());
		cards.add(new HammerOfWrath());
		cards.add(new HandOfProtection());
		cards.add(new HolyLight());
		cards.add(new Humility());
		cards.add(new LightsJustice());
		cards.add(new TruesilverChampion());
		cards.add(new CircleOfHealing());
		cards.add(new DivineSpirit());
		cards.add(new HolyNova());
		cards.add(new HolySmite());
		cards.add(new MindBlast());
		cards.add(new MindControl());
		cards.add(new MindVision());
		cards.add(new NorthshireCleric());
		cards.add(new PowerWordShield());
		cards.add(new ShadowWordDeath());
		cards.add(new ShadowWordPain());
		cards.add(new Assassinate());
		cards.add(new AssassinsBlade());
		cards.add(new Backstab());
		cards.add(new ColdBlood());
		cards.add(new DeadlyPoison());
		cards.add(new FanOfKnives());
		cards.add(new Sap());
		cards.add(new Shiv());
		cards.add(new SinisterStrike());
		cards.add(new Sprint());
		cards.add(new Vanish());
		cards.add(new AncestralHealing());
		cards.add(new Bloodlust());
		cards.add(new FireElemental());
		cards.add(new FrostShock());
		cards.add(new Hex());
		cards.add(new RockbiterWeapon());
		cards.add(new TotemicMight());
		cards.add(new Windfury());
		cards.add(new Windspeaker());
		cards.add(new BloodImp());
		cards.add(new Corruption());
		cards.add(new DrainLife());
		cards.add(new DreadInfernal());
		cards.add(new Hellfire());
		cards.add(new MortalCoil());
		cards.add(new SacrificialPact());
		cards.add(new ShadowBolt());
		cards.add(new Soulfire());
		cards.add(new Succubus());
		cards.add(new Voidwalker());
		cards.add(new ArathiWeaponsmith());
		cards.add(new ArcaniteReaper());
		cards.add(new BattleRage());
		cards.add(new Charge());
		cards.add(new Cleave());
		cards.add(new CruelTaskmaster());
		cards.add(new Execute());
		cards.add(new FieryWarAxe());
		cards.add(new GrommashHellscream());
		cards.add(new HeroicStrike());
		cards.add(new KorkronElite());
		cards.add(new ShieldBlock());
		cards.add(new WarsongCommander());
		cards.add(new Whirlwind());
	}

	public static Card getCardByName(String name) {
		for (Card card : cards) {
			if (card.getName().equals(name)) {
				return card.clone();
			}
		}

		return null;
	}
	
	public static Card getCardById(int id) {
		for (Card card : cards) {
			if (card.getId() == id) {
				return card.clone();
			}
		}

		return null;
	}

	public static CardCollection<Card> query(CardType cardType, Rarity rarity, HeroClass heroClass) {
		CardCollection<Card> result = new CardCollection<Card>();
		for (Card card : cards) {
			if (!card.isCollectible()) {
				continue;
			}
			if (cardType != null && card.getCardType() != cardType) {
				continue;
			}
			if (rarity != null && card.getRarity() != rarity) {
				continue;
			}
			if (heroClass != null && card.getClassRestriction() != heroClass) {
				continue;
			}
			result.add(card.clone());
		}

		return result;
	}

}
