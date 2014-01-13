import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;

import org.testng.annotations.Test;


public class CloningTest extends TestBase {
	
	
	@Test
	public void testCloning() { 
		GameContext context = createContext(new Jaina(), new Garrosh());
		GameContext clone = context.clone();
	}
}
