package net.demilich.metastone.game.cards;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class CardCatalogue {

	private final static CardCollection cards = new CardCollection();

	static {
		/*
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * AxeFlinger()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * BlackwingTechnician()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * DarkIronSkulker()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * DragonEgg()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * DragonkinSorcerer()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * GrimPatron()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * HungryDragon()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * LavaShock()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.
		 * RendBlackhand()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.blackrockmountain.Whelp())
		 * ; cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid.
		 * DruidOfTheFang()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid.
		 * Malorne()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid.
		 * MechBearCat()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid.
		 * Recycle()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid.
		 * TreeOfLife()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * CallPet()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * CobraShot()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * FeignDeath()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * Gahzrilla()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * Glaivezooka()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * KingOfBeasts()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * MetaltoothLeaper()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter.
		 * SteamwheedleSniper()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * EchoOfMedivh()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * Flamecannon()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * FlameLeviathan()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * GoblinBlastmage()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * Snowchugger()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * SootSpewer()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * UnstablePortal()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage.
		 * WeeSpellstopper()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * AnnoyOTron()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * AntiqueHealbot()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * ArcaneNullifierX21()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Blingtron3000()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * BombLobber()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * BurlyRockjawTrogg()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * ClockworkGiant()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * ClockworkGnome()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Cogmaster()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * DrBoom()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * EnhanceOMechano()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * ExplosiveSheep()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * FelReaver()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * FlyingMachine()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * FoeReaper4000()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * ForceTankMax()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Gazlowe()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * GilblinStalker()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * GnomereganInfantry()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * GnomishExperimenter()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * GoblinSapper()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * HemetNesingwary()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Hobgoblin()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Illuminator()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Jeeves()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Junkbot()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * KezanMystic()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * LilExorcist()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * LostTallstrider()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * MadderBomber()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * MechanicalYeti()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Mechwarper()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * MekgineerThermaplugg()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * MicroMachine()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * MimironsHead()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * MiniMage()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * MogorTheOgre()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * OgreBrute()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * PilotedShredder()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * PilotedSkyGolem()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Puddlestomper()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Recombobulator()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * SaltyDog()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * ShipsCannon()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * SneedsOldShredder()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * SpiderTank()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * StonesplinterTrogg()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * TargetDummy()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * TinkertownTechnician()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * Toshley()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral.
		 * TroggzorTheEarthinator()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * BolvarFordragon()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * CobaltGuardian()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * Coghammer()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * MusterForBattle()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * Quartermaster()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * ScarletPurifier()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * SealOfLight()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin.
		 * ShieldedMinibot()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * Lightbomb()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * LightOfTheNaaru()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * Shadowbomber()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * Shadowboxer()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * Shrinkmeister()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * UpgradedRepairBot()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * VelensChosen()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest.
		 * Voljin()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * CogmastersWrench()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * GoblinAutoBarber()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * IronSensai()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * OgreNinja()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * OneEyedCheat()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * Sabotage()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * TinkersSharpswordOil()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue.
		 * TradePrinceGallywix()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * AncestorsCall()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * Crackle()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * DunemaulShaman()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * Neptulon()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * Powermace()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * SiltfinSpiritwalker()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * VitalityTotem()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman.
		 * WhirlingZapOmatic()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts
		 * .ArmorPlating()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts
		 * .EmergencyCoolant()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts
		 * .FinickyCloakfield()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts
		 * .ReversingSwitch()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts
		 * .RustyHorn()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts
		 * .TimeRewinder()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts
		 * .WhirlingBlades()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.
		 * BoomBot()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.
		 * BurrowingMine()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.
		 * Chicken()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.
		 * CobraForm()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.
		 * GallywixsCoin()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.
		 * V07TR0N()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * AnimaGolem()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * Darkbomb()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * Demonheart()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * FelCannon()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * FloatingWatcher()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * Implosion()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * MalGanis()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock.
		 * MistressOfPain()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * BouncingBlade()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * Crush()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * IronJuggernaut()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * OgreWarmaul()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * ScrewjankClunker()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * Shieldmaiden()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * SiegeEngine()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior.
		 * Warbot()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.AnubarAmbusher()
		 * ); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Avenge());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.BaronRivendare()
		 * ); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.DancingSwords())
		 * ; cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.DarkCultist());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Deathlord());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.DeathsBite());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Duplicate());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.EchoingOoze());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Feugen());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.HauntedCreeper()
		 * ); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.KelThuzad());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Loatheb());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.MadScientist());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Maexxna());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.NerubarWeblord()
		 * ); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.NerubianEgg());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.PoisonSeeds());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Reincarnate());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.ShadeOfNaxxramas
		 * ()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.SludgeBelcher())
		 * ; cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.SpectralKnight()
		 * ); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Stalagg());
		 * cards.add(new net.demilich.metastone.game.cards.concrete.naxxramas.
		 * StoneskinGargoyle()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.tokens.Nerubian(
		 * )); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.tokens.Slime());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.tokens.
		 * SpectralSpider()); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.tokens.Thaddius(
		 * )); cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Undertaker());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.UnstableGhoul())
		 * ; cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Voidcaller());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.WailingSoul());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.Webspinner());
		 * cards.add(new
		 * net.demilich.metastone.game.cards.concrete.naxxramas.ZombieChow());
		 */
	}

	public static void add(Card card) {
		cards.add(card);
	}

	public static CardCollection getAll() {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			result.add(card);
		}
		return result;
	}

	public static Card getCardById(int id) {
		for (Card card : cards) {
			if (card.getTypeId() == id) {
				return card.clone();
			}
		}

		return null;
	}

	public static Card getCardById(String id) {
		for (Card card : cards) {
			if (card.getCardId() != null && card.getCardId().equalsIgnoreCase(id)) {
				return card.clone();
			}
		}

		return null;
	}

	public static Card getCardByName(String name) {
		for (Card card : cards) {
			if (card.getName().equals(name)) {
				return card.clone();
			}
		}

		return null;
	}

	public static CardCollection query(CardType cardType) {
		return query(cardType, null, null);
	}

	public static CardCollection query(CardType cardType, Rarity rarity, HeroClass heroClass) {
		return query(cardType, rarity, heroClass, null);
	}
	
	public static CardCollection query(Predicate<Card> filter) {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (filter.test(card)) {
				result.add(card);
			}
		}
		return result;
	}

	public static CardCollection query(CardType cardType, Rarity rarity, HeroClass heroClass, GameTag tag) {
		CardCollection result = new CardCollection();
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
			if (tag != null && !card.hasTag(tag)) {
				continue;
			}
			result.add(card.clone());
		}

		return result;
	}

	public static CardCollection query(GameTag tag) {
		return query(null, null, null, tag);
	}
}
