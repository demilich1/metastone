package com.hiddenswitch.proto3.net.common;

import io.vertx.core.Handler;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.targeting.TargetSelection;

import java.util.List;

/**
 * Created by bberman on 11/23/16.
 */
public class AsyncGameLogic extends GameLogic {
	@Override
	protected void mulligan(Player player, boolean begins) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("AsyncGameLogic::mulligan is unsupported. Use AsyncGameLogic::mulliganAsync instead.");
	}

	@Override
	protected void mulliganAsync(Player player, boolean begins, Handler<Object> callback) {
		FirstHand firstHand = new FirstHand(player, begins).invoke();

		NetworkBehaviour networkBehaviour = (NetworkBehaviour) player.getBehaviour();

		networkBehaviour.mulligan((ServerGameContext) context, player, firstHand.getStarterCards(), (List<Card> discardedCards) -> {
			handleMulligan(player, begins, firstHand, discardedCards);
			callback.handle(null);
		});
	}

	@Override
	public void init(int playerId, boolean begins) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("AsyncGameLogic::init is unsupported. Use AsyncGameLogic::initAsync instead.");
	}

	@Override
	public void initAsync(int playerId, boolean begins, Handler<Player> callback) {
		Player player = initializePlayer(playerId);

		mulliganAsync(player, begins, o -> {
			startGameForPlayer(player);
			callback.handle(player);
		});
	}

//	@Override
//	protected void resolveBattlecry(int playerId, Actor actor) throws UnsupportedOperationException {
//		throw new UnsupportedOperationException("AsyncGameLogic::resolveBattlecry is unsupported. Use AsyncGameLogic::resolveBattlecryAsync instead.");
//	}

	@Override
	protected void resolveBattlecryAsync(int playerId, Actor actor, Handler<GameAction> result) {
		BattlecryAction battlecry = actor.getBattlecry();

		Player player = context.getPlayer(playerId);
		if (!battlecry.canBeExecuted(context, player)) {
			return;
		}

		battlecry.setSource(actor.getReference());

		if (battlecry.getTargetRequirement() != TargetSelection.NONE) {
			List<GameAction> battlecryActions = getTargetedBattlecryGameActions(battlecry, player);

			if (battlecryActions == null
					|| battlecryActions.size() == 0) {
				return;
			}

			NetworkBehaviour networkBehaviour = (NetworkBehaviour) player.getBehaviour();
			networkBehaviour.requestActionAsync((ServerGameContext) context, player, battlecryActions, action -> {
				performBattlecryAction(playerId, actor, player, action);
				result.handle(action);
			});
		} else {
			performBattlecryAction(playerId, actor, player, battlecry);
			result.handle(battlecry);
		}
	}

//	@Override
//	public void equipWeapon(int playerId, Weapon weapon) throws UnsupportedOperationException {
//		throw new UnsupportedOperationException("AsyncGameLogic::equipWeapon is unsupported. Use AsyncGameLogic::equipWeaponAsync instead.");
//	}

	@Override
	public void equipWeaponAsync(int playerId, Weapon weapon, Handler<Weapon> result) {
		PreEquipWeapon preEquipWeapon = new PreEquipWeapon(playerId, weapon).invoke();
		Weapon currentWeapon = preEquipWeapon.getCurrentWeapon();
		Player player = preEquipWeapon.getPlayer();

		if (weapon.getBattlecry() != null) {
			resolveBattlecry(playerId, weapon);
			resolveBattlecryAsync(playerId, weapon, action -> {
				postEquipWeapon(playerId, weapon, currentWeapon, player);
				result.handle(weapon);
			});
		} else {
			postEquipWeapon(playerId, weapon, currentWeapon, player);
			result.handle(weapon);
		}
	}

//	@Override
//	public boolean summon(int playerId, Minion minion, Card source, int index, boolean resolveBattlecry) throws UnsupportedOperationException {
//		throw new UnsupportedOperationException("AsyncGameLogic::summon is unsupported. Use AsyncGameLogic::summonAsync instead.");
//	}

	@Override
	protected void summonAsync(int playerId, Minion minion, Card source, int index, boolean resolveBattlecry, Handler<Boolean> summoned) {
		PreSummon preSummon = new PreSummon(playerId, minion, index).invoke();
		if (preSummon.is()) {
			summoned.handle(false);
			return;
		}

		Player player = preSummon.getPlayer();

		Handler<GameAction> postSummonHandler = battlecry -> {
			if (battlecry != null) {
				checkForDeadEntities();
			}

			postSummon(minion, source, player);
			summoned.handle(true);
		};

		if (resolveBattlecry && minion.getBattlecry() != null) {
			resolveBattlecryAsync(player.getId(), minion, postSummonHandler);
		} else {
			postSummonHandler.handle(null);
		}
	}
}
