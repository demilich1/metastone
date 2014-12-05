import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;


public class DebugContext extends GameContext {

	public DebugContext(Player player1, Player player2, GameLogic logic) {
		super(player1, player2, logic);
	}
	
	@Override
	public void init() {
		activePlayer = getPlayer(PLAYER_1).getId();
		getLogic().init(activePlayer, true);
		getLogic().init(getOpponent(getActivePlayer()).getId(), false);
	}
	
	public void setActivePlayer(int playerId) {
		this.activePlayer = playerId;
	}

}
