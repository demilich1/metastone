package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

public interface IGameLogic {
	
	public boolean canPlayCard(int playerId, Card card);
	public void castSpell(int playerId, Spell spell);
	public void damage(Entity target, int damage);
	public void destroy(Entity target);
	public int determineBeginner(int... playerIds);
	
	public void drawCard(int playerId);
	public void equipWeapon(int playerId, Weapon weapon);
	
	public void endTurn(int playerId);
	public void fight(Entity attacker, Entity defender);
	
	public GameResult getMatchResult(Player player, Player oppenent);
	public List<GameAction> getValidActions(int playerId);
	public List<Entity> getValidTargets(int playerId, GameAction action);
	public void heal(Entity target, int healing);
	public void init(int playerId, boolean begins);
	public void modifyCurrentMana(int playerId, int mana);
	
	public void performGameAction(int playerIdId, GameAction action);
	
	public void playCard(int playerId, Card card);
	public void receiveCard(int playerId, Card card);
	
	public void startTurn(int playerId);
	
	public void setContext(GameContext context);
	public void summon(int playerId, Minion minion, Entity nextTo);
	public void useHeroPower(int playerId, HeroPower power);
}
