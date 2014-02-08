import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;


public class DevMonster extends MinionCard {

	private final Minion minion;

	public DevMonster(int baseAttack, int baseHp, GameTag... tags) {
		super("Dev monster", baseAttack, baseHp, Rarity.COMMON, HeroClass.ANY, 1);
		this.minion = createMinion(tags);
	}

	public Minion getMinion() {
		return minion;
	}

	@Override
	public Minion summon() {
		return minion;
	}

}
