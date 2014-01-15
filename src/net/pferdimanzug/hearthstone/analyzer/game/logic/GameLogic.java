package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
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
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.IdFactory;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
	private final IdFactory idFactory = new IdFactory();
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
	public void castSpell(Player player, Spell spell) {
		spell.cast(context, player, targetLogic.resolveTargetKey(context, player, spell.getTarget()));
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
		int effectiveHp = hero.getHp() + hero.getArmor();
		hero.modifyArmor(-damage);
		int newHp = Math.min(hero.getHp(), effectiveHp - damage);
		hero.setHp(newHp);
		logger.debug(hero.getName() + " receives " + damage + " damage, hp now: " + hero.getHp() + "("
				+ hero.getArmor() + ")");
	}
	
	private void damageMinion(Entity minion, int damage) {
		if (minion.hasTag(GameTag.DIVINE_SHIELD)) {
			minion.removeTag(GameTag.DIVINE_SHIELD);
			logger.debug("{}'s DIVINE SHIELD absorbs the damage", minion);
			return;
		}
		logger.debug("{} is damaged for {}", minion, damage);
		minion.setHp(minion.getHp() - damage);
		context.getEventManager().fireGameEvent(new DamageEvent(context, minion, damage));
		if (minion.hasTag(GameTag.ENRAGE_SPELL)){
			handleEnrage(minion);
		}
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
		logger.debug("{} is destroyed", minion);
		Player owner = context.getPlayer(minion.getOwner());
		owner.getMinions().remove(minion);
		for (SpellTrigger spellTrigger : minion.getSpellTriggers()) {
			context.getEventManager().removeGameEventListener(spellTrigger);
		}
		context.getEventManager().fireGameEvent(new KillEvent(context, minion));
	}
	
	private void destroyWeapon(Weapon weapon) {
		Player owner = context.getPlayer(weapon.getOwner());
		owner.getHero().setWeapon(null);
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
		player.getHero().setTag(GameTag.ATTACK_BONUS, 0);
		player.getHero().removeTag(GameTag.COMBO);
		logger.debug("{} ends his turn.", player.getName());
		int playerIndex = context.getPlayerIndex(player);
		context.getEventManager().fireGameEvent(new TurnEndEvent(context, playerIndex));
	}

	@Override
	public void fight(Entity attacker, Entity defender) {
		logger.debug("{} attacks {}", attacker, defender);
		int attackerDamage = attacker.getAttack();
		int defenderDamage = defender.getAttack();
		damage(defender, attackerDamage);
		// heroes do not retaliate when attacked
		if (defender.getEntityType() != EntityType.HERO) {
			damage(attacker, defenderDamage);
		}
		
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
	public List<GameAction> getValidActions(Player player) {
		return actionLogic.getValidActions(context, player);
	}

	@Override
	public List<Entity> getValidTargets(Player player, GameAction action) {
		return targetLogic.getValidTargets(context, player, action);
	}

	private void handleEnrage(Entity entity) {
		boolean enraged = entity.getHp() < entity.getMaxHp();
		// enrage state has not changed; do nothing
		if (entity.hasTag(GameTag.ENRAGED) == enraged) {
			return;
		}
		
		if (enraged) {
			logger.debug("{} is now enraged", entity);
			entity.setTag(GameTag.ENRAGED);
		} else {
			logger.debug("{} is no longer enraged", entity);
			entity.removeTag(GameTag.ENRAGED);
		}
		
		Spell enrageSpell = (Spell) entity.getTag(GameTag.ENRAGE_SPELL);
		Player owner = context.getPlayer(entity.getOwner());
		enrageSpell.cast(context, owner, toList(entity));
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
			logger.debug(hero + " is healed for " + healing + ", hp now: " + hero.getHp());
		}
		
		hero.setHp(newHp);
	}

	private void healMinion(Entity minion, int healing) {
		int newHp = Math.min(minion.getMaxHp(), minion.getHp() + healing);
		if (logger.isDebugEnabled()) {
			logger.debug(minion + " is healed for " + healing + ", hp now: " + newHp + "/" + minion.getMaxHp());	
		}
		
		minion.setHp(newHp);
		if (minion.hasTag(GameTag.ENRAGE_SPELL)) {
			handleEnrage(minion);
		}
	}

	@Override
	public void init(Player player, boolean begins) {
		player.getHero().setId(idFactory.generateId());
		player.getHero().setOwner(context.getPlayerIndex(player));
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
			logger.debug("{} receives {}", player.getName(), theCoin);
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
			action.setTargetKey(TargetKey.pointTo(action.getSource()));
		}
		if (action.getTargetRequirement() != TargetSelection.NONE && action.getTargetKey() == null) {
			List<Entity> validTargets = getValidTargets(player, action);
			if (validTargets.isEmpty() && action.getActionType() == ActionType.MINION_ABILITY) {
				return;
			}
			Entity target = player.getBehaviour().provideTargetFor(player, action, validTargets);
			if (target != null) {
				if (!validTargets.contains(target)) {
					throw new IllegalArgumentException("Selected invalid target " + target.getName() + " for action "
							+ action);
				}
			}
			action.setTargetKey(TargetKey.pointTo(target));
		}

		action.execute(context, player);
		checkForDeadEntities();
	}
	
	private void checkForDeadEntities() {
		for (Player player : context.getPlayers()) {
			for (Minion minion : new ArrayList<Minion>(player.getMinions())) {
				if (minion.isDead()) {
					destroyMinion(minion);	
				}
			}
		}
		
	}

	@Override
	public void playCard(Player player, Card card) {
		modifyCurrentMana(player, -card.getManaCost());
		logger.debug("{} plays {}", player.getName(), card);
		//logger.debug("{} is now at {} mana", player.getName(), player.getMana() + "/" + player.getMaxMana());
		player.getHand().remove(card);
		player.getGraveyard().add(card);
		player.getHero().modifyTag(GameTag.COMBO, +1);
	}
	
	@Override
	public void receiveCard(Player player, Card card) {
		CardCollection<Card> hand = player.getHand();
		CardCollection<Card> graveyard = player.getGraveyard();
		if (hand.getCount() < MAX_HAND_CARDS) {
			logger.debug("{} receives card {}", player.getName(), card);
			hand.add(card);
		} else {
			logger.debug("{} has too many cards on his hand, card destroyed: {}", player.getName(), card);
			graveyard.add(card);
		}
	}

	private void refreshAttacksPerRound(Entity entity) {
		int attacks = 1;
		if (entity.hasTag(GameTag.FROZEN)) {
			attacks = 0;
		} else if ( entity.hasTag(GameTag.WINDFURY)) {
			attacks = 2;
		}
		entity.setTag(GameTag.NUMBER_OF_ATTACKS, attacks);
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
		logger.debug("{} starts his turn with {} mana", player.getName(), player.getMana() + "/" + player.getMaxMana());
		player.getHero().getHeroPower().setUsed(false);
		refreshAttacksPerRound(player.getHero());
		drawCard(player);
		for (Entity minion : player.getMinions()) {
			refreshAttacksPerRound(minion);
			minion.removeTag(GameTag.SUMMONING_SICKNESS);
		}
		int playerIndex = context.getPlayerIndex(player);
		context.getEventManager().fireGameEvent(new TurnStartEvent(context, playerIndex));
	}

	@Override
	public void summon(Player player, Minion minion, Entity nextTo) {
		minion.setId(idFactory.generateId());
		logger.debug("{} summons {}", player.getName(), minion);
		refreshAttacksPerRound(minion);
		minion.setTag(GameTag.SUMMONING_SICKNESS);
		
		context.getPendingEntities().add(minion);
		if (minion.getBattlecry() != null) {
			GameAction battlecry = minion.getBattlecry();
			battlecry.setSource(minion);
			performGameAction(player, battlecry);
		}
		context.getPendingEntities().remove(minion);

		if (nextTo == null) {
			player.getMinions().add(minion);
		} else {
			// TODO: implement summoning next to
			// player.getMinions().addAfter(minion, nextTo);
		}
		minion.setOwner(context.getPlayerIndex(player));
		for (SpellTrigger spellTrigger : minion.getSpellTriggers()) {
			spellTrigger.setHost(minion);
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
		logger.debug("{} uses {}", player.getName(), power);
		power.setUsed(true);
	}

	@Override
	public void equipWeapon(Player player, Hero hero, Weapon weapon) {
		weapon.setId(idFactory.generateId());
		logger.debug("{} equips weapon {}", player.getHero(), weapon);
		player.getHero().setWeapon(weapon);
		for (SpellTrigger spellTrigger : weapon.getSpellTriggers()) {
			context.getEventManager().registerGameEventListener(spellTrigger);
		}
	}
	
	private List<Entity> toList(Entity entity) {
		List<Entity> list = new ArrayList<Entity>(1);
		list.add(entity);
		return list;
	}

}
