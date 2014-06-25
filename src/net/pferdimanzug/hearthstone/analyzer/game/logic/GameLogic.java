package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.events.CardPlayedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.OverloadEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.PhysicalAttackEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SpellCastedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TargetAcquisitionEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnStartEvent;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;
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

	private final TargetLogic targetLogic = new TargetLogic();
	private final ActionLogic actionLogic = new ActionLogic();
	private final IdFactory idFactory;
	private GameContext context;

	public GameLogic() {
		idFactory = new IdFactory();
	}

	private GameLogic(IdFactory idFactory) {
		this.idFactory = idFactory;
	}

	public void addSpellTrigger(Player player, SpellTrigger spellTrigger, Entity target) {
		// there was a .clone() here before, we don't need this, do we?
		spellTrigger.setOwner(player.getId());
		spellTrigger.setHost(target);
		spellTrigger.reset();
		spellTrigger.onAdd(context);
		context.addTrigger(spellTrigger);
		logger.debug("New spelltrigger was added for {} on {}", player.getName(), target);
	}

	private void assignCardIds(CardCollection cardCollection) {
		for (Card card : cardCollection) {
			card.setId(idFactory.generateId());
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
		return player.getMinions().size() < MAX_MINIONS;
	}

	public void castSpell(int playerId, Spell spell) {
		castSpell(playerId, spell, null);
	}

	public void castSpell(int playerId, Spell spell, SpellCard sourceCard) {
		Player player = context.getPlayer(playerId);
		Actor source = null;
		if (spell.getSource() != null) {
			source = (Actor) context.resolveSingleTarget(playerId, spell.getSource());
		}

		List<Entity> targets = targetLogic.resolveTargetKey(context, player, source, spell.getTarget());
		// target can only be changed when there is one target
		// note: this code block is basically exclusively for the SpellBender
		// Secret, but it can easily be expanded if targets of area of effect
		// spell
		// should be changeable as well
		if (sourceCard != null && targets != null && sourceCard.getTargetRequirement() != TargetSelection.NONE
				&& targets.size() == 1) {
			context.fireGameEvent(new TargetAcquisitionEvent(context, ActionType.SPELL, targets.get(0)),
					TriggerLayer.SECRET);
			if (context.getEnvironment().containsKey(Environment.TARGET_OVERRIDE)) {
				targets.remove(0);
				targets.add((Actor) context.getEnvironment().get(Environment.TARGET_OVERRIDE));
				spell.setTarget(targets.get(0).getReference());
				logger.info("Target for spell has been changed! New target {}", targets.get(0));
			}
		}
		spell.cast(context, player, targets);
		if (sourceCard != null) {
			context.getEnvironment().remove(Environment.TARGET_OVERRIDE);
		}
	}

	public void changeDurability(Weapon weapon, int durability) {
		logger.debug("Durability of weapon {} is changed by {}", weapon, durability);
		weapon.modifyTag(GameTag.DURABILITY, durability);
		if (weapon.isBroken()) {
			destroy(weapon);
		}
	}

	public void checkForDeadEntities() {
		for (Player player : context.getPlayers()) {
			for (Minion minion : new ArrayList<Minion>(player.getMinions())) {
				if (minion.isDead()) {
					destroy(minion);
				}
			}
		}
	}

	@Override
	public GameLogic clone() {
		return new GameLogic(idFactory.clone());
	}

	public boolean damage(Player player, Actor target, int damage, boolean applySpellpower) {
		if (applySpellpower) {
			damage += getTotalTagValue(player, GameTag.SPELL_POWER);
		}
		boolean success = false;
		switch (target.getEntityType()) {
		case MINION:
			success = damageMinion((Actor) target, damage);
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
		}

		return success;
	}

	private boolean damageHero(Hero hero, int damage) {
		int effectiveHp = hero.getHp() + hero.getArmor();
		hero.modifyArmor(-damage);
		int newHp = Math.min(hero.getHp(), effectiveHp - damage);
		hero.setHp(newHp);
		logger.debug(hero.getName() + " receives " + damage + " damage, hp now: " + hero.getHp() + "("
				+ hero.getArmor() + ")");
		return true;
	}

	private boolean damageMinion(Actor minion, int damage) {
		if (minion.hasTag(GameTag.DIVINE_SHIELD)) {
			minion.removeTag(GameTag.DIVINE_SHIELD);
			logger.debug("{}'s DIVINE SHIELD absorbs the damage", minion);
			return false;
		}
		logger.debug("{} is damaged for {}", minion, damage);
		minion.setHp(minion.getHp() - damage);
		if (minion.hasTag(GameTag.ENRAGE_SPELL)) {
			handleEnrage(minion);
		}
		return true;
	}

	public void destroy(Actor target) {
		context.removeTriggersAssociatedWith(target.getReference());
		if (target.hasSpellTrigger()) {
			target.getSpellTrigger().onRemove(context);
		}

		switch (target.getEntityType()) {
		case HERO:
			logger.error("Destroying hero not implemented!");
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
		logger.debug("{} is destroyed", minion);
		Player owner = context.getPlayer(minion.getOwner());
		context.getEnvironment().put(Environment.KILLED_MINION, minion);
		KillEvent killEvent = new KillEvent(context, minion);
		context.fireGameEvent(killEvent, TriggerLayer.SECRET);
		context.fireGameEvent(killEvent);
		context.getEnvironment().remove(Environment.KILLED_MINION);

		// set Hp to zero to make .isDead() return true
		if (minion.getHp() > 0) {
			minion.setHp(0);
		}

		// TODO: add unit test for deathrattle; also check when exactly it
		// should be fire
		if (minion.hasTag(GameTag.DEATHRATTLES)) {
			for (Spell deathrattle : minion.getDeathrattles()) {
				castSpell(owner.getId(), deathrattle);
			}
		}
		owner.getMinions().remove(minion);
	}

	private void destroyWeapon(Weapon weapon) {
		Player owner = context.getPlayer(weapon.getOwner());
		owner.getHero().setWeapon(null);
	}

	public int determineBeginner(int... playerIds) {
		return ThreadLocalRandom.current().nextBoolean() ? playerIds[0] : playerIds[1];
	}

	public void drawCard(int playerId) {
		Player player = context.getPlayer(playerId);
		CardCollection deck = player.getDeck();
		if (deck.isEmpty()) {
			Hero hero = player.getHero();
			int fatigue = hero.hasTag(GameTag.FATIGUE) ? hero.getTagValue(GameTag.FATIGUE) : 0;
			hero.setTag(GameTag.FATIGUE, fatigue + 1);
			damage(player, hero, fatigue, false);
			logger.debug("{}'s deck is empty, taking {} fatigue damage!", player.getName(), fatigue);
			return;
		}

		Card card = deck.removeFirst();
		receiveCard(playerId, card);
	}

	public void endTurn(int playerId) {
		Player player = context.getPlayer(playerId);
		player.getHero().removeTag(GameTag.COMBO);
		player.getHero().activateWeapon(false);
		logger.debug("{} ends his turn.", player.getName());
		context.fireGameEvent(new TurnEndEvent(context, player.getId()));
	}

	public void equipWeapon(int playerId, Weapon weapon) {
		Player player = context.getPlayer(playerId);
		weapon.setId(idFactory.generateId());
		Weapon currentWeapon = player.getHero().getWeapon();
		if (currentWeapon != null) {
			logger.debug("{} discards currently equipped weapon {}", player.getHero(), currentWeapon);
			destroy(currentWeapon);
		}
		logger.debug("{} equips weapon {}", player.getHero(), weapon);
		player.getHero().setWeapon(weapon);
		weapon.setActive(context.getActivePlayer() == player);
		if (weapon.hasSpellTrigger()) {
			SpellTrigger spellTrigger = weapon.getSpellTrigger();
			addSpellTrigger(player, spellTrigger, weapon);
		}
	}

	public void fight(Player player, Actor attacker, Actor defender) {
		logger.debug("{} attacks {}", attacker, defender);

		context.getEnvironment().put(Environment.ATTACKER, attacker);
		context.fireGameEvent(new TargetAcquisitionEvent(context, ActionType.PHYSICAL_ATTACK, defender),
				TriggerLayer.SECRET);
		Actor target = defender;
		if (context.getEnvironment().containsKey(Environment.TARGET_OVERRIDE)) {
			target = (Actor) context.getEnvironment().get(Environment.TARGET_OVERRIDE);
		}
		context.getEnvironment().remove(Environment.TARGET_OVERRIDE);

		if (target != defender) {
			logger.debug("Target of attack was changed! New Target: {}", target);
		}

		int attackerDamage = attacker.getAttack();
		int defenderDamage = target.getAttack();
		context.fireGameEvent(new PhysicalAttackEvent(context, attacker, target, attackerDamage), TriggerLayer.SECRET);
		// secret may have killed attacker
		if (attacker.isDead()) {
			return;
		}

		boolean damaged = damage(player, target, attackerDamage, false);
		if (defenderDamage > 0) {
			damage(player, attacker, defenderDamage, false);
		}

		if (attacker.getEntityType() == EntityType.HERO) {
			Hero hero = (Hero) attacker;
			if (hero.getWeapon() != null && hero.getWeapon().isActive()) {
				changeDurability(hero.getWeapon(), -1);
			}
		}

		attacker.modifyTag(GameTag.NUMBER_OF_ATTACKS, -1);
		context.fireGameEvent(new PhysicalAttackEvent(context, attacker, target, damaged ? attackerDamage : 0));
		context.getEnvironment().remove(Environment.ATTACKER);
	}

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

	private int getModifiedManaCost(Player player, Card card) {
		int manaCost = card.getManaCost(player);
		if (card.getCardType() == CardType.MINION) {
			manaCost += getTotalTagValue(player, GameTag.MINION_MANA_COST);
			int minManaCost = getTagValue(player, GameTag.MINION_MIN_MANA_COST, 0);
			manaCost = MathUtils.clamp(manaCost, minManaCost, Integer.MAX_VALUE);
		} else if (card.getCardType() == CardType.SPELL) {
			manaCost += getTotalTagValue(player, GameTag.SPELL_MANA_COST);
		}
		return manaCost;
	}

	public int getTotalTagValue(Player player, GameTag tag) {
		int total = 0;
		for (Entity minion : player.getMinions()) {
			if (!minion.hasTag(tag)) {
				continue;
			}

			total += minion.getTagValue(tag);
		}
		return total;
	}

	public int getTagValue(Player player, GameTag tag, int defaultValue) {
		for (Entity minion : player.getMinions()) {
			if (!minion.hasTag(tag)) {
				continue;
			}

			return minion.getTagValue(tag);
		}
		return defaultValue;
	}

	public List<GameAction> getValidActions(int playerId) {
		Player player = context.getPlayer(playerId);
		return actionLogic.getValidActions(context, player);
	}

	public List<Entity> getValidTargets(int playerId, GameAction action) {
		Player player = context.getPlayer(playerId);
		return targetLogic.getValidTargets(context, player, action);
	}

	private void handleEnrage(Actor entity) {
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

	public void heal(Actor target, int healing) {
		switch (target.getEntityType()) {
		case MINION:
			healMinion((Actor) target, healing);
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

	private void healMinion(Actor minion, int healing) {
		int newHp = Math.min(minion.getMaxHp(), minion.getHp() + healing);
		if (logger.isDebugEnabled()) {
			logger.debug(minion + " is healed for " + healing + ", hp now: " + newHp + "/" + minion.getMaxHp());
		}

		minion.setHp(newHp);
		if (minion.hasTag(GameTag.ENRAGE_SPELL)) {
			handleEnrage(minion);
		}
	}

	public void init(int playerId, boolean begins) {
		Player player = context.getPlayer(playerId);
		player.getHero().setId(idFactory.generateId());
		player.getHero().setOwner(player.getId());
		player.getHero().setMaxHp(MAX_HERO_HP);
		player.getHero().setHp(MAX_HERO_HP);

		assignCardIds(player.getDeck());
		assignCardIds(player.getHand());
		assignCardIds(player.getGraveyard());

		logger.debug("Setting hero hp to {} for {}", player.getHero().getHp(), player.getName());

		player.getDeck().shuffle();

		for (int j = 0; j < STARTER_CARDS; j++) {
			drawCard(playerId);
		}
		// second player gets additional card + TheCoin
		if (!begins) {
			drawCard(playerId);
			TheCoin theCoin = new TheCoin();
			receiveCard(playerId, theCoin);
		}
	}

	public void modifyCurrentMana(int playerId, int mana) {
		Player player = context.getPlayer(playerId);
		int newMana = Math.min(player.getMana() + mana, MAX_MANA);
		player.setMana(newMana);
	}

	public void performGameAction(int playerId, GameAction action) {
		if (action.getTargetRequirement() == TargetSelection.SELF) {
			action.setTargetKey(action.getSource());
		}
		Player player = context.getPlayer(playerId);
		if (action.getTargetRequirement() != TargetSelection.NONE && action.getTargetKey() == null) {
			List<Entity> validTargets = getValidTargets(playerId, action);
			if (validTargets.isEmpty() && action.getActionType() == ActionType.MINION_ABILITY) {
				return;
			}
			action.setValidTargets(validTargets);
			Entity target = player.getBehaviour().provideTargetFor(player, action);
			if (!validTargets.contains(target)) {
				throw new IllegalArgumentException("Selected invalid target " + target + " for action " + action);
			}
			if (target != null) {
				action.setTargetKey(target.getReference());
			}

		}
		action.execute(context, playerId);
		checkForDeadEntities();
	}

	public void playCard(int playerId, CardReference cardReference) {
		Player player = context.getPlayer(playerId);
		Card card = context.resolveCardReference(cardReference);
		modifyCurrentMana(playerId, -getModifiedManaCost(player, card));
		logger.debug("{} plays {}", player.getName(), card);
		player.getHand().remove(card);
		player.getGraveyard().add(card);
		player.getHero().modifyTag(GameTag.COMBO, +1);

		if (card.getCardType() == CardType.SPELL) {
			GameEvent spellCastedEvent = new SpellCastedEvent(context, playerId, card);
			context.fireGameEvent(spellCastedEvent, TriggerLayer.SECRET);
			if (!card.hasTag(GameTag.COUNTERED)) {
				context.fireGameEvent(spellCastedEvent);
			} else {
				logger.debug("{} was countered!", card.getName());
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
		logger.debug("{} has a new secret activated: {}", player.getName(), secret.getSource());
		addSpellTrigger(player, secret, player.getHero());
		player.getSecrets().add(secret.getSource().getTypeId());
	}

	public void receiveCard(int playerId, Card card) {
		Player player = context.getPlayer(playerId);
		if (card.getId() == IdFactory.UNASSIGNED) {
			card.setId(idFactory.generateId());
		}
		CardCollection hand = player.getHand();
		if (hand.getCount() < MAX_HAND_CARDS) {
			logger.debug("{} receives card {}", player.getName(), card);
			hand.add(card);
		} else {
			CardCollection graveyard = player.getGraveyard();
			logger.debug("{} has too many cards on his hand, card destroyed: {}", player.getName(), card);
			graveyard.add(card);
		}
	}

	private void refreshAttacksPerRound(Entity entity) {
		int attacks = 1;
		if (entity.hasTag(GameTag.SUMMONING_SICKNESS) && !entity.hasTag(GameTag.CHARGE)) {
			attacks = 0;
		} else if (entity.hasTag(GameTag.FROZEN)) {
			attacks = 0;
		} else if (entity.hasTag(GameTag.WINDFURY)) {
			attacks = 2;
		}
		entity.setTag(GameTag.NUMBER_OF_ATTACKS, attacks);
	}

	public void removeMinion(Actor minion) {
		context.removeTriggersAssociatedWith(minion.getReference());
		if (minion.hasSpellTrigger()) {
			minion.getSpellTrigger().onRemove(context);
		}

		logger.debug("{} was removed", minion);
		// set Hp to zero to make .isDead() return true
		minion.setHp(0);

		Player owner = context.getPlayer(minion.getOwner());
		owner.getMinions().remove(minion);
	}

	private void resolveBattlecry(int playerId, Minion minion) {
		GameAction battlecry = minion.getBattlecry();
		battlecry.setSource(minion.getReference());
		performGameAction(playerId, battlecry);
	}

	public void secretTriggered(Player player, Secret secret) {
		logger.debug("Secret was trigged: {}", secret.getSource());
		player.getSecrets().remove((Integer) secret.getSource().getTypeId());
	}

	// TODO: circular dependency. Very ugly, refactor!
	public void setContext(GameContext context) {
		this.context = context;
	}

	public void silence(Minion target) {
		final HashSet<GameTag> immuneToSilence = new HashSet<GameTag>();
		immuneToSilence.add(GameTag.HP);
		immuneToSilence.add(GameTag.MAX_HP);
		immuneToSilence.add(GameTag.BASE_HP);
		immuneToSilence.add(GameTag.BASE_ATTACK);
		immuneToSilence.add(GameTag.SUMMONING_SICKNESS);
		immuneToSilence.add(GameTag.AURA_ATTACK_BONUS);
		immuneToSilence.add(GameTag.AURA_HP_BONUS);

		List<GameTag> tags = new ArrayList<GameTag>();
		tags.addAll(target.getTags().keySet());
		for (GameTag tag : tags) {
			if (immuneToSilence.contains(tag)) {
				continue;
			}
			target.removeTag(tag);
		}
		context.removeTriggersAssociatedWith(target.getReference());
	}

	public void startTurn(int playerId) {
		Player player = context.getPlayer(playerId);
		if (player.getMaxMana() < MAX_MANA) {
			player.setMaxMana(player.getMaxMana() + 1);
		}

		player.setMana(player.getMaxMana() - player.getHero().getTagValue(GameTag.OVERLOAD));
		player.getHero().removeTag(GameTag.OVERLOAD);
		logger.debug("{} starts his turn with {} mana", player.getName(), player.getMana() + "/" + player.getMaxMana());
		player.getHero().getHeroPower().setUsed(false);
		player.getHero().activateWeapon(true);
		refreshAttacksPerRound(player.getHero());
		drawCard(playerId);
		for (Entity minion : player.getMinions()) {
			minion.removeTag(GameTag.SUMMONING_SICKNESS);
			refreshAttacksPerRound(minion);
		}
		context.fireGameEvent(new TurnStartEvent(context, player.getId()));
	}

	public void summon(int playerId, Minion minion, Card source, Actor nextTo, boolean resolveBattlecry) {
		Player player = context.getPlayer(playerId);
		minion.setId(idFactory.generateId());
		// TODO: this does not work correctly. Spells referring to this may encounter a NullPointerException
		// because another spell was triggered in response to the SummonEvent, which itself summons a
		// minion, reaches the end of this method and removes the Environment.SUMMONED_MINION
		// need a stack or another approach here!
		context.getEnvironment().put(Environment.SUMMONED_MINION, minion);
		logger.debug("{} summons {}", player.getName(), minion);

		minion.setOwner(player.getId());

		if (resolveBattlecry && minion.getBattlecry() != null && !minion.getBattlecry().isResolvedLate()) {
			resolveBattlecry(player.getId(), minion);
		}

		int index = player.getMinions().indexOf(nextTo);
		if (index == -1) {
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
			addSpellTrigger(player, minion.getSpellTrigger(), minion);
		}

		if (resolveBattlecry && minion.getBattlecry() != null && minion.getBattlecry().isResolvedLate()) {
			resolveBattlecry(player.getId(), minion);
		}

		context.getEnvironment().remove(Environment.SUMMONED_MINION);
	}

	private List<Entity> toList(Actor entity) {
		List<Entity> list = new ArrayList<>(1);
		list.add(entity);
		return list;
	}

	public void useHeroPower(int playerId, HeroPower power) {
		Player player = context.getPlayer(playerId);
		modifyCurrentMana(playerId, -power.getManaCost(player));
		logger.debug("{} uses {}", player.getName(), power);
		power.setUsed(true);
	}

}
