import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;


public class TestMinionCard extends MinionCard {

	private final Minion minion;

	public TestMinionCard(int baseAttack, int baseHp, GameTag... tags) {
		super("Test monster", baseAttack, baseHp, Rarity.COMMON, HeroClass.ANY, 1);
		setCollectible(false);
		
		this.minion = createMinion(tags);
	}
	
	public TestMinionCard(int baseAttack, int baseHp, int manaCost) {
		super("Test monster", baseAttack, baseHp, Rarity.COMMON, HeroClass.ANY, manaCost);
		setCollectible(false);
		
		this.minion = createMinion();
	}

	public Minion getMinion() {
		return minion;
	}

	@Override
	public Minion summon() {
		return minion;
	}

}
