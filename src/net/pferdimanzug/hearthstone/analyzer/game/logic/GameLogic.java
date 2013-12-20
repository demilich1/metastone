package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnStartEvent;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLogic implements IGameLogic {

	private static Logger logger = LoggerFactory.getLogger(GameLogic.class);

	public static final int MAX_MINIONS = 7;
	public static final int MAX_HAND_CARDS = 10;
	public static final int MAX_HERO_HP = 30;
	public static final int STARTER_CARDS = 3;
	public static final int MAX_MANA = 10;

	private final TargetLogic targetLogic = new TargetLogic();
	private GameContext context;

	@Override
	public boolean canPlayCard(Player player, Card card) {
		if (player.getMana() < card.getManaCost()) {
			return false;
		}
		if (card.getCardType() == CardType.HERO_POWER) {
			HeroPower power = (HeroPower) card;
			return !power.hasBeenUsed();
		} else if (card.getCardType() == CardType.MINION) {
			return player.getMinions().size() < MAX_MINIONS;
		}
		return true;
	}

	@Override
	public void damage(Entity target, int damage) {
		switch (target.getEntityType()) {
		case MINION:
			damageMinion((Entity) target, damage);
			break;
		case HERO:
			damageHero((Hero) target, damage);
			break;
		default:
			break;
		}
	}

	private void damageHero(Hero hero, int damage) {
		int damageAfterArmor = damage - hero.getArmor();
		hero.modifyArmor(-damage);

		hero.setHp(hero.getHp() - damageAfterArmor);
		logger.debug(hero.getName() + " receives " + Math.abs(damage) + " damage, hp now: " + hero.getHp() + "("
				+ hero.getArmor() + ")");
	}

	private void damageMinion(Entity minion, int damage) {
		logger.debug("Minion " + minion.getName() + " is damaged for " + damage);
		minion.setHp(minion.getHp() - damage);
		context.getEventManager().fireGameEvent(new DamageEvent(context, minion, damage));
		if (minion.isDead()) {
			destroyMinion((Minion) minion);
		} else if (minion.hasTag(GameTag.ENRAGE_SPELL)){
			handleEnrage(minion);
		}
	}
	
	private void handleEnrage(Entity entity) {
		boolean enraged = entity.getHp() < entity.getMaxHp();
		// enrage state has not changed; do nothing
		if (entity.hasTag(GameTag.ENRAGED) == enraged) {
			return;
		}
		
		if (enraged) {
			entity.setTag(GameTag.ENRAGED);
		} else {
			entity.removeTag(GameTag.ENRAGED);
		}
		
		ISpell enrageSpell = (ISpell) entity.getTag(GameTag.ENRAGE_SPELL);
		enrageSpell.cast(context, entity.getOwner(), entity);
	}

	@Override
	public void destroy(Entity target) {
		
	}
	
	private void destroyMinion(Minion minion) {
		minion.getOwner().getMinions().remove(minion);
		if (minion instanceof IGameEventListener) {
			context.getEventManager().removeGameEventListener((IGameEventListener) minion);
		}
		context.getEventManager().fireGameEvent(new KillEvent(context, minion));
	}

	@Override
	public Player determineBeginner(Player... players) {
		return ThreadLocalRandom.current().nextBoolean() ? players[0] : players[1];
	}

	@Override
	public void drawCard(Player player) {
		CardCollection<Card> deck = player.getDeck();
		if (deck.isEmpty()) {
			Hero hero = player.getHero();
			int fatigue = hero.hasTag(GameTag.FATIGUE) ? hero.getTagValue(GameTag.FATIGUE) : 0;
			hero.setTag(GameTag.FATIGUE, fatigue + 1);
			damage(hero, fatigue);
			logger.debug(player.getName() + "'s deck is empty, taking " + fatigue + " fatigue damage!");
			return;
		}

		CardCollection<Card> hand = player.getHand();
		CardCollection<Card> graveyard = player.getGraveyard();
		Card card = deck.removeFirst();
		if (hand.getCount() < MAX_HAND_CARDS) {
			logger.debug(player.getName() + " draws a card: " + card.getName());
			hand.add(card);
		} else {
			logger.debug(player.getName() + " has too many cards on his hand, card destroyed: " + card.getName());
		}
		graveyard.add(card);
	}

	@Override
	public void endTurn(Player player) {
		player.getHero().setBaseAttack(0);
		logger.debug(player.getName() + " ends his turn.");
		context.getEventManager().fireGameEvent(new TurnEndEvent(context, player));
	}

	@Override
	public void fight(Entity attacker, Entity defender) {
		int attackerDamage = attacker.getAttack();
		int defenderDamage = defender.getAttack();
		damage(defender, attackerDamage);
		damage(attacker, defenderDamage);
		attacker.modifyTag(GameTag.NUMBER_OF_ATTACKS, -1);
		context.getEventManager().fireGameEvent(new PhysicalAttackEvent(context, attacker, defender));
	}

	@Override
	public GameResult getMatchResult(Player player, Player opponent) {
		int ownHp = player.getHero().getHp();
		int opponentHp = opponent.getHero().getHp();
		if (ownHp < 1 && opponentHp < 1) {
			return GameResult.DOUBLE_LOSS;
		} else if (opponentHp < 1) {
			return GameResult.WIN;
		} else if (ownHp < 1) {
			return GameResult.DEFEAT;
		}
		return GameResult.RUNNING;
	}

	@Override
	public List<Entity> getValidTargets(Player player, GameAction action) {
		return targetLogic.getValidTargets(context, player, action);
	}

	@Override
	public void heal(Entity target, int healing) {
		switch (target.getEntityType()) {
		case MINION:
			healMinion((Minion) target, healing);
			break;
		case HERO:
			healHero((Hero) target, healing);
			break;
		default:
			break;
		}
	}

	private void healHero(Hero hero, int healing) {
		int newHp = Math.min(MAX_HERO_HP, hero.getHp() + healing);
		logger.debug(hero.getName() + " is healed for " + healing + ", hp now: " + hero.getHp());
		hero.setHp(newHp);
	}
	
	private void healMinion(Minion minion, int healing) {
		int newHp = Math.min(minion.getMaxHp(), minion.getHp() + healing);
		logger.debug(minion.getName() + " is healed for " + healing + ", hp now: " + minion.getHp() + "/" + minion.getMaxHp());
		minion.setHp(newHp);
		if (minion.hasTag(GameTag.ENRAGE_SPELL)) {
			handleEnrage(minion);
		}
	}

	@Override
	public void init(Player player, boolean begins) {
		logger.debug("Setting player hp to " + MAX_HERO_HP + " for " + player.getName());
		player.getHero().setMaxHp(MAX_HERO_HP);
		player.getHero().setHp(MAX_HERO_HP);

		for (int j = 0; j < STARTER_CARDS; j++) {
			drawCard(player);
		}
		// second player gets additional card + TheCoin
		if (!begins) {
			drawCard(player);
			TheCoin theCoin = new TheCoin();
			logger.debug(player.getName() + " receives " + theCoin.getName());
			player.getHand().add(theCoin);
		}
	}

	@Override
	public void modifyCurrentMana(Player player, int mana) {
		int newMana = Math.min(player.getMana() + mana, MAX_MANA);
		player.setMana(newMana);
	}

	@Override
	public void performGameAction(Player player, GameAction action) {
		if (action.getTargetRequirement() != TargetRequirement.NONE && action.getTarget() == null) {
			List<Entity> validTargets = getValidTargets(player, action);
			Entity target = player.getBehaviour().provideTargetFor(action, validTargets);
			if (target != null) {
				if (!validTargets.contains(target)) {
					throw new IllegalArgumentException("Selected invalid target " + target.getName() + " for action "
							+ action);
				}
			}
			action.setTarget(target);
		}

		action.execute(context, player);
	}

	@Override
	public void playCard(Player player, Card card) {
		modifyCurrentMana(player, -card.getManaCost());
		logger.debug(player.getName() + " plays " + card.getName());
		player.getHand().remove(card);
		player.getGraveyard().add(card);
	}

	// TODO: circular dependency. Very ugly, refactor!
	public void setContext(GameContext context) {
		this.context = context;
	}

	@Override
	public void startTurn(Player player) {
		if (player.getMaxMana() < MAX_MANA) {
			player.setMaxMana(player.getMaxMana() + 1);
		}
		player.setMana(player.getMaxMana());
		player.getHero().getHeroPower().setUsed(false);
		logger.debug(player.getName() + " is at " + player.getMana() + "/" + player.getMaxMana() + " mana");
		drawCard(player);
		for (Minion minion : player.getMinions()) {
			int attacks = minion.hasTag(GameTag.WINDFURY) ? 2 : 1;
			minion.setTag(GameTag.NUMBER_OF_ATTACKS, attacks);
		}
		context.getEventManager().fireGameEvent(new TurnStartEvent(context, player));
	}

	@Override
	public void summon(Player player, Minion minion, Entity nextTo) {
		logger.debug(player.getName() + " summons " + minion.getName());
		
		if (minion.getBattlecry() != null) {
			performGameAction(player, minion.getBattlecry());
		}

		if (nextTo == null) {
			player.getMinions().add(minion);
		} else {
			// TODO: implement summoning next to
			// player.getMinions().addAfter(minion, nextTo);
		}
		minion.setOwner(player);
		if (minion instanceof IGameEventListener) {
			context.getEventManager().registerGameEventListener((IGameEventListener) minion);
		}
		if (minion.hasTag(GameTag.CHARGE)) {
			minion.setTag(GameTag.NUMBER_OF_ATTACKS, minion.hasTag(GameTag.WINDFURY) ? 2 : 1);
		} else {
			minion.setTag(GameTag.NUMBER_OF_ATTACKS, 0);
		}
		context.getEventManager().fireGameEvent(new SummonEvent(context, minion));
	}

	@Override
	public void useHeroPower(Player player, HeroPower power) {
		modifyCurrentMana(player, -power.getManaCost());
		logger.debug(player.getName() + " uses hero power " + power.getName());
		power.setUsed(true);
	}

	@Override
	public void castSpell(Player player, ISpell spell, Entity target) {
		spell.cast(context, player, target);
	}

}
