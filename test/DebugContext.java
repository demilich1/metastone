import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;


public class DebugContext extends GameContext {

	public DebugContext(Player player1, Player player2, GameLogic logic) {
		super(player1, player2, logic);
	}
	
	public void setActivePlayer(Player player) {
		this.activePlayer = player;
	}

}
