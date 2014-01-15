package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

public interface IGameLogic {
	
	public boolean canPlayCard(Player player, Card card);
	public void castSpell(Player player, Spell spell);
	public void damage(Entity target, int damage);
	public void destroy(Entity target);
	public Player determineBeginner(Player... players);
	
	public void drawCard(Player player);
	public void equipWeapon(Player player, Hero hero, Weapon weapon);
	
	public void endTurn(Player player);
	public void fight(Entity attacker, Entity defender);
	
	public GameResult getMatchResult(Player player, Player oppenent);
	public List<GameAction> getValidActions(Player player);
	public List<Entity> getValidTargets(Player player, GameAction action);
	public void heal(Entity target, int healing);
	public void init(Player player, boolean begins);
	public void modifyCurrentMana(Player player, int mana);
	
	public void performGameAction(Player player, GameAction action);
	
	public void playCard(Player player, Card card);
	public void receiveCard(Player player, Card card);
	
	public void startTurn(Player player);
	
	public void setContext(GameContext context);
	public void summon(Player player, Minion minion, Entity nextTo);
	public void useHeroPower(Player player, HeroPower power);
}
