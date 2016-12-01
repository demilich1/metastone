package com.hiddenswitch.proto3.net.common;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.VertxException;
import io.vertx.ext.sync.Sync;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.SummonResult;
import net.demilich.metastone.game.targeting.TargetSelection;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by bberman on 11/23/16.
 */
public class GameLogicAsync extends GameLogic {
	@Override
	protected void mulligan(Player player, boolean begins) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("GameLogicAsync::mulligan is unsupported. Use GameLogicAsync::mulliganAsync instead.");
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
		throw new UnsupportedOperationException("GameLogicAsync::init is unsupported. Use GameLogicAsync::initAsync instead.");
	}

	@Override
	public void initAsync(int playerId, boolean begins, Handler<Player> callback) {
		Player player = context.getPlayer(playerId);

		mulliganAsync(player, begins, o -> {
			startGameForPlayer(player);
			callback.handle(player);
		});
	}

	@Override
	@Suspendable
	protected void resolveBattlecry(int playerId, Actor actor) {
		logger.debug("AsyncDebug {} successfully called resolveBattlecry.", this.context);
		Boolean result = Sync.awaitResult(new BattlecryResult(playerId, actor));
	}

	@Override
	@Suspendable
	protected void resolveBattlecryAsync(int playerId, Actor actor, Handler<AsyncResult<Boolean>> result) {
		BattlecryAction battlecry = actor.getBattlecry();

		Player player = context.getPlayer(playerId);
		if (!battlecry.canBeExecuted(context, player)) {
			if (result != null) {
				result.handle(NullResult.SUCESSS);
			}
			return;
		}

		battlecry.setSource(actor.getReference());

		if (battlecry.getTargetRequirement() != TargetSelection.NONE) {
			List<GameAction> battlecryActions = getTargetedBattlecryGameActions(battlecry, player);

			if (battlecryActions == null
					|| battlecryActions.size() == 0) {
				result.handle(NullResult.SUCESSS);
				return;
			}

			NetworkBehaviour networkBehaviour = (NetworkBehaviour) player.getBehaviour();
			networkBehaviour.requestActionAsync((ServerGameContext) context, player, battlecryActions, action -> {
				performBattlecryAction(playerId, actor, player, action);
				logger.debug("AsyncDebug {} successfully called resolveBattlecryAsync", this.context);
				if (result != null) {
					result.handle(NullResult.SUCESSS);
				}
			});
		} else {
			performBattlecryAction(playerId, actor, player, battlecry);
			if (result != null) {
				result.handle(NullResult.SUCESSS);
			}
		}
	}

	@Override
	@Suspendable
	public void equipWeapon(int playerId, Weapon weapon, boolean resolveBattlecry) {
		logger.debug("AsyncDebug {} successfully called equipWeapon.", this.context);
		if (!resolveBattlecry) {
			super.equipWeapon(playerId, weapon, false);
		} else {
			Boolean result = Sync.awaitResult(new EquipWeaponResult(playerId, weapon, resolveBattlecry));
		}
	}

	@Override
	@Suspendable
	public void equipWeaponAsync(int playerId, Weapon weapon, boolean resolveBattlecry, Handler<AsyncResult<Boolean>> result) {
		logger.debug("AsyncDebug {} successfully called equipWeaponAsync.", this.context);
		PreEquipWeapon preEquipWeapon = new PreEquipWeapon(playerId, weapon).invoke();
		Weapon currentWeapon = preEquipWeapon.getCurrentWeapon();
		Player player = preEquipWeapon.getPlayer();

		if (resolveBattlecry
				&& weapon.getBattlecry() != null) {
			resolveBattlecryAsync(playerId, weapon, action -> {
				postEquipWeapon(playerId, weapon, currentWeapon, player);
				if (result != null) {
					result.handle(NullResult.SUCESSS);
				}
			});
		} else {
			postEquipWeapon(playerId, weapon, currentWeapon, player);
			if (result != null) {
				result.handle(NullResult.SUCESSS);
			}
		}
	}

	@Override
	@Suspendable
	public boolean summon(int playerId, Minion minion, Card source, int index, boolean resolveBattlecry) {
		if (!resolveBattlecry) {
			logger.debug("AsyncDebug {} successfully called regular summon.", this.context.toString());
			return super.summon(playerId, minion, source, index, false);
		}

		Boolean summonResult = Sync.awaitResult(new SummonResult2(playerId, minion, source, index));

		logger.debug("AsyncDebug {} successfully called async summon.", this.context.toString());
		return summonResult;
	}

	@Override
	@Suspendable
	protected void summonAsync(int playerId, Minion minion, Card source, int index, boolean resolveBattlecry, Handler<AsyncResult<Boolean>> summoned) {
		PreSummon preSummon = new PreSummon(playerId, minion, index).invoke();
		if (preSummon.is()) {
			if (summoned != null) {
				summoned.handle(SummonResult.NOT_SUMMONED);
			}
			return;
		}

		Player player = preSummon.getPlayer();

		Handler<AsyncResult<Boolean>> postSummonHandler = result -> {
			checkForDeadEntities();

			postSummon(minion, source, player);
			if (summoned != null) {
				summoned.handle(SummonResult.SUMMONED);
			}
		};

		if (resolveBattlecry && minion.getBattlecry() != null) {
			resolveBattlecryAsync(player.getId(), minion, (o) -> {
				postSummonHandler.handle(SummonResult.SUMMONED);
			});
		} else {
			postSummonHandler.handle(SummonResult.SUMMONED);
		}
	}

	private class EquipWeaponResult implements Consumer<Handler<AsyncResult<Boolean>>> {
		private final int playerId;
		private final Weapon weapon;
		private final boolean resolveBattlecry;

		public EquipWeaponResult(int playerId, Weapon weapon, boolean resolveBattlecry) {
			this.playerId = playerId;
			this.weapon = weapon;
			this.resolveBattlecry = resolveBattlecry;
		}

		@Override
		@Suspendable
		public void accept(Handler<AsyncResult<Boolean>> done) {
			if (done == null) {
				logger.error("A handler was null!");
			}
			GameLogicAsync.this.equipWeaponAsync(playerId, weapon, resolveBattlecry, done);
		}
	}

	private class BattlecryResult implements Consumer<Handler<AsyncResult<Boolean>>> {
		private final int playerId;
		private final Actor actor;

		public BattlecryResult(int playerId, Actor actor) {
			this.playerId = playerId;
			this.actor = actor;
		}

		@Override
		@Suspendable
		public void accept(Handler<AsyncResult<Boolean>> done) {
			if (done == null) {
				logger.error("A handler was null!");
			}
			GameLogicAsync.this.resolveBattlecryAsync(playerId, actor, done);
		}
	}

	private class SummonResult2 implements Consumer<Handler<AsyncResult<Boolean>>> {
		private final int playerId;
		private final Minion minion;
		private final Card source;
		private final int index;

		public SummonResult2(int playerId, Minion minion, Card source, int index) {
			this.playerId = playerId;
			this.minion = minion;
			this.source = source;
			this.index = index;
		}

		@Override
		@Suspendable
		public void accept(Handler<AsyncResult<Boolean>> done) {
			if (done == null) {
				logger.error("A handler was null!");
			}
			GameLogicAsync.this.summonAsync(playerId, minion, source, index, true, done);
		}
	}
}
