import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.AuraSpellBuff;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;


public class TestAura extends Aura {

	public TestAura() {
		super(new AuraSpellBuff(1), new AuraSpellBuff(-1), EntityReference.FRIENDLY_MINIONS);
	}
	
	@Override
	protected boolean affects(GameContext context, Actor target) {
		if (!super.affects(context, target)) {
			
			return false;
		}
		return target.getOwner() == getOwner();
	}

}
