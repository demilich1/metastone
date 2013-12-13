package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public interface IGameLogic {
	
	public Player determineBeginner(Player... players);
	public void init(Player player, boolean begins);
	public void drawCard(Player player);
	public void performGameAction(GameContext context, Player player, GameAction action);
	
	public boolean canPlayCard(Player player, Card card);
	
	public List<Entity> getValidTargets(GameContext context, Player player, GameAction action);
	
	public void useHeroPower(GameContext context, Player player, HeroPower power);
	public void playCard(GameContext context, Player player, Card card);
	public void damage(Entity target, int damage);
	public void heal(Entity target, int healing);
	public void destroy(Entity target);
	
	public void modifyCurrentMana(Player player, int mana);
	
	public void summon(Player player, Minion minion, Entity nextTo);
	public void fight(Entity attacker, Entity defender);
	
	public GameResult getMatchResult(Player player, Player oppenent);
	
	public void startTurn(Player player);
	public void endTurn(Player player);
}
