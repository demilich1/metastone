package net.demilich.metastone.game.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.AppConfig;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.events.AfterSpellCastedEvent;
import net.demilich.metastone.game.events.ArmorGainedEvent;
import net.demilich.metastone.game.events.BoardChangedEvent;
import net.demilich.metastone.game.events.CardPlayedEvent;
import net.demilich.metastone.game.events.DamageEvent;
import net.demilich.metastone.game.events.DiscardEvent;
import net.demilich.metastone.game.events.DrawCardEvent;
import net.demilich.metastone.game.events.EnrageChangedEvent;
import net.demilich.metastone.game.events.FatalDamageEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.HealEvent;
import net.demilich.metastone.game.events.HeroPowerUsedEvent;
import net.demilich.metastone.game.events.JoustEvent;
import net.demilich.metastone.game.events.KillEvent;
import net.demilich.metastone.game.events.OverloadEvent;
import net.demilich.metastone.game.events.PhysicalAttackEvent;
import net.demilich.metastone.game.events.SecretPlayedEvent;
import net.demilich.metastone.game.events.SecretRevealedEvent;
import net.demilich.metastone.game.events.SilenceEvent;
import net.demilich.metastone.game.events.SpellCastedEvent;
import net.demilich.metastone.game.events.SummonEvent;
import net.demilich.metastone.game.events.TargetAcquisitionEvent;
import net.demilich.metastone.game.events.TurnEndEvent;
import net.demilich.metastone.game.events.TurnStartEvent;
import net.demilich.metastone.game.events.WeaponDestroyedEvent;
import net.demilich.metastone.game.events.WeaponEquippedEvent;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.SpellFactory;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TriggerLayer;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.IdFactory;
import net.demilich.metastone.game.targeting.TargetSelection;
import net.demilich.metastone.utils.MathUtils;

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
	public static final int TURN_LIMIT = 100;

	public static final int WINDFURY_ATTACKS = 2;
	public static final int MEGA_WINDFURY_ATTACKS = 4;

	private static boolean hasPlayerLost(Player player) {
		return player.getHero().getHp() < 1 || player.getHero().hasAttribute(Attribute.DESTROYED);
	}

	private final TargetLogic targetLogic = new TargetLogic();
	private final ActionLogic actionLogic = new ActionLogic();
	private final SpellFactory spellFactory = new SpellFactory();
	private final IdFactory idFactory;
	private GameContext context;

	private boolean loggingEnabled = true;

	// DEBUG
	private final int MAX_HISTORY_ENTRIES = 100;
	private Queue<String> debugHistory = new LinkedList<>();

	public GameLogic() {
		idFactory = new IdFactory();
	}

	private GameLogic(IdFactory idFactory) {
		this.idFactory = idFactory;
	}

	public void addGameEventListener(Player player, IGameEventListener gameEventListener, Entity target) {
		gameEventListener.setHost(target);
		gameEventListener.setOwner(player.getId());
		gameEventListener.onAdd(context);
		context.addTrigger(gameEventListener);
		log("New spelltrigger was added for {} on {}: {}", player.getName(), target, gameEventListener);
	}

	public void addManaModifier(Player player, CardCostModifier cardCostModifier, Entity target) {
		context.getCardCostModifiers().add(cardCostModifier);
		addGameEventListener(player, cardCostModifier, target);
	}

	public void afterCardPlayed(int playerId, CardReference cardReference) {
		Player player = context.getPlayer(playerId);

		player.getHero().modifyAttribute(Attribute.COMBO, +1);
		Card card = context.resolveCardReference(cardReference);
		
		if (card.getCardType() == CardType.SPELL && !card.hasAttribute(Attribute.COUNTERED)) {
			checkForDeadEntities();
			context.fireGameEvent(new AfterSpellCastedEvent(context, playerId, card));
		}
		card.removeAttribute(Attribute.MANA_COST_MODIFIER);
	}

	public int applyAmplify(Player player, int baseValue) {
		int amplify = 1 + getTotalAttributeValue(player, Attribute.SPELL_AMPLIFY_MULTIPLIER);
		return baseValue * amplify;
	}

	public void applyAttribute(Entity entity, Attribute attr) {
		if (attr == Attribute.MEGA_WINDFURY && entity.hasAttribute(Attribute.WINDFURY)) {
			entity.modifyAttribute(Attribute.NUMBER_OF_ATTACKS, MEGA_WINDFURY_ATTACKS - WINDFURY_ATTACKS);
		} else if (attr == Attribute.WINDFURY && !entity.hasAttribute(Attribute.WINDFURY)
				&& !entity.hasAttribute(Attribute.MEGA_WINDFURY)) {
			entity.modifyAttribute(Attribute.NUMBER_OF_ATTACKS, WINDFURY_ATTACKS - 1);
		} else if (attr == Attribute.MEGA_WINDFURY && !entity.hasAttribute(Attribute.MEGA_WINDFURY)) {
			entity.modifyAttribute(Attribute.NUMBER_OF_ATTACKS, MEGA_WINDFURY_ATTACKS - 1);
		}
		entity.setAttribute(attr);
		log("Applying attr {} to {}", attr, entity);
	}

	public int applyHeroPowerDamage(Player player, int baseValue) {
		int spellpower = getTotalAttributeValue(player, Attribute.HERO_POWER_DAMAGE);
		return baseValue + spellpower;
	}

	public int applySpellpower(Player player, Entity source, int baseValue) {
		int spellpower = getTotalAttributeValue(player, Attribute.SPELL_DAMAGE);
		if (source.hasAttribute(Attribute.SPELL_DAMAGE_MULTIPLIER)) {
			spellpower *= source.getAttributeValue(Attribute.SPELL_DAMAGE_MULTIPLIER);
		}
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
			int heroPowerUsages = getTotalAttributeValue(player, Attribute.HERO_POWER_USAGES);
			if (heroPowerUsages == 0) {
				heroPowerUsages = 1;
			}
			if (power.hasBeenUsed() >= heroPowerUsages) {
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
		return player.getSecrets().size() < MAX_SECRETS && !player.getSecrets().contains(card.getCardId());
	}

	public boolean canSummonMoreMinions(Player player) {
		int minionsInPlay = 0;
		for (Minion minion : player.getMinions()) {
			if (minion.isDestroyed()) {
				continue;
			}
			minionsInPlay++;
		}
		return minionsInPlay < MAX_MINIONS;
	}

	public void castSpell(int playerId, SpellDesc spellDesc, EntityReference sourceReference, EntityReference targetReference,
			boolean childSpell) {
		Player player = context.getPlayer(playerId);
		Entity source = null;
		if (sourceReference != null) {
			try {
				source = context.resolveSingleTarget(sourceReference);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error resolving source entity while casting spell: " + spellDesc);
			}

		}
		SpellCard spellCard = null;
		EntityReference spellTarget = spellDesc.hasPredefinedTarget() ? spellDesc.getTarget() : targetReference;
		List<Entity> targets = targetLogic.resolveTargetKey(context, player, source, spellTarget);
		// target can only be changed when there is one target
		// note: this code block is basically exclusively for the SpellBender
		// Secret, but it can easily be expanded if targets of area of effect
		// spell should be changeable as well
		Card sourceCard = source.getEntityType() == EntityType.CARD ? (Card) source : null;
		if (sourceCard != null && sourceCard.getCardType() == CardType.SPELL && !spellDesc.hasPredefinedTarget() && targets != null
				&& targets.size() == 1) {
			if (sourceCard instanceof SpellCard) {
				spellCard = (SpellCard) sourceCard;
			}

			if (spellCard != null && spellCard.getTargetRequirement() != TargetSelection.NONE && !childSpell) {
				GameEvent spellTargetEvent = new TargetAcquisitionEvent(context, ActionType.SPELL, spellCard, targets.get(0));
				context.fireGameEvent(spellTargetEvent);
				Entity targetOverride = (Entity) context.getEnvironment().get(Environment.TARGET_OVERRIDE);
				if (targetOverride != null && targetOverride.getId() != IdFactory.UNASSIGNED) {
					targets.remove(0);
					targets.add(targetOverride);
					log("Target for spell {} has been changed! New target {}", spellCard, targets.get(0));
				}
			}

		}
		try {
			Spell spell = spellFactory.getSpell(spellDesc);
			spell.cast(context, player, spellDesc, source, targets);
		} catch (Exception e) {
			logger.error("Error while casting spell: " + spellDesc);
			panicDump();
			e.printStackTrace();
		}

		if (spellCard != null) {
			context.getEnvironment().remove(Environment.TARGET_OVERRIDE);
		}
	}

	public void changeHero(Player player, Hero hero) {
		hero.setId(player.getHero().getId());
		if (hero.getHeroClass() == null || hero.getHeroClass() == HeroClass.ANY) {
			hero.setHeroClass(player.getHero().getHeroClass());
		}

		Map<Attribute, Object> attributesToCopy = player.getHero().getAttributesCopy();
		for (Map.Entry<Attribute, Object> entry : attributesToCopy.entrySet()) {
			hero.setAttribute(entry.getKey(), entry.getValue());
		}

		log("{}'s hero has been changed to {}", player.getName(), hero);
		hero.setOwner(player.getId());
		hero.setWeapon(player.getHero().getWeapon());
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
				if (minion.isDestroyed() && player.getMinions().contains(minion)) {
					destroy(minion);
				}
			}
			if (player.getHero().getWeapon() != null && player.getHero().getWeapon().isBroken()) {
				destroy(player.getHero().getWeapon());
			}
		}

		// a death of one minion may trigger the death of another one, so if
		// there are still dead entities: run again
		for (Player player : context.getPlayers()) {
			for (Minion minion : player.getMinions()) {
				if (minion.isDestroyed()) {
					checkForDeadEntities();
					break;
				}
			}
		}
	}

	@Override
	public GameLogic clone() {
		GameLogic clone = new GameLogic(idFactory.clone());
		clone.debugHistory = new LinkedList<>(debugHistory);
		return clone;
	}

	public int damage(Player player, Actor target, int baseDamage, Entity source) {
		return damage(player, target, baseDamage, source, false);
	}

	public int damage(Player player, Actor target, int baseDamage, Entity source, boolean ignoreSpellPower) {
		// sanity check to prevent StackOverFlowError with Mistress of Pain + Auchenai Soulpriest
		if (target.getHp() < -100) {
			return 0;
		}
		int damage = baseDamage;
		Card sourceCard = source != null && source.getEntityType() == EntityType.CARD ? (Card) source : null;
		if (!ignoreSpellPower && sourceCard != null) {
			if (sourceCard.getCardType() == CardType.SPELL) {
				damage = applySpellpower(player, source, baseDamage);
			} else if (sourceCard.getCardType() == CardType.HERO_POWER) {
				damage = applyHeroPowerDamage(player, damage);
			}
		}

		if (!ignoreSpellPower && sourceCard != null
				&& (sourceCard.getCardType() == CardType.SPELL || sourceCard.getCardType() == CardType.HERO_POWER)) {
			damage = applyAmplify(player, damage);
		}
		int damageDealt = 0;
		switch (target.getEntityType()) {
		case MINION:
			damageDealt = damageMinion(player, (Actor) target, damage);
			break;
		case HERO:
			Player owner = context.getPlayer(target.getOwner());
			Minion meatshield = findMeatshield(owner);
			if (meatshield != null) {
				target = meatshield;
				damageDealt = damageMinion(player, meatshield, damage);
				break;
			} else if (isFatalDamage(target, damage)) {
				FatalDamageEvent fatalDamageEvent = new FatalDamageEvent(context, target);
				context.fireGameEvent(fatalDamageEvent);
			}
			damageDealt = damageHero((Hero) target, damage);
			break;
		default:
			break;
		}

		target.setAttribute(Attribute.LAST_HIT, damageDealt);
		if (damageDealt > 0) {
			DamageEvent damageEvent = new DamageEvent(context, target, source, damageDealt);
			context.fireGameEvent(damageEvent);
			player.getStatistics().damageDealt(damageDealt);
		}

		return damageDealt;
	}

	private int damageHero(Hero hero, int damage) {
		if (hero.hasAttribute(Attribute.IMMUNE)) {
			log("{} is IMMUNE and does not take damage", hero);
			return 0;
		}
		if (hero.hasAttribute(Attribute.CURSED)) {
			damage *= 2;
		}
		int effectiveHp = hero.getHp() + hero.getArmor();
		hero.modifyArmor(-damage);
		int newHp = Math.min(hero.getHp(), effectiveHp - damage);
		hero.setHp(newHp);
		log(hero.getName() + " receives " + damage + " damage, hp now: " + hero.getHp() + "(" + hero.getArmor() + ")");
		return damage;
	}

	private Minion findMeatshield(Player player) {
		for (Minion minion : player.getMinions()) {
			if (minion.hasAttribute(Attribute.MEATSHIELD)) {
				return minion;
			}
		}
		return null;
	}

	private int damageMinion(Player player, Actor minion, int damage) {
		if (minion.hasAttribute(Attribute.DIVINE_SHIELD)) {
			removeAttribute(minion, Attribute.DIVINE_SHIELD);
			log("{}'s DIVINE SHIELD absorbs the damage", minion);
			return 0;
		}
		if (minion.hasAttribute(Attribute.IMMUNE)) {
			log("{} is IMMUNE and does not take damage", minion);
			return 0;
		}
		Player owner = context.getPlayer(minion.getOwner());
		if (damage >= minion.getHp() && owner.getHero().hasAttribute(Attribute.CANNOT_REDUCE_HP_BELOW_1)) {
			damage = minion.getHp() - 1;
		}

		log("{} is damaged for {}", minion, damage);
		minion.setHp(minion.getHp() - damage);
		handleEnrage(minion);
		return damage;
	}

	public void destroy(Actor target) {
		removeSpelltriggers(target);

		switch (target.getEntityType()) {
		case HERO:
			log("Hero {} has been destroyed.", target.getName());
			applyAttribute(target, Attribute.DESTROYED);
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

		context.fireGameEvent(new BoardChangedEvent(context));
	}

	private void destroyMinion(Minion minion) {
		log("{} is destroyed", minion);
		Player owner = context.getPlayer(minion.getOwner());
		context.getEnvironment().put(Environment.KILLED_MINION, minion);
		KillEvent killEvent = new KillEvent(context, minion);
		context.fireGameEvent(killEvent);
		context.getEnvironment().remove(Environment.KILLED_MINION);

		minion.setAttribute(Attribute.DESTROYED);
		minion.setAttribute(Attribute.DIED_ON_TURN, context.getTurn());

		int boardPosition = owner.getMinions().indexOf(minion);
		owner.getMinions().remove(boardPosition);
		owner.getGraveyard().add(minion);

		resolveDeathrattles(owner, minion, boardPosition);
	}

	private void destroyWeapon(Weapon weapon) {
		Player owner = context.getPlayer(weapon.getOwner());
		resolveDeathrattles(owner, weapon);
		weapon.onUnequip(context, owner);
		owner.getHero().setWeapon(null);
		owner.getGraveyard().add(weapon);
		context.fireGameEvent(new WeaponDestroyedEvent(context, weapon));
	}

	public int determineBeginner(int... playerIds) {
		return ThreadLocalRandom.current().nextBoolean() ? playerIds[0] : playerIds[1];
	}

	public void discardCard(Player player, Card card) {
		logger.debug("{} discards {}", player.getName(), card);
		// only a 'real' discard should fire a DiscardEvent
		if (card.getLocation() == CardLocation.HAND) {
			context.fireGameEvent(new DiscardEvent(context, player.getId(), card));
		}

		removeCard(player.getId(), card);
	}

	public Card drawCard(int playerId, Entity source) {
		Player player = context.getPlayer(playerId);
		CardCollection deck = player.getDeck();
		if (deck.isEmpty()) {
			Hero hero = player.getHero();
			int fatigue = hero.hasAttribute(Attribute.FATIGUE) ? hero.getAttributeValue(Attribute.FATIGUE) : 0;
			fatigue++;
			hero.setAttribute(Attribute.FATIGUE, fatigue);
			damage(player, hero, fatigue, null);
			log("{}'s deck is empty, taking {} fatigue damage!", player.getName(), fatigue);
			player.getStatistics().fatigueDamage(fatigue);
			return null;
		}

		Card card = deck.getRandom();
		return drawCard(playerId, card, source);
	}

	public Card drawCard(int playerId, Card card, Entity source) {
		Player player = context.getPlayer(playerId);
		player.getStatistics().cardDrawn();
		player.getDeck().remove(card);
		receiveCard(playerId, card);
		CardType sourceType = null;
		if (source instanceof Card) {
			Card sourceCard = (Card) source;
			sourceType = sourceCard.getCardType();
		}
		context.fireGameEvent(new DrawCardEvent(context, playerId, card, sourceType));
		return card;
	}

	public void endTurn(int playerId) {
		Player player = context.getPlayer(playerId);

		Hero hero = player.getHero();
		hero.removeAttribute(Attribute.TEMPORARY_ATTACK_BONUS);
		hero.removeAttribute(Attribute.CANNOT_REDUCE_HP_BELOW_1);
		handleFrozen(hero);
		for (Minion minion : player.getMinions()) {
			minion.removeAttribute(Attribute.TEMPORARY_ATTACK_BONUS);
			minion.removeAttribute(Attribute.CANNOT_REDUCE_HP_BELOW_1);
			handleFrozen(minion);
		}
		hero.removeAttribute(Attribute.COMBO);
		hero.activateWeapon(false);
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

	private void handleFrozen(Actor actor) {
		if (!actor.hasAttribute(Attribute.FROZEN)) {
			return;
		}
		if (actor.getAttributeValue(Attribute.NUMBER_OF_ATTACKS) >= actor.getMaxNumberOfAttacks()) {
			removeAttribute(actor, Attribute.FROZEN);
		}
	}

	public void equipWeapon(int playerId, Weapon weapon) {
		Player player = context.getPlayer(playerId);

		weapon.setId(idFactory.generateId());
		context.getEnvironment().put(Environment.SUMMONED_WEAPON, weapon);
		Weapon currentWeapon = player.getHero().getWeapon();

		if (currentWeapon != null) {
			log("{} discards currently equipped weapon {}", player.getHero(), currentWeapon);
			destroy(currentWeapon);
		}
		if (weapon.getBattlecry() != null && !weapon.getBattlecry().isResolvedLate()) {
			resolveBattlecry(playerId, weapon);
		}
		player.getStatistics().equipWeapon(weapon);
		context.getEnvironment().remove(Environment.SUMMONED_WEAPON);

		log("{} equips weapon {}", player.getHero(), weapon);
		player.getHero().setWeapon(weapon);
		weapon.onEquip(context, player);
		weapon.setActive(context.getActivePlayerId() == playerId);
		if (weapon.getBattlecry() != null && weapon.getBattlecry().isResolvedLate()) {
			resolveBattlecry(playerId, weapon);
		}
		if (weapon.hasSpellTrigger()) {
			SpellTrigger spellTrigger = weapon.getSpellTrigger();
			addGameEventListener(player, spellTrigger, weapon);
		}
		context.fireGameEvent(new WeaponEquippedEvent(context, weapon));
		context.fireGameEvent(new BoardChangedEvent(context));
	}

	public void fight(Player player, Actor attacker, Actor defender) {
		log("{} attacks {}", attacker, defender);

		context.getEnvironment().put(Environment.ATTACKER, attacker);
		TargetAcquisitionEvent targetAcquisitionEvent = new TargetAcquisitionEvent(context, ActionType.PHYSICAL_ATTACK, attacker, defender);
		context.fireGameEvent(targetAcquisitionEvent);
		Actor target = defender;
		if (context.getEnvironment().containsKey(Environment.TARGET_OVERRIDE)) {
			target = (Actor) context.getEnvironment().get(Environment.TARGET_OVERRIDE);
		}
		context.getEnvironment().remove(Environment.TARGET_OVERRIDE);

		// if (attacker.hasTag(GameTag.FUMBLE) && randomBool()) {
		// log("{} fumbled and hits another target", attacker);
		// target = getAnotherRandomTarget(player, attacker, defender,
		// EntityReference.ENEMY_CHARACTERS);
		// }

		if (target != defender) {
			log("Target of attack was changed! New Target: {}", target);
		}

		if (attacker.hasAttribute(Attribute.IMMUNE_WHILE_ATTACKING)) {
			applyAttribute(attacker, Attribute.IMMUNE);
		}

		removeAttribute(attacker, Attribute.STEALTH);

		int attackerDamage = attacker.getAttack();
		int defenderDamage = target.getAttack();
		context.fireGameEvent(new PhysicalAttackEvent(context, attacker, target, attackerDamage), TriggerLayer.SECRET);
		// secret may have killed attacker ADDENDUM: or defender
		if (attacker.isDestroyed() || target.isDestroyed()) {
			return;
		}

		if (target.getOwner() == -1) {
			logger.error("Target has no owner!! {}", target);
		}

		Player owningPlayer = context.getPlayer(target.getOwner());
		boolean damaged = damage(owningPlayer, target, attackerDamage, attacker) > 0;
		if (defenderDamage > 0) {
			damage(player, attacker, defenderDamage, target);
		}
		if (attacker.hasAttribute(Attribute.IMMUNE_WHILE_ATTACKING)) {
			attacker.removeAttribute(Attribute.IMMUNE);
		}

		if (attacker.getEntityType() == EntityType.HERO) {
			Hero hero = (Hero) attacker;
			Weapon weapon = hero.getWeapon();
			if (weapon != null && weapon.isActive()) {
				modifyDurability(hero.getWeapon(), -1);
			}
		}
		attacker.modifyAttribute(Attribute.NUMBER_OF_ATTACKS, -1);

		context.fireGameEvent(new PhysicalAttackEvent(context, attacker, target, damaged ? attackerDamage : 0));

		context.getEnvironment().remove(Environment.ATTACKER);
	}

	public void gainArmor(Player player, int armor) {
		logger.debug("{} gains {} armor", player.getHero(), armor);
		player.getHero().modifyArmor(armor);
		player.getStatistics().armorGained(armor);
		context.fireGameEvent(new ArmorGainedEvent(context, player.getHero()));
	}

	public Actor getAnotherRandomTarget(Player player, Actor attacker, Actor originalTarget, EntityReference potentialTargets) {
		List<Entity> validTargets = context.resolveTarget(player, null, potentialTargets);
		// cannot redirect to attacker
		validTargets.remove(attacker);
		// cannot redirect to original target
		validTargets.remove(originalTarget);
		if (validTargets.isEmpty()) {
			return originalTarget;
		}

		return (Actor) SpellUtils.getRandomTarget(validTargets);
	}

	/**
	 * Returns the first value of the attribute encountered. This method should
	 * be used with caution, as the result is random if there are different
	 * values of the same attribute in play.
	 * 
	 * @param player
	 * @param attr
	 *            Which attribute to find
	 * @param defaultValue
	 *            The value returned if no occurrence of the attribute is found
	 * @return the first occurrence of the value of attribute or defaultValue
	 */
	public int getAttributeValue(Player player, Attribute attr, int defaultValue) {
		for (Entity minion : player.getMinions()) {
			if (minion.hasAttribute(attr)) {
				return minion.getAttributeValue(attr);
			}
		}

		return defaultValue;
	}

	public MatchResult getMatchResult(Player player, Player opponent) {
		boolean playerLost = hasPlayerLost(player);
		boolean opponentLost = hasPlayerLost(opponent);
		if (playerLost && opponentLost) {
			return MatchResult.DOUBLE_LOSS;
		} else if (playerLost || opponentLost) {
			return MatchResult.WON;
		}
		return MatchResult.RUNNING;
	}

	public int getModifiedManaCost(Player player, Card card) {
		int manaCost = card.getManaCost(context, player);
		int minValue = 0;
		for (CardCostModifier costModifier : context.getCardCostModifiers()) {
			if (!costModifier.appliesTo(card)) {
				continue;
			}
			manaCost = costModifier.process(card, manaCost);
			if (costModifier.getMinValue() > minValue) {
				minValue = costModifier.getMinValue();
			}
		}
		if (card.hasAttribute(Attribute.MANA_COST_MODIFIER)) {
			manaCost += card.getAttributeValue(Attribute.MANA_COST_MODIFIER);
		}
		manaCost = MathUtils.clamp(manaCost, minValue, Integer.MAX_VALUE);
		return manaCost;
	}

	public List<IGameEventListener> getSecrets(Player player) {
		List<IGameEventListener> secrets = context.getTriggersAssociatedWith(player.getHero().getReference());
		for (Iterator<IGameEventListener> iterator = secrets.iterator(); iterator.hasNext();) {
			IGameEventListener trigger = iterator.next();
			if (!(trigger instanceof Secret)) {
				iterator.remove();
			}
		}
		return secrets;
	}

	public int getTotalAttributeValue(Attribute attr) {
		int total = 0;
		for (Player player : context.getPlayers()) {
			total += getTotalAttributeValue(player, attr);
		}
		return total;
	}

	public int getTotalAttributeValue(Player player, Attribute attr) {
		int total = player.getHero().getAttributeValue(attr);
		for (Entity minion : player.getMinions()) {
			if (!minion.hasAttribute(attr)) {
				continue;
			}

			total += minion.getAttributeValue(attr);
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
		boolean playerLost = hasPlayerLost(player);
		boolean opponentLost = hasPlayerLost(opponent);
		if (playerLost && opponentLost) {
			return null;
		} else if (opponentLost) {
			return player;
		} else if (playerLost) {
			return opponent;
		}
		return null;
	}

	private void handleEnrage(Actor entity) {
		if (!entity.hasAttribute(Attribute.ENRAGABLE)) {
			return;
		}
		boolean enraged = entity.getHp() < entity.getMaxHp();
		// enrage state has not changed; do nothing
		if (entity.hasAttribute(Attribute.ENRAGED) == enraged) {
			return;
		}

		if (enraged) {
			log("{} is now enraged", entity);
			entity.setAttribute(Attribute.ENRAGED);
		} else {
			log("{} is no longer enraged", entity);
			entity.removeAttribute(Attribute.ENRAGED);
		}

		context.fireGameEvent(new EnrageChangedEvent(context, entity));
	}

	public boolean hasAttribute(Player player, Attribute attr) {
		if (player.getHero().hasAttribute(attr)) {
			return true;
		}
		for (Entity minion : player.getMinions()) {
			if (minion.hasAttribute(attr)) {
				return true;
			}
		}

		return false;
	}

	public void heal(Player player, Actor target, int healing, Entity source) {
		if (hasAttribute(player, Attribute.INVERT_HEALING)) {
			log("All healing inverted, deal damage instead!");
			damage(player, target, healing, source);
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
		int newHp = Math.min(hero.getMaxHp(), hero.getHp() + healing);
		int oldHp = hero.getHp();
		if (logger.isDebugEnabled()) {
			log(hero + " is healed for " + healing + ", hp now: " + newHp / hero.getMaxHp());
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
		handleEnrage(minion);
		return newHp != oldHp;
	}

	public void init(int playerId, boolean begins) {
		Player player = context.getPlayer(playerId);
		player.getHero().setId(idFactory.generateId());
		player.getHero().setOwner(player.getId());
		player.getHero().setMaxHp(MAX_HERO_HP);
		player.getHero().setHp(MAX_HERO_HP);

		player.getHero().getHeroPower().setId(idFactory.generateId());
		assignCardIds(player.getDeck());
		assignCardIds(player.getHand());

		log("Setting hero hp to {} for {}", player.getHero().getHp(), player.getName());

		player.getDeck().shuffle();

		mulligan(player, begins);
	}

	private boolean isFatalDamage(Entity entity, int damage) {
		Hero hero = (Hero) entity;
		return damage >= hero.getEffectiveHp();
	}

	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	public JoustEvent joust(Player player) {
		Card ownCard = player.getDeck().getRandomOfType(CardType.MINION);
		Card opponentCard = null;
		boolean won = false;
		// no minions left in deck - automatically loose joust
		if (ownCard == null) {
			won = false;
			log("Jousting LOST - no minion card left");
		} else {
			Player opponent = context.getOpponent(player);
			opponentCard = opponent.getDeck().getRandomOfType(CardType.MINION);
			// opponent has no minions left in deck - automatically win joust
			if (opponentCard == null) {
				won = true;
				log("Jousting WON - opponent has no minion card left");
			} else {
				// both players have minion cards left, the initiator needs to
				// have the one with
				// higher mana cost to win the joust
				won = ownCard.getBaseManaCost() > opponentCard.getBaseManaCost();

				log("Jousting {} - {} vs. {}", won ? "WON" : "LOST", ownCard, opponentCard);
			}
		}
		JoustEvent joustEvent = new JoustEvent(context, player.getId(), won, ownCard, opponentCard);
		context.fireGameEvent(joustEvent);
		return joustEvent;
	}

	private void log(String message) {
		logToDebugHistory(message);
		if (isLoggingEnabled() && logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	private void log(String message, Object param1) {
		logToDebugHistory(message, param1);
		if (isLoggingEnabled() && logger.isDebugEnabled()) {
			logger.debug(message, param1);
		}
	}

	private void log(String message, Object param1, Object param2) {
		logToDebugHistory(message, param1, param2);
		if (isLoggingEnabled() && logger.isDebugEnabled()) {
			logger.debug(message, param1, param2);
		}
	}

	private void log(String message, Object param1, Object param2, Object param3) {
		logToDebugHistory(message, param1, param2, param3);
		if (isLoggingEnabled() && logger.isDebugEnabled()) {
			logger.debug(message, param1, param2, param3);
		}
	}

	private void logToDebugHistory(String message, Object... params) {
		if (!AppConfig.DEV_BUILD) {
			return;
		}
		if (debugHistory.size() == MAX_HISTORY_ENTRIES) {
			debugHistory.poll();
		}
		if (params != null && params.length > 0) {
			message = message.replaceAll("\\{\\}", "%s");
			message = String.format(message, params);
		}

		debugHistory.add(message);
	}

	public void markAsDestroyed(Actor target) {
		target.setAttribute(Attribute.DESTROYED);
	}

	public void mindControl(Player player, Minion minion) {
		log("{} mind controls {}", player.getName(), minion);
		Player opponent = context.getOpponent(player);
		if (!opponent.getMinions().contains(minion)) {
			logger.warn("Minion {} cannot be mind-controlled, because opponent does not own it.", minion);
			return;
		}
		if (canSummonMoreMinions(player)) {
			context.getOpponent(player).getMinions().remove(minion);
			player.getMinions().add(minion);
			minion.setOwner(player.getId());
			applyAttribute(minion, Attribute.SUMMONING_SICKNESS);
			List<IGameEventListener> triggers = context.getTriggersAssociatedWith(minion.getReference());
			removeSpelltriggers(minion);
			for (IGameEventListener trigger : triggers) {
				addGameEventListener(player, trigger, minion);
			}
			context.fireGameEvent(new BoardChangedEvent(context));
		} else {
			destroy(minion);
		}
	}

	public void modifyCurrentMana(int playerId, int mana) {
		Player player = context.getPlayer(playerId);
		int newMana = Math.min(player.getMana() + mana, MAX_MANA);
		player.setMana(newMana);
	}

	public void modifyDurability(Weapon weapon, int durability) {
		log("Durability of weapon {} is changed by {}", weapon, durability);

		weapon.modifyAttribute(Attribute.HP, durability);
	}

	public void modifyMaxHp(Actor actor, int value) {
		actor.setMaxHp(value);
		actor.setHp(value);
		handleEnrage(actor);
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
			Card theCoin = CardCatalogue.getCardById("spell_the_coin");
			receiveCard(player.getId(), theCoin);
		}
	}

	public void panicDump() {
		logger.error("=========PANIC DUMP=========");
		for (String entry : debugHistory) {
			logger.error(entry);
		}
	}

	public void performGameAction(int playerId, GameAction action) {
		if (playerId != context.getActivePlayerId()) {
			logger.warn("Player {} tries to perform an action, but it is not his turn!", context.getPlayer(playerId).getName());
		}
		if (action.getTargetRequirement() != TargetSelection.NONE) {
			Entity target = context.resolveSingleTarget(action.getTargetKey());
			context.getEnvironment().put(Environment.TARGET, target);
		}

		action.execute(context, playerId);

		context.getEnvironment().remove(Environment.TARGET);
		checkForDeadEntities();
	}

	public void playCard(int playerId, CardReference cardReference) {
		Player player = context.getPlayer(playerId);
		Card card = context.resolveCardReference(cardReference);

		int modifiedManaCost = getModifiedManaCost(player, card);
		modifyCurrentMana(playerId, -modifiedManaCost);
		player.getStatistics().manaSpent(modifiedManaCost);
		log("{} plays {}", player.getName(), card);

		player.getStatistics().cardPlayed(card);
		CardPlayedEvent cardPlayedEvent = new CardPlayedEvent(context, playerId, card);
		context.fireGameEvent(cardPlayedEvent);

		if (card.hasAttribute(Attribute.OVERLOAD)) {
			context.fireGameEvent(new OverloadEvent(context, playerId));
		}

		removeCard(playerId, card);

		if (card.getCardType() == CardType.SPELL) {
			GameEvent spellCastedEvent = new SpellCastedEvent(context, playerId, card);
			context.fireGameEvent(spellCastedEvent, TriggerLayer.SECRET);
			if (!card.hasAttribute(Attribute.COUNTERED)) {
				context.fireGameEvent(spellCastedEvent);
			} else {
				log("{} was countered!", card.getName());
				return;
			}
		}

		if (card.hasAttribute(Attribute.OVERLOAD)) {
			player.getHero().modifyAttribute(Attribute.OVERLOAD, card.getAttributeValue(Attribute.OVERLOAD));
		}
	}

	public void playSecret(Player player, Secret secret) {
		log("{} has a new secret activated: {}", player.getName(), secret.getSource());
		addGameEventListener(player, secret, player.getHero());
		player.getSecrets().add(secret.getSource().getCardId());
		context.fireGameEvent(new SecretPlayedEvent(context, (SecretCard) secret.getSource()));
	}

	public void processTargetModifiers(Player player, GameAction action) {
		HeroPower heroPower = player.getHero().getHeroPower();
		if (heroPower.getClassRestriction() != HeroClass.HUNTER) {
			return;
		}
		if (action.getActionType() == ActionType.HERO_POWER && hasAttribute(player, Attribute.HERO_POWER_CAN_TARGET_MINIONS)) {
			action.setTargetRequirement(TargetSelection.ANY);
		}
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
		if (card.getId() == IdFactory.UNASSIGNED) {
			card.setId(idFactory.generateId());
		}

		card.setOwner(playerId);
		CardCollection hand = player.getHand();

		if (card.getAttribute(Attribute.PASSIVE_TRIGGER) != null) {
			TriggerDesc triggerDesc = (TriggerDesc) card.getAttribute(Attribute.PASSIVE_TRIGGER);
			addGameEventListener(player, triggerDesc.create(), card);
		}

		if (hand.getCount() < MAX_HAND_CARDS) {
			log("{} receives card {}", player.getName(), card);
			hand.add(card);
			card.setLocation(CardLocation.HAND);
		} else {
			log("{} has too many cards on his hand, card destroyed: {}", player.getName(), card);
			discardCard(player, card);
		}
	}

	public void refreshAttacksPerRound(Entity entity) {
		int attacks = 1;
		if (entity.hasAttribute(Attribute.MEGA_WINDFURY)) {
			attacks = MEGA_WINDFURY_ATTACKS;
		} else if (entity.hasAttribute(Attribute.WINDFURY)) {
			attacks = WINDFURY_ATTACKS;
		}
		entity.setAttribute(Attribute.NUMBER_OF_ATTACKS, attacks);
	}

	public void removeAttribute(Entity entity, Attribute attr) {
		if (!entity.hasAttribute(attr)) {
			return;
		}
		if (attr == Attribute.MEGA_WINDFURY && entity.hasAttribute(Attribute.WINDFURY)) {
			entity.modifyAttribute(Attribute.NUMBER_OF_ATTACKS, WINDFURY_ATTACKS - MEGA_WINDFURY_ATTACKS);
		}
		if (attr == Attribute.WINDFURY && !entity.hasAttribute(Attribute.MEGA_WINDFURY)) {
			entity.modifyAttribute(Attribute.NUMBER_OF_ATTACKS, 1 - WINDFURY_ATTACKS);
		} else if (attr == Attribute.MEGA_WINDFURY) {
			entity.modifyAttribute(Attribute.NUMBER_OF_ATTACKS, 1 - MEGA_WINDFURY_ATTACKS);
		}
		entity.removeAttribute(attr);
		log("Removing attribute {} from {}", attr, entity);
	}

	public void removeCard(int playerId, Card card) {
		Player player = context.getPlayer(playerId);
		log("Card {} has been moved from the HAND to the GRAVEYARD", card);
		card.setLocation(CardLocation.GRAVEYARD);
		if (card.getAttribute(Attribute.PASSIVE_TRIGGER) != null) {
			removeSpelltriggers(card);
		}
		player.getHand().remove(card);
		player.getGraveyard().add(card);
	}
	
	public void removeCardFromDeck(int playerID, Card card) {
		Player player = context.getPlayer(playerID);
		log("Card {} has been moved from the DECK to the GRAVEYARD", card);
		card.setLocation(CardLocation.GRAVEYARD);
		if (card.getAttribute(Attribute.PASSIVE_TRIGGER) != null) {
			removeSpelltriggers(card);
		}
		player.getDeck().remove(card);
		player.getGraveyard().add(card);
	}

	public void removeMinion(Minion minion) {
		removeSpelltriggers(minion);

		log("{} was removed", minion);

		minion.setAttribute(Attribute.DESTROYED);

		Player owner = context.getPlayer(minion.getOwner());
		owner.getMinions().remove(minion);
		owner.getGraveyard().add(minion);
		context.fireGameEvent(new BoardChangedEvent(context));
	}

	public void removeSecrets(Player player) {
		log("All secrets for {} have been destroyed", player.getName());
		// this only works while Secrets are the only SpellTrigger on the heroes
		for (IGameEventListener secret : getSecrets(player)) {
			secret.onRemove(context);
			context.removeTrigger(secret);
		}
		player.getSecrets().clear();
	}

	private void removeSpelltriggers(Entity entity) {
		EntityReference entityReference = entity.getReference();
		for (IGameEventListener trigger : context.getTriggersAssociatedWith(entityReference)) {
			log("SpellTrigger {} was removed for {}", trigger, entity);
			trigger.onRemove(context);
		}
		context.removeTriggersAssociatedWith(entityReference);
		for (Iterator<CardCostModifier> iterator = context.getCardCostModifiers().iterator(); iterator.hasNext();) {
			CardCostModifier cardCostModifier = iterator.next();
			if (cardCostModifier.getHostReference().equals(entityReference)) {
				iterator.remove();
			}
		}
	}
	
	private void resolveBattlecry(int playerId, Actor actor) {
		resolveBattlecry(playerId, actor, 0);
	}

	private void resolveBattlecry(int playerId, Actor actor, int iteration) {
		BattlecryAction battlecry = actor.getBattlecry();
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
		if (iteration < 1 && hasAttribute(player, Attribute.DOUBLE_BATTLECRIES)) {
			resolveBattlecry(playerId, actor, iteration + 1);
		}
	}

	public void resolveDeathrattles(Player player, Actor actor) {
		resolveDeathrattles(player, actor, -1);
	}

	public void resolveDeathrattles(Player player, Actor actor, int boardPosition) {
		if (!actor.hasAttribute(Attribute.DEATHRATTLES)) {
			return;
		}
		if (boardPosition == -1) {
			player.getMinions().indexOf(actor);
		}
		boolean doubleDeathrattles = hasAttribute(player, Attribute.DOUBLE_DEATHRATTLES);
		EntityReference sourceReference = actor.getReference();
		for (SpellDesc deathrattleTemplate : actor.getDeathrattles()) {
			SpellDesc deathrattle = deathrattleTemplate.addArg(SpellArg.BOARD_POSITION_ABSOLUTE, boardPosition);
			castSpell(player.getId(), deathrattle, sourceReference, EntityReference.NONE, false);
			if (doubleDeathrattles) {
				castSpell(player.getId(), deathrattle, sourceReference, EntityReference.NONE, false);
			}
		}
	}

	public void secretTriggered(Player player, Secret secret) {
		log("Secret was trigged: {}", secret.getSource());
		player.getSecrets().remove(secret.getSource().getCardId());
		context.fireGameEvent(new SecretRevealedEvent(context, (SecretCard) secret.getSource(), player.getId()));
	}

	// TODO: circular dependency. Very ugly, refactor!
	public void setContext(GameContext context) {
		this.context = context;
	}

	public void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}

	public void shuffleToDeck(Player player, Card card) {
		if (card.getId() == IdFactory.UNASSIGNED) {
			card.setId(idFactory.generateId());
		}
		card.setLocation(CardLocation.DECK);

		Card randomCard = player.getDeck().getRandom();
		if (randomCard == null) {
			player.getDeck().add(card);
		} else {
			player.getDeck().addAfter(card, randomCard);
		}
		log("Card {} has been shuffled to {}'s deck", card, player.getName());
	}

	public void silence(Minion target) {
		context.fireGameEvent(new SilenceEvent(context, target));
		final HashSet<Attribute> immuneToSilence = new HashSet<Attribute>();
		immuneToSilence.add(Attribute.HP);
		immuneToSilence.add(Attribute.MAX_HP);
		immuneToSilence.add(Attribute.BASE_HP);
		immuneToSilence.add(Attribute.BASE_ATTACK);
		immuneToSilence.add(Attribute.SUMMONING_SICKNESS);
		immuneToSilence.add(Attribute.AURA_ATTACK_BONUS);
		immuneToSilence.add(Attribute.AURA_HP_BONUS);
		immuneToSilence.add(Attribute.RACE);
		immuneToSilence.add(Attribute.NUMBER_OF_ATTACKS);
		immuneToSilence.add(Attribute.UNIQUE_ENTITY);

		List<Attribute> tags = new ArrayList<Attribute>();
		tags.addAll(target.getAttributes().keySet());
		for (Attribute attr : tags) {
			if (immuneToSilence.contains(attr)) {
				continue;
			}
			removeAttribute(target, attr);
		}
		removeSpelltriggers(target);

		int oldMaxHp = target.getMaxHp();
		target.setMaxHp(target.getAttributeValue(Attribute.BASE_HP));
		target.setAttack(target.getAttributeValue(Attribute.BASE_ATTACK));
		if (target.getHp() > target.getMaxHp() || target.getHp() == oldMaxHp) {
			target.setHp(target.getMaxHp());
		}
		if (target.getHp() > target.getMaxHp()) {
			target.setHp(target.getMaxHp());
		}

		log("{} was silenced", target);
	}

	public void startTurn(int playerId) {
		Player player = context.getPlayer(playerId);
		if (player.getMaxMana() < MAX_MANA) {
			player.setMaxMana(player.getMaxMana() + 1);
		}
		player.getStatistics().startTurn();

		player.setLockedMana(player.getHero().getAttributeValue(Attribute.OVERLOAD));
		int mana = MathUtils.clamp(player.getMaxMana() - player.getLockedMana(), 0, MAX_MANA);
		player.setMana(mana);
		String manaString = player.getMana() + "/" + player.getMaxMana();
		if (player.getLockedMana() > 0) {
			manaString += " (" + player.getLockedMana() + " locked by overload)";
		}
		log("{} starts his turn with {} mana", player.getName(), manaString);

		player.getHero().removeAttribute(Attribute.OVERLOAD);
		for (Minion minion : player.getMinions()) {
			minion.removeAttribute(Attribute.TEMPORARY_ATTACK_BONUS);
		}

		player.getHero().getHeroPower().setUsed(0);
		player.getHero().activateWeapon(true);
		refreshAttacksPerRound(player.getHero());
		drawCard(playerId, null);
		for (Entity minion : player.getMinions()) {
			minion.removeAttribute(Attribute.SUMMONING_SICKNESS);
			refreshAttacksPerRound(minion);
		}
		context.fireGameEvent(new TurnStartEvent(context, player.getId()));
		checkForDeadEntities();
	}

	public boolean summon(int playerId, Minion minion) {
		return summon(playerId, minion, null, -1, false);
	}

	public boolean summon(int playerId, Minion minion, Card source, int index, boolean resolveBattlecry) {
		Player player = context.getPlayer(playerId);
		if (!canSummonMoreMinions(player)) {
			log("{} cannot summon any more minions, {} is destroyed", player.getName(), minion);
			return false;
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
		context.fireGameEvent(summonEvent);

		applyAttribute(minion, Attribute.SUMMONING_SICKNESS);
		refreshAttacksPerRound(minion);
		if (player.getHero().hasAttribute(Attribute.CANNOT_REDUCE_HP_BELOW_1)) {
			minion.setAttribute(Attribute.CANNOT_REDUCE_HP_BELOW_1);
		}

		if (minion.hasSpellTrigger()) {
			addGameEventListener(player, minion.getSpellTrigger(), minion);
		}

		if (minion.getCardCostModifier() != null) {
			addManaModifier(player, minion.getCardCostModifier(), minion);
		}

		if (resolveBattlecry && minion.getBattlecry() != null && minion.getBattlecry().isResolvedLate()) {
			resolveBattlecry(player.getId(), minion);
		}

		handleEnrage(minion);

		context.getSummonStack().pop();
		context.fireGameEvent(new BoardChangedEvent(context));
		return true;
	}

	public void useHeroPower(int playerId) {
		Player player = context.getPlayer(playerId);
		HeroPower power = player.getHero().getHeroPower();
		int modifiedManaCost = getModifiedManaCost(player, power);
		modifyCurrentMana(playerId, -modifiedManaCost);
		log("{} uses {}", player.getName(), power);
		power.markUsed();
		player.getHero().modifyAttribute(Attribute.HERO_POWER_USED, +1);
		player.getStatistics().cardPlayed(power);
		context.fireGameEvent(new HeroPowerUsedEvent(context, playerId, power));
	}

}
