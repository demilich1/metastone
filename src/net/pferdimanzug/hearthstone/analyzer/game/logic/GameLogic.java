package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.events.BoardChangedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.CardPlayedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.HealEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.OverloadEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.ReceiveCardEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SecretPlayedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SecretRevealedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SilenceEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SpellCastedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TargetAcquisitionEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnStartEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.WeaponDestroyedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellFactory;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellSource;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.IdFactory;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.utils.MathUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLogic implements Cloneable {

	public static Logger logger = LoggerFactory.getLogger(GameLogic.class);

	public static final int MAX_PLAYERS = 2;
	public static final int MAX_MINIONS = 7;
	public static final int MAX_HAND_CARDS = 10;
	public static final int MAX_HERO_HP = 30;
	public static final int STARTER_CARDS = 3;
	public static final int MAX_MANA = 10;
	public static final int MAX_SECRETS = 5;
	public static final int DECK_SIZE = 30;

	private final TargetLogic targetLogic = new TargetLogic();
	private final ActionLogic actionLogic = new ActionLogic();
	private final SpellFactory spellFactory = new SpellFactory();
	private final IdFactory idFactory;
	private GameContext context;
	private boolean loggingEnabled = true;

	public GameLogic() {
		idFactory = new IdFactory();
	}

	private GameLogic(IdFactory idFactory) {
		this.idFactory = idFactory;
	}

	public void addGameEventListener(Player player, IGameEventListener gameEventListener, Entity target) {
		gameEventListener.setHost(target);
		gameEventListener.setOwner(player.getId());
		gameEventListener.reset();
		gameEventListener.onAdd(context);
		context.addTrigger(gameEventListener);
		log("New spelltrigger was added for {} on {}", player.getName(), target);
	}

	public void addManaModifier(Player player, CardCostModifier cardCostModifier, Entity target) {
		context.getCardCostModifiers().add(cardCostModifier);
		addGameEventListener(player, cardCostModifier, target);
	}

	public void afterCardPlayed(int playerId, CardReference cardReference) {
		Player player = context.getPlayer(playerId);
		player.getHero().modifyTag(GameTag.COMBO, +1);
		Card card = context.resolveCardReference(cardReference);
		card.setLocation(CardLocation.VOID);
	}

	public int applyAmplify(Player player, int baseValue) {
		int amplify = 1 + getTotalTagValue(player, GameTag.SPELL_AMPLIFY_MULTIPLIER);
		return baseValue * amplify;
	}

	public int applySpellpower(Player player, int baseValue) {
		int spellpower = getTotalTagValue(player, GameTag.SPELL_POWER);
		return baseValue + spellpower;
	}

	private void assignCardIds(CardCollection cardCollection) {
		for (Card card : cardCollection) {
			card.setId(idFactory.generateId());
			card.setLocation(CardLocation.DECK);
		}
	}

	public boolean canPlayCard(int playerId, CardReference cardReference) {
		Player player = context.getPlayer(playerId);
		Card card = context.resolveCardReference(cardReference);
		int manaCost = getModifiedManaCost(player, card);
		if (player.getMana() < manaCost) {
			return false;
		}
		if (card.getCardType() == CardType.HERO_POWER) {
			HeroPower power = (HeroPower) card;
			if (power.hasBeenUsed()) {
				return false;
			}
		} else if (card.getCardType() == CardType.MINION) {
			return canSummonMoreMinions(player);
		}

		if (card instanceof SpellCard) {
			SpellCard spellCard = (SpellCard) card;
			return spellCard.canBeCast(context, player);
		}
		return true;
	}

	public boolean canPlaySecret(Player player, SecretCard card) {
		return player.getSecrets().size() < MAX_SECRETS && !player.getSecrets().contains(card.getTypeId());
	}

	public boolean canSummonMoreMinions(Player player) {
		int minionsInPlay = 0;
		for (Minion minion : player.getMinions()) {
			if (minion.isDead()) {
				continue;
			}
			minionsInPlay++;
		}
		return minionsInPlay < MAX_MINIONS;
	}

	public void castSpell(int playerId, SpellDesc spellDesc) {
		Player player = context.getPlayer(playerId);
		Actor source = null;
		if (spellDesc.getSourceEntity() != null) {
			source = (Actor) context.resolveSingleTarget(spellDesc.getSourceEntity());
		}
		SpellCard sourceCard = null;
		List<Entity> targets = targetLogic.resolveTargetKey(context, player, source, spellDesc.getTarget());
		// target can only be changed when there is one target
		// note: this code block is basically exclusively for the SpellBender
		// Secret, but it can easily be expanded if targets of area of effect
		// spell should be changeable as well
		if (spellDesc.getSource() == SpellSource.SPELL_CARD && !spellDesc.hasPredefinedTarget() && targets != null && targets.size() == 1) {
			Card pendingCard = (Card) context.getEnvironment().get(Environment.PENDING_CARD);
			if (pendingCard instanceof SpellCard) {
				sourceCard = (SpellCard) pendingCard;
			}

			if (sourceCard != null && sourceCard.getTargetRequirement() != TargetSelection.NONE) {
				context.fireGameEvent(new TargetAcquisitionEvent(context, ActionType.SPELL, targets.get(0)), TriggerLayer.SECRET);
				Entity targetOverride = (Entity) context.getEnvironment().get(Environment.TARGET_OVERRIDE);
				if (targetOverride != null && targetOverride.getId() != IdFactory.UNASSIGNED) {
					targets.remove(0);
					targets.add(targetOverride);
					spellDesc.setTarget(targets.get(0).getReference());
					log("Target for spell {} has been changed! New target {}", sourceCard, targets.get(0));
				}
			}

		}
		try {
			Spell spell = spellFactory.getSpell(spellDesc);
			spell.cast(context, player, spellDesc, targets);
		} catch (Exception e) {
			Card pendingCard = (Card) context.getEnvironment().get(Environment.PENDING_CARD);
			if (pendingCard instanceof SpellCard) {
				sourceCard = (SpellCard) pendingCard;
			}
			logger.error("Error while playing card: " + sourceCard);
			logger.error("Error while casting spell: " + spellDesc);
			e.printStackTrace();
		}

		if (sourceCard != null) {
			context.getEnvironment().remove(Environment.TARGET_OVERRIDE);
		}
	}

	public void changeHero(Player player, Hero hero) {
		hero.setId(player.getHero().getId());

		HashMap<GameTag, Object> tagsToCopy = player.getHero().getTagsCopy();
		for (Map.Entry<GameTag, Object> entry : tagsToCopy.entrySet()) {
			hero.setTag(entry.getKey(), entry.getValue());
		}

		log("{}'s hero has been changed to {}", player.getName(), hero);
		hero.setOwner(player.getId());
		player.setHero(hero);
		refreshAttacksPerRound(hero);
	}

	public void checkForDeadEntities() {
		for (Player player : context.getPlayers()) {
			List<Minion> minionList = new ArrayList<Minion>(player.getMinions());
			// sort by id, which has the effect of destroying minions in FIFO
			// order
			// which is relevant for deathrattles
			Collections.sort(minionList, (m1, m2) -> Integer.compare(m1.getId(), m2.getId()));
			for (Minion minion : minionList) {
				// need to check if minion is still on the players minion
				// list...
				// it may have been removed by another minion dying before (i.e.
				// Anub'ar Ambusher)
				if (minion.isDead() && player.getMinions().contains(minion)) {
					destroy(minion);
				}
			}
		}
	}

	@Override
	public GameLogic clone() {
		return new GameLogic(idFactory.clone());
	}

	public boolean damage(Player player, Actor target, int baseDamage, SpellSource spellSource) {
		int damage = baseDamage;
		if (spellSource == SpellSource.SPELL_CARD) {
			damage = applySpellpower(player, baseDamage);
		}
		if (spellSource == SpellSource.SPELL_CARD || spellSource == SpellSource.HERO_POWER) {
			damage = applyAmplify(player, damage);
		}
		boolean success = false;
		switch (target.getEntityType()) {
		case MINION:
			success = damageMinion(player, (Actor) target, damage);
			break;
		case HERO:
			success = damageHero((Hero) target, damage);
			break;
		default:
			break;
		}

		if (success) {
			DamageEvent damageEvent = new DamageEvent(context, target, damage);
			context.fireGameEvent(damageEvent, TriggerLayer.SECRET);
			context.fireGameEvent(damageEvent);
			player.getStatistics().damageDealt(damage);
		}

		return success;
	}

	private boolean damageHero(Hero hero, int damage) {
		if (hero.hasStatus(GameTag.IMMUNE)) {
			log("{} is IMMUNE and does not take damage", hero);
			return false;
		}
		int effectiveHp = hero.getHp() + hero.getArmor();
		hero.modifyArmor(-damage);
		int newHp = Math.min(hero.getHp(), effectiveHp - damage);
		hero.setHp(newHp);
		log(hero.getName() + " receives " + damage + " damage, hp now: " + hero.getHp() + "(" + hero.getArmor() + ")");
		return true;
	}

	private boolean damageMinion(Player player, Actor minion, int damage) {
		if (minion.hasStatus(GameTag.DIVINE_SHIELD)) {
			minion.removeTag(GameTag.DIVINE_SHIELD);
			log("{}'s DIVINE SHIELD absorbs the damage", minion);
			return false;
		}
		if (minion.hasStatus(GameTag.IMMUNE)) {
			log("{} is IMMUNE and does not take damage", minion);
			return false;
		}
		if (damage >= minion.getHp() && player.getHero().hasStatus(GameTag.CANNOT_REDUCE_HP_BELOW_1)) {
			damage = minion.getHp() - 1;
		}

		log("{} is damaged for {}", minion, damage);
		minion.setHp(minion.getHp() - damage);
		if (minion.hasTag(GameTag.ENRAGE_SPELL)) {
			handleEnrage(minion);
		}
		return true;
	}

	public void markAsDestroyed(Actor target) {
		target.setTag(GameTag.DEAD);
	}

	public void destroy(Actor target) {
		removeSpelltriggers(target);

		switch (target.getEntityType()) {
		case HERO:
			log("Hero {} has been destroyed.", target.getName());
			target.setHp(0);
			break;
		case MINION:
			destroyMinion((Minion) target);
			break;
		case WEAPON:
			destroyWeapon((Weapon) target);
			break;
		case ANY:
		default:
			logger.error("Trying to destroy unknown entity type {}", target.getEntityType());
			break;
		}
	}

	private void destroyMinion(Minion minion) {
		log("{} is destroyed", minion);
		Player owner = context.getPlayer(minion.getOwner());
		context.getEnvironment().put(Environment.KILLED_MINION, minion);
		KillEvent killEvent = new KillEvent(context, minion);
		context.fireGameEvent(killEvent, TriggerLayer.SECRET);
		context.fireGameEvent(killEvent);
		context.getEnvironment().remove(Environment.KILLED_MINION);

		minion.setTag(GameTag.DEAD);
		minion.setTag(GameTag.DIED_ON_TURN, context.getTurn());

		int tokenIndex = owner.getMinions().indexOf(minion);
		context.getEnvironment().put(Environment.TOKEN_INDEX, tokenIndex);
		owner.getMinions().remove(tokenIndex);
		owner.getGraveyard().add(minion);

		resolveDeathrattles(owner, minion);
		context.getEnvironment().remove(Environment.TOKEN_INDEX);

		context.fireGameEvent(new BoardChangedEvent(context));
	}

	private void destroyWeapon(Weapon weapon) {
		Player owner = context.getPlayer(weapon.getOwner());
		resolveDeathrattles(owner, weapon);
		owner.getHero().setWeapon(null);
		context.fireGameEvent(new WeaponDestroyedEvent(context, weapon));
	}

	public int determineBeginner(int... playerIds) {
		return ThreadLocalRandom.current().nextBoolean() ? playerIds[0] : playerIds[1];
	}

	public Card drawCard(int playerId) {
		Player player = context.getPlayer(playerId);
		CardCollection deck = player.getDeck();
		if (deck.isEmpty()) {
			Hero hero = player.getHero();
			int fatigue = hero.hasStatus(GameTag.FATIGUE) ? hero.getTagValue(GameTag.FATIGUE) : 0;
			hero.setTag(GameTag.FATIGUE, fatigue + 1);
			damage(player, hero, fatigue, SpellSource.FATIGUE);
			log("{}'s deck is empty, taking {} fatigue damage!", player.getName(), fatigue);
			player.getStatistics().fatigueDamage(fatigue);
			return null;
		}

		player.getStatistics().cardDrawn();
		Card card = deck.getRandom();
		deck.remove(card);
		receiveCard(playerId, card);
		return card;
	}

	public void endTurn(int playerId) {
		Player player = context.getPlayer(playerId);
		player.getHero().removeTag(GameTag.COMBO);
		player.getHero().activateWeapon(false);
		log("{} ends his turn.", player.getName());
		context.fireGameEvent(new TurnEndEvent(context, player.getId()));
		for (Iterator<CardCostModifier> iterator = context.getCardCostModifiers().iterator(); iterator.hasNext();) {
			CardCostModifier cardCostModifier = iterator.next();
			if (cardCostModifier.isExpired()) {
				iterator.remove();
			}
		}
		checkForDeadEntities();
	}

	public void equipWeapon(int playerId, Weapon weapon) {
		Player player = context.getPlayer(playerId);
		player.getStatistics().equipWeapon();
		weapon.setId(idFactory.generateId());
		context.getEnvironment().put(Environment.SUMMONED_WEAPON, weapon);
		Weapon currentWeapon = player.getHero().getWeapon();
		if (currentWeapon != null) {
			log("{} discards currently equipped weapon {}", player.getHero(), currentWeapon);
			destroy(currentWeapon);
		}
		if (weapon.getBattlecry() != null) {
			resolveBattlecry(playerId, weapon);
		}
		context.getEnvironment().remove(Environment.SUMMONED_WEAPON);

		log("{} equips weapon {}", player.getHero(), weapon);
		player.getHero().setWeapon(weapon);
		weapon.setActive(context.getActivePlayerId() == playerId);
		if (weapon.hasSpellTrigger()) {
			SpellTrigger spellTrigger = weapon.getSpellTrigger();
			addGameEventListener(player, spellTrigger, weapon);
		}
	}

	public void fight(Player player, Actor attacker, Actor defender) {
		log("{} attacks {}", attacker, defender);

		context.getEnvironment().put(Environment.ATTACKER, attacker);
		TargetAcquisitionEvent targetAcquisitionEvent = new TargetAcquisitionEvent(context, ActionType.PHYSICAL_ATTACK, defender);
		context.fireGameEvent(targetAcquisitionEvent, TriggerLayer.SECRET);
		context.fireGameEvent(targetAcquisitionEvent);
		Actor target = defender;
		if (context.getEnvironment().containsKey(Environment.TARGET_OVERRIDE)) {
			target = (Actor) context.getEnvironment().get(Environment.TARGET_OVERRIDE);
		}
		context.getEnvironment().remove(Environment.TARGET_OVERRIDE);

		if (target != defender) {
			log("Target of attack was changed! New Target: {}", target);
		}

		if (attacker.hasStatus(GameTag.IMMUNE_WHILE_ATTACKING)) {
			attacker.setTag(GameTag.IMMUNE);
		}
		attacker.removeTag(GameTag.STEALTHED);

		int attackerDamage = attacker.getAttack();
		int defenderDamage = target.getAttack();
		context.fireGameEvent(new PhysicalAttackEvent(context, attacker, target, attackerDamage), TriggerLayer.SECRET);
		// secret may have killed attacker
		if (attacker.isDead()) {
			return;
		}

		Player owningPlayer = context.getPlayer(target.getOwner());
		boolean damaged = damage(owningPlayer, target, attackerDamage, SpellSource.PHYSICAL_ATTACK);
		if (defenderDamage > 0) {
			damage(player, attacker, defenderDamage, SpellSource.PHYSICAL_ATTACK);
		}
		if (attacker.hasStatus(GameTag.IMMUNE_WHILE_ATTACKING)) {
			attacker.removeTag(GameTag.IMMUNE);
		}

		if (attacker.getEntityType() == EntityType.HERO) {
			Hero hero = (Hero) attacker;
			Weapon weapon = hero.getWeapon();
			// TODO: this is not nice, maybe move this functionality to the
			// Weapon class?
			if (weapon != null && weapon.isActive()) {
				if (weapon.hasStatus(GameTag.CONSUME_DAMAGE_INSTEAD_OF_DURABILITY_ON_MINIONS)
						&& defender.getEntityType() == EntityType.MINION) {
					modifyDurability(hero.getWeapon(), GameTag.WEAPON_DAMAGE, -1);
				} else {
					modifyDurability(hero.getWeapon(), GameTag.DURABILITY, -1);
				}

			}
		}

		attacker.modifyTag(GameTag.NUMBER_OF_ATTACKS, -1);
		context.fireGameEvent(new PhysicalAttackEvent(context, attacker, target, damaged ? attackerDamage : 0));
		context.getEnvironment().remove(Environment.ATTACKER);
	}

	public void gainArmor(Player player, int armor) {
		logger.debug("{} gains {} armor", player.getHero(), armor);
		player.getHero().modifyArmor(armor);
		player.getStatistics().armorGained(armor);
	}

	public MatchResult getMatchResult(Player player, Player opponent) {
		int ownHp = player.getHero().getHp();
		int opponentHp = opponent.getHero().getHp();
		if (ownHp < 1 && opponentHp < 1) {
			return MatchResult.DOUBLE_LOSS;
		} else if (opponentHp < 1 || ownHp < 1) {
			return MatchResult.WON;
		}
		return MatchResult.RUNNING;
	}

	public int getModifiedManaCost(Player player, Card card) {
		int manaCost = card.getManaCost(context, player);
		int minValue = 0;
		for (CardCostModifier costModifier : context.getCardCostModifiers()) {
			manaCost += costModifier.process(card);
			if (costModifier.getMinValue() > minValue) {
				minValue = costModifier.getMinValue();
			}
		}
		manaCost = MathUtils.clamp(manaCost, minValue, Integer.MAX_VALUE);
		return manaCost;
	}

	public int getTotalTagValue(GameTag tag) {
		int total = 0;
		for (Player player : context.getPlayers()) {
			total += getTotalTagValue(player, tag);
		}
		return total;
	}

	public int getTotalTagValue(Player player, GameTag tag) {
		int total = player.getHero().getTagValue(tag);
		for (Entity minion : player.getMinions()) {
			if (!minion.hasTag(tag)) {
				continue;
			}

			total += minion.getTagValue(tag);
		}
		return total;
	}

	public List<GameAction> getValidActions(int playerId) {
		Player player = context.getPlayer(playerId);
		return actionLogic.getValidActions(context, player);
	}

	public List<Entity> getValidTargets(int playerId, GameAction action) {
		Player player = context.getPlayer(playerId);
		return targetLogic.getValidTargets(context, player, action);
	}

	public Player getWinner(Player player, Player opponent) {
		int ownHp = player.getHero().getHp();
		int opponentHp = opponent.getHero().getHp();
		if (ownHp < 1 && opponentHp < 1) {
			return null;
		} else if (opponentHp < 1) {
			return player;
		} else if (ownHp < 1) {
			return opponent;
		}
		return null;
	}

	private void handleEnrage(Actor entity) {
		boolean enraged = entity.getHp() < entity.getMaxHp();
		// enrage state has not changed; do nothing
		if (entity.hasStatus(GameTag.ENRAGED) == enraged) {
			return;
		}

		if (enraged) {
			log("{} is now enraged", entity);
			entity.setTag(GameTag.ENRAGED);
		} else {
			log("{} is no longer enraged", entity);
			entity.removeTag(GameTag.ENRAGED);
		}

		SpellDesc enrageSpell = (SpellDesc) entity.getTag(GameTag.ENRAGE_SPELL);
		enrageSpell.setTarget(entity.getReference());
		Player owner = context.getPlayer(entity.getOwner());
		castSpell(owner.getId(), enrageSpell);
	}

	public boolean hasTag(Player player, GameTag tag) {
		if (player.getHero().hasStatus(tag)) {
			return true;
		}
		for (Entity minion : player.getMinions()) {
			if (minion.hasStatus(tag)) {
				return true;
			}
		}

		return false;
	}

	public void heal(Player player, Actor target, int healing, SpellSource spellSource) {
		if (hasTag(player, GameTag.INVERT_HEALING)) {
			log("All healing inverted, deal damage instead!");
			damage(player, target, healing, spellSource);
			return;
		}
		boolean success = false;
		switch (target.getEntityType()) {
		case MINION:
			success = healMinion((Actor) target, healing);
			break;
		case HERO:
			success = healHero((Hero) target, healing);
			break;
		default:
			break;
		}

		if (success) {
			HealEvent healEvent = new HealEvent(context, target, healing);
			context.fireGameEvent(healEvent);
			player.getStatistics().heal(healing);
		}
	}

	private boolean healHero(Hero hero, int healing) {
		int newHp = Math.min(MAX_HERO_HP, hero.getHp() + healing);
		int oldHp = hero.getHp();
		if (logger.isDebugEnabled()) {
			log(hero + " is healed for " + healing + ", hp now: " + hero.getHp());
		}

		hero.setHp(newHp);
		return newHp != oldHp;
	}

	private boolean healMinion(Actor minion, int healing) {
		int newHp = Math.min(minion.getMaxHp(), minion.getHp() + healing);
		int oldHp = minion.getHp();
		if (logger.isDebugEnabled()) {
			log(minion + " is healed for " + healing + ", hp now: " + newHp + "/" + minion.getMaxHp());
		}

		minion.setHp(newHp);
		if (minion.hasTag(GameTag.ENRAGE_SPELL)) {
			handleEnrage(minion);
		}
		return newHp != oldHp;
	}

	public void init(int playerId, boolean begins) {
		Player player = context.getPlayer(playerId);
		player.getHero().setId(idFactory.generateId());
		player.getHero().setOwner(player.getId());
		player.getHero().setMaxHp(MAX_HERO_HP);
		player.getHero().setHp(MAX_HERO_HP);

		assignCardIds(player.getDeck());
		assignCardIds(player.getHand());

		log("Setting hero hp to {} for {}", player.getHero().getHp(), player.getName());

		player.getDeck().shuffle();

		mulligan(player, begins);
	}

	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	private void log(String message) {
		if (isLoggingEnabled() && logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	private void log(String message, Object param1) {
		if (isLoggingEnabled() && logger.isDebugEnabled()) {
			logger.debug(message, param1);
		}
	}

	private void log(String message, Object param1, Object param2) {
		if (isLoggingEnabled() && logger.isDebugEnabled()) {
			logger.debug(message, param1, param2);
		}
	}

	public void mindControl(Player player, Minion minion) {
		log("{} mind controls {}", player.getName(), minion);
		Player opponent = context.getOpponent(player);
		if (!opponent.getMinions().contains(minion)) {
			logger.warn("Minion {} cannot be mind-controlled, because opponent does not own it.", minion);
		}
		context.getOpponent(player).getMinions().remove(minion);
		player.getMinions().add(minion);
		// TODO: we need to change the owner of all spelltriggers as well
		minion.setOwner(player.getId());
		List<IGameEventListener> triggers = context.getTriggersAssociatedWith(minion.getReference());
		for (IGameEventListener trigger : triggers) {
			trigger.setOwner(player.getId());
		}
	}

	public void modifyCurrentMana(int playerId, int mana) {
		Player player = context.getPlayer(playerId);
		int newMana = Math.min(player.getMana() + mana, MAX_MANA);
		player.setMana(newMana);
	}

	private void modifyDurability(Weapon weapon, GameTag tag, int durability) {
		if (logger.isDebugEnabled()) {
			log(tag + " of weapon {} is changed by {}", weapon, durability);
		}

		weapon.modifyTag(tag, durability);
		if (weapon.isBroken()) {
			destroy(weapon);
		}
	}

	public void modifyDurability(Weapon weapon, int durability) {
		modifyDurability(weapon, GameTag.DURABILITY, durability);
	}

	public void modifyMaxMana(Player player, int delta) {
		log("Maximum mana was changed by {} for {}", delta, player.getName());
		int maxMana = MathUtils.clamp(player.getMaxMana() + delta, 0, GameLogic.MAX_MANA);
		player.setMaxMana(maxMana);
	}

	private void mulligan(Player player, boolean begins) {
		int numberOfStarterCards = begins ? STARTER_CARDS : STARTER_CARDS + 1;
		List<Card> starterCards = new ArrayList<>();
		for (int j = 0; j < numberOfStarterCards; j++) {
			Card randomCard = player.getDeck().getRandom();
			player.getDeck().remove(randomCard);
			log("Player {} been offered card {} for mulligan", player.getName(), randomCard);
			starterCards.add(randomCard);
		}

		List<Card> discardedCards = player.getBehaviour().mulligan(context, player, starterCards);

		// remove player selected cards from starter cards
		for (Card discardedCard : discardedCards) {
			log("Player {} mulligans {} ", player.getName(), discardedCard);
			starterCards.remove(discardedCard);
		}

		// draw random cards from deck until required starter card count is
		// reached
		while (starterCards.size() < numberOfStarterCards) {
			Card randomCard = player.getDeck().getRandom();
			player.getDeck().remove(randomCard);
			starterCards.add(randomCard);
		}

		// put the mulligan cards back in the deck
		for (Card discardedCard : discardedCards) {
			player.getDeck().add(discardedCard);
		}

		for (Card starterCard : starterCards) {
			receiveCard(player.getId(), starterCard);
		}

		// second player gets the coin additionally
		if (!begins) {
			TheCoin theCoin = new TheCoin();
			receiveCard(player.getId(), theCoin);
		}
	}

	public void performGameAction(int playerId, GameAction action) {
		if (playerId != context.getActivePlayerId()) {
			logger.warn("Player {} tries to perform an action, but it is not his turn!", context.getPlayer(playerId).getName());
		}
		action.execute(context, playerId);
		checkForDeadEntities();
	}

	public void playCard(int playerId, CardReference cardReference) {
		Player player = context.getPlayer(playerId);
		Card card = context.resolveCardReference(cardReference);

		int modifiedManaCost = getModifiedManaCost(player, card);
		modifyCurrentMana(playerId, -modifiedManaCost);
		player.getStatistics().manaSpent(modifiedManaCost);
		log("{} plays {}", player.getName(), card);
		player.getHand().remove(card);

		player.getStatistics().cardPlayed(card);

		if (card.getCardType() == CardType.SPELL) {
			GameEvent spellCastedEvent = new SpellCastedEvent(context, playerId, card);
			context.fireGameEvent(spellCastedEvent, TriggerLayer.SECRET);
			if (!card.hasStatus(GameTag.COUNTERED)) {
				context.fireGameEvent(spellCastedEvent);
			} else {
				log("{} was countered!", card.getName());
				return;
			}
		}

		context.fireGameEvent(new CardPlayedEvent(context, playerId, card));

		if (card.hasTag(GameTag.OVERLOAD)) {
			player.getHero().modifyTag(GameTag.OVERLOAD, card.getTagValue(GameTag.OVERLOAD));
			context.fireGameEvent(new OverloadEvent(context, playerId));
		}
	}

	public void playSecret(Player player, Secret secret) {
		log("{} has a new secret activated: {}", player.getName(), secret.getSource());
		addGameEventListener(player, secret, player.getHero());
		player.getSecrets().add(secret.getSource().getTypeId());
		context.fireGameEvent(new SecretPlayedEvent(context, (SecretCard) secret.getSource()));
	}

	/**
	 * 
	 * @param max
	 *            Upper bound of random number (exclusive)
	 * @return Random number between 0 and max (exclusive)
	 */
	public int random(int max) {
		return ThreadLocalRandom.current().nextInt(max);
	}

	public boolean randomBool() {
		return ThreadLocalRandom.current().nextBoolean();
	}

	public void receiveCard(int playerId, Card card) {
		Player player = context.getPlayer(playerId);
		card.setId(idFactory.generateId());
		card.setOwner(playerId);
		CardCollection hand = player.getHand();
		if (hand.getCount() < MAX_HAND_CARDS) {
			log("{} receives card {}", player.getName(), card);
			hand.add(card);
			card.setLocation(CardLocation.HAND);
			context.fireGameEvent(new ReceiveCardEvent(context, playerId, card));
		} else {
			log("{} has too many cards on his hand, card destroyed: {}", player.getName(), card);
			card.setLocation(CardLocation.VOID);
		}
	}

	public void refreshAttacksPerRound(Entity entity) {
		int attacks = 1;
		if (entity.hasStatus(GameTag.SUMMONING_SICKNESS) && !entity.hasStatus(GameTag.CHARGE)) {
			attacks = 0;
		} else if (entity.hasStatus(GameTag.FROZEN)) {
			attacks = 0;
		} else if (entity.hasStatus(GameTag.WINDFURY)) {
			attacks = 2;
		} else if (entity.hasStatus(GameTag.MEGA_WINDFURY)) {
			attacks = 4;
		}
		entity.setTag(GameTag.NUMBER_OF_ATTACKS, attacks);
	}

	public void removeMinion(Minion minion) {
		removeSpelltriggers(minion);

		log("{} was removed", minion);

		minion.setTag(GameTag.DEAD);

		Player owner = context.getPlayer(minion.getOwner());
		owner.getMinions().remove(minion);
		owner.getGraveyard().add(minion);
		context.fireGameEvent(new BoardChangedEvent(context));
	}

	public void removeSecrets(Player player) {
		log("All secrets for {} have been destroyed", player.getName());
		// this only works while Secrets are the only SpellTrigger on the heroes
		context.removeTriggersAssociatedWith(player.getHero().getReference());
		player.getSecrets().clear();
	}

	private void removeSpelltriggers(Actor actor) {
		for (IGameEventListener trigger : context.getTriggersAssociatedWith(actor.getReference())) {
			log("SpellTrigger {} was removed for {}", trigger, actor);
			trigger.onRemove(context);
		}
		context.removeTriggersAssociatedWith(actor.getReference());
	}

	private void resolveBattlecry(int playerId, Actor actor) {
		Battlecry battlecry = actor.getBattlecry();
		Player player = context.getPlayer(playerId);
		if (!battlecry.canBeExecuted(context, player)) {
			return;
		}

		GameAction battlecryAction = null;
		battlecry.setSource(actor.getReference());
		if (battlecry.getTargetRequirement() != TargetSelection.NONE) {
			List<Entity> validTargets = targetLogic.getValidTargets(context, player, battlecry);
			if (validTargets.isEmpty()) {
				return;
			}

			List<GameAction> battlecryActions = new ArrayList<>();
			for (Entity validTarget : validTargets) {
				GameAction targetedBattlecry = battlecry.clone();
				targetedBattlecry.setTarget(validTarget);
				battlecryActions.add(targetedBattlecry);
			}

			battlecryAction = player.getBehaviour().requestAction(context, player, battlecryActions);
		} else {
			battlecryAction = battlecry;
		}
		performGameAction(playerId, battlecryAction);
	}

	public void resolveDeathrattles(Player player, Actor actor) {
		boolean doubleDeathrattles = hasTag(player, GameTag.DOUBLE_DEATHRATTLES);
		// TODO: check when and in which order deathrattles should fire
		if (actor.hasTag(GameTag.DEATHRATTLES)) {
			for (SpellDesc deathrattle : actor.getDeathrattles()) {
				castSpell(player.getId(), deathrattle);
				if (doubleDeathrattles) {
					castSpell(player.getId(), deathrattle);
				}
			}
		}
	}

	public void secretTriggered(Player player, Secret secret) {
		log("Secret was trigged: {}", secret.getSource());
		player.getSecrets().remove((Integer) secret.getSource().getTypeId());
		context.fireGameEvent(new SecretRevealedEvent(context, (SecretCard) secret.getSource(), player.getId()));
	}

	// TODO: circular dependency. Very ugly, refactor!
	public void setContext(GameContext context) {
		this.context = context;
	}

	public void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}

	public void silence(Minion target) {
		context.fireGameEvent(new SilenceEvent(context, target));
		final HashSet<GameTag> immuneToSilence = new HashSet<GameTag>();
		immuneToSilence.add(GameTag.HP);
		immuneToSilence.add(GameTag.MAX_HP);
		immuneToSilence.add(GameTag.BASE_HP);
		immuneToSilence.add(GameTag.BASE_ATTACK);
		immuneToSilence.add(GameTag.SUMMONING_SICKNESS);
		immuneToSilence.add(GameTag.AURA_ATTACK_BONUS);
		immuneToSilence.add(GameTag.AURA_HP_BONUS);
		immuneToSilence.add(GameTag.RACE);
		immuneToSilence.add(GameTag.NUMBER_OF_ATTACKS);
		immuneToSilence.add(GameTag.UNIQUE_MINION);

		List<GameTag> tags = new ArrayList<GameTag>();
		tags.addAll(target.getTags().keySet());
		for (GameTag tag : tags) {
			if (immuneToSilence.contains(tag)) {
				continue;
			}
			target.removeTag(tag);
			if (tag == GameTag.WINDFURY) {
				target.modifyTag(GameTag.NUMBER_OF_ATTACKS, -1);
			}
		}
		removeSpelltriggers(target);
		refreshAttacksPerRound(target);
		target.setHp(target.getMaxHp());
		log("{} was silenced", target);
	}

	public void startTurn(int playerId) {
		Player player = context.getPlayer(playerId);
		if (player.getMaxMana() < MAX_MANA) {
			player.setMaxMana(player.getMaxMana() + 1);
		}
		player.getStatistics().startTurn();

		int mana = MathUtils.clamp(player.getMaxMana() - player.getHero().getTagValue(GameTag.OVERLOAD), 0, MAX_MANA);
		player.setMana(mana);
		player.getHero().removeTag(GameTag.OVERLOAD);
		log("{} starts his turn with {} mana", player.getName(), player.getMana() + "/" + player.getMaxMana());
		player.getHero().getHeroPower().setUsed(false);
		player.getHero().activateWeapon(true);
		refreshAttacksPerRound(player.getHero());
		drawCard(playerId);
		for (Entity minion : player.getMinions()) {
			minion.removeTag(GameTag.SUMMONING_SICKNESS);
			refreshAttacksPerRound(minion);
		}
		context.fireGameEvent(new TurnStartEvent(context, player.getId()));
		checkForDeadEntities();
	}

	public void summon(int playerId, Minion minion) {
		summon(playerId, minion, null, -1, false);
	}

	public void summon(int playerId, Minion minion, Card source, int index, boolean resolveBattlecry) {
		Player player = context.getPlayer(playerId);
		if (!canSummonMoreMinions(player)) {
			log("{} cannot summon any more minions, {} is destroyed", player.getName(), minion);
			return;
		}
		minion.setId(idFactory.generateId());

		context.getSummonStack().push(minion);

		log("{} summons {}", player.getName(), minion);
		minion.setOwner(player.getId());

		if (resolveBattlecry && minion.getBattlecry() != null && !minion.getBattlecry().isResolvedLate()) {
			resolveBattlecry(player.getId(), minion);
		}

		if (index < 0 || index >= player.getMinions().size()) {
			player.getMinions().add(minion);
		} else {
			player.getMinions().add(index, minion);
		}

		SummonEvent summonEvent = new SummonEvent(context, minion, source);
		context.fireGameEvent(summonEvent, TriggerLayer.SECRET);
		context.fireGameEvent(summonEvent, TriggerLayer.DEFAULT);

		minion.setTag(GameTag.SUMMONING_SICKNESS);
		refreshAttacksPerRound(minion);

		if (minion.hasSpellTrigger()) {
			addGameEventListener(player, minion.getSpellTrigger(), minion);
		}

		if (minion.getCardCostModifier() != null) {
			addManaModifier(player, minion.getCardCostModifier(), minion);
		}

		if (resolveBattlecry && minion.getBattlecry() != null && minion.getBattlecry().isResolvedLate()) {
			resolveBattlecry(player.getId(), minion);
		}

		context.getSummonStack().pop();
		context.fireGameEvent(new BoardChangedEvent(context));
	}

	public void useHeroPower(int playerId) {
		Player player = context.getPlayer(playerId);
		HeroPower power = player.getHero().getHeroPower();
		modifyCurrentMana(playerId, -power.getManaCost(context, player));
		log("{} uses {}", player.getName(), power);
		power.setUsed(true);
		player.getStatistics().cardPlayed(power);
	}

}
