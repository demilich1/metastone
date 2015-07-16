package net.demilich.metastone.tests;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;


public class TestMinionCard extends MinionCard {
	
	private static int id;

	private final Minion minion;

	public TestMinionCard(int baseAttack, int baseHp, GameTag... tags) {
		super("Test monster " + ++id, baseAttack, baseHp, Rarity.COMMON, HeroClass.ANY, 1);
		setCollectible(false);
		
		this.minion = createMinion();
		for(GameTag attribute : tags) {
			minion.setTag(attribute);
		}
	}
	
	public TestMinionCard(int baseAttack, int baseHp, int manaCost) {
		super("Test monster " + ++id, baseAttack, baseHp, Rarity.COMMON, HeroClass.ANY, manaCost);
		setCollectible(false);
		
		this.minion = createMinion();
	}

	public Actor getMinion() {
		return minion;
	}

	@Override
	public Minion summon() {
		return minion.clone();
	}
	
	@Override
	public String getCardId() {
		return "minion_test";
	}

}
