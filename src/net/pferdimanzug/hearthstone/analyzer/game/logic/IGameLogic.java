package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;

public interface IGameLogic extends Cloneable {
	
	public boolean canPlayCard(int playerId, CardReference cardReference);
	public void castSpell(int playerId, Spell spell);
	public IGameLogic clone();
	public void damage(Player player, Entity target, int damage, boolean applySpellpower);
	public void destroy(Entity target);
	
	public int determineBeginner(int... playerIds);
	public void drawCard(int playerId);
	
	public void endTurn(int playerId);
	public void equipWeapon(int playerId, Weapon weapon);
	public void changeDurability(Weapon weapon, int durability);
	
	public int getTotalTagValue(Player player, GameTag tag);
	public void fight(Player player, Entity attacker, Entity defender);
	public GameResult getMatchResult(Player player, Player oppenent);
	public List<GameAction> getValidActions(int playerId);
	public List<Entity> getValidTargets(int playerId, GameAction action);
	public void heal(Entity target, int healing);
	public void init(int playerId, boolean begins);
	
	public void modifyCurrentMana(int playerId, int mana);
	
	public void performGameAction(int playerIdId, GameAction action);
	public void playCard(int playerId, CardReference cardReference);
	
	public void receiveCard(int playerId, Card card);
	
	public void setContext(GameContext context);
	public void startTurn(int playerId);
	public void summon(int playerId, Minion minion, Entity nextTo);
	
	public void useHeroPower(int playerId, HeroPower power);
}
