package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnStartEvent;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

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
	private final ActionLogic actionLogic = new ActionLogic();
	private GameContext context;

	@Override
	public boolean canPlayCard(Player player, Card card) {
		if (player.getMana() < card.getManaCost()) {
			return false;
		}
		if (card.getCardType() == CardType.HERO_POWER) {
			HeroPower power = (HeroPower) card;
			if (power.hasBeenUsed()) {
				return false;
			}
		} else if (card.getCardType() == CardType.MINION) {
			return player.getMinions().size() < MAX_MINIONS;
		} 
		
		if (card instanceof SpellCard) {
			SpellCard spellCard = (SpellCard) card;
			return spellCard.canBeCast(context, player);
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
		logger.debug("Minion [{}] is damaged for {}", minion.getName(), damage);
		minion.setHp(minion.getHp() - damage);
		context.getEventManager().fireGameEvent(new DamageEvent(context, minion, damage));
		if (minion.isDead()) {
			destroyMinion((Entity) minion);
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
			logger.debug("[{}] is now enraged", entity.getName());
			entity.setTag(GameTag.ENRAGED);
		} else {
			logger.debug("[{}] is no longer enraged", entity.getName());
			entity.removeTag(GameTag.ENRAGED);
		}
		
		ISpell enrageSpell = (ISpell) entity.getTag(GameTag.ENRAGE_SPELL);
		enrageSpell.cast(context, entity.getOwner(), entity);
	}

	@Override
	public void destroy(Entity target) {
		switch (target.getEntityType()) {
		case HERO:
			logger.error("Destroying hero not implemented!");
			break;
		case MINION:
			destroyMinion(target);
			break;
		case WEAPON:
			destroyWeapon((Weapon) target);
			break;
		case ANY:
		default:
			break;
		
		}
	}
	
	private void destroyMinion(Entity minion) {
		logger.debug("Minion [{}] is destroyed", minion.getName());
		minion.getOwner().getMinions().remove(minion);
		for (SpellTrigger spellTrigger : minion.getSpellTriggers()) {
			context.getEventManager().removeGameEventListener(spellTrigger);
		}
		context.getEventManager().fireGameEvent(new KillEvent(context, minion));
	}
	
	private void destroyWeapon(Weapon weapon) {
		weapon.getOwner().getHero().setWeapon(null);
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
			logger.debug("{}'s deck is empty, taking {} fatigue damage!", player.getName(), fatigue);
			return;
		}

		Card card = deck.removeFirst();
		receiveCard(player, card);
	}

	@Override
	public void endTurn(Player player) {
		player.getHero().setBaseAttack(0);
		player.getHero().removeTag(GameTag.COMBO);
		logger.debug("{} ends his turn.", player.getName());
		context.getEventManager().fireGameEvent(new TurnEndEvent(context, player));
	}

	@Override
	public void fight(Entity attacker, Entity defender) {
		logger.debug("[{}] attacks [{}]", attacker.getName(), defender.getName());
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
			healMinion((Entity) target, healing);
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
		if (logger.isDebugEnabled()) {
			logger.debug("[" + hero.getName() + "] is healed for " + healing + ", hp now: " + hero.getHp());
		}
		
		hero.setHp(newHp);
	}
	
	private void healMinion(Entity minion, int healing) {
		int newHp = Math.min(minion.getMaxHp(), minion.getHp() + healing);
		if (logger.isDebugEnabled()) {
			logger.debug("[" + minion.getName() + "] is healed for " + healing + ", hp now: " + minion.getHp() + "/" + minion.getMaxHp());	
		}
		
		minion.setHp(newHp);
		if (minion.hasTag(GameTag.ENRAGE_SPELL)) {
			handleEnrage(minion);
		}
	}

	@Override
	public void init(Player player, boolean begins) {
		player.getHero().setMaxHp(MAX_HERO_HP);
		player.getHero().setHp(MAX_HERO_HP);
		
		logger.debug("Setting hero hp to {} for {}", player.getHero().getHp(), player.getName());

		player.getDeck().shuffle();
		
		for (int j = 0; j < STARTER_CARDS; j++) {
			drawCard(player);
		}
		// second player gets additional card + TheCoin
		if (!begins) {
			drawCard(player);
			TheCoin theCoin = new TheCoin();
			logger.debug("{} receives [{}]", player.getName(), theCoin.getName());
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
		if (action.getTargetRequirement() == TargetSelection.SELF) {
			action.setTarget(action.getSource());
		}
		if (action.getTargetRequirement() != TargetSelection.NONE && action.getTarget() == null) {
			List<Entity> validTargets = getValidTargets(player, action);
			if (validTargets.isEmpty() && action.getActionType() == ActionType.MINION_ABILITY) {
				return;
			}
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
		logger.debug("{} plays [{}]", player.getName(), card.getName());
		player.getHand().remove(card);
		player.getGraveyard().add(card);
		player.getHero().setTag(GameTag.COMBO);
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
		for (Entity minion : player.getMinions()) {
			int attacks = 1;
			if (minion.hasTag(GameTag.FROZEN)) {
				attacks = 0;
			} else if ( minion.hasTag(GameTag.WINDFURY)) {
				attacks = 2;
			}
			minion.setTag(GameTag.NUMBER_OF_ATTACKS, attacks);
		}
		context.getEventManager().fireGameEvent(new TurnStartEvent(context, player));
	}

	@Override
	public void summon(Player player, Minion minion, Entity nextTo) {
		logger.debug("{} summons [{}]", player.getName(), minion.getName());
		
		if (minion.getBattlecry() != null) {
			GameAction battlecry = minion.getBattlecry();
			battlecry.setSource(minion);
			performGameAction(player, battlecry);
		}

		if (nextTo == null) {
			player.getMinions().add(minion);
		} else {
			// TODO: implement summoning next to
			// player.getMinions().addAfter(minion, nextTo);
		}
		minion.setOwner(player);
		for (SpellTrigger spellTrigger : minion.getSpellTriggers()) {
			context.getEventManager().registerGameEventListener(spellTrigger);
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
		logger.debug("{} uses hero power [{}]", player.getName(), power.getName());
		power.setUsed(true);
	}

	@Override
	public void castSpell(Player player, ISpell spell, Entity target) {
		spell.cast(context, player, target);
	}

	@Override
	public void receiveCard(Player player, Card card) {
		CardCollection<Card> hand = player.getHand();
		CardCollection<Card> graveyard = player.getGraveyard();
		if (hand.getCount() < MAX_HAND_CARDS) {
			logger.debug("{} receives card [{}]", player.getName(), card.getName());
			hand.add(card);
		} else {
			logger.debug("{} has too many cards on his hand, card destroyed: [{}]", player.getName(), card.getName());
			graveyard.add(card);
		}
	}

	@Override
	public List<GameAction> getValidActions(Player player) {
		return actionLogic.getValidActions(context, player);
	}
	

}
