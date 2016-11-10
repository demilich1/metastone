package net.demilich.metastone.gui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.gui.dialog.DialogType;

public class IconFactory {

	//public static final String RESOURCE_PATH = "/net/demilich/metastone/resources";
	public static final String RESOURCE_PATH = "";

	public static Image getClassIcon(HeroClass heroClass) {
		String iconPath = RESOURCE_PATH + "/img/classes/";
		iconPath += heroClass.toString().toLowerCase();
		iconPath += ".png";
		return new Image(iconPath);
	}

	public static Image getDefaultCardBack() {
		String iconPath = RESOURCE_PATH + "/img/common/card_back_default.png";
		return new Image(iconPath);
	}

	public static Image getDialogIcon(DialogType dialogType) {
		String iconPath = RESOURCE_PATH + "/img/ui/";
		switch (dialogType) {
		case CONFIRM:
			iconPath += "confirm.png";
			break;
		case ERROR:
			iconPath += "error.png";
			break;
		case INFO:
			iconPath += "info.png";
			break;
		case WARNING:
			iconPath += "warning.png";
			break;
		default:
			break;

		}
		return new Image(iconPath);
	}

	public static String getHeroIconUrl(HeroClass heroClass) {
		String iconPath = RESOURCE_PATH + "/img/heroes/";
		switch (heroClass) {
		case DRUID:
			iconPath += "malfurion";
			break;
		case HUNTER:
			iconPath += "rexxar";
			break;
		case MAGE:
			iconPath += "jaina";
			break;
		case PALADIN:
			iconPath += "uther";
			break;
		case PRIEST:
			iconPath += "anduin";
			break;
		case ROGUE:
			iconPath += "valeera";
			break;
		case SHAMAN:
			iconPath += "thrall";
			break;
		case WARLOCK:
			iconPath += "guldan";
			break;
		case WARRIOR:
			iconPath += "garrosh";
			break;
		default:
		case ANY:
			iconPath += "unknown";
			break;

		}
		return iconPath + ".png";
	}

	public static String getHeroPowerIconUrl(HeroPower heroPower) {
		String iconPath = RESOURCE_PATH + "/img/powers/";
		switch (heroPower.getHeroClass()) {
		case DRUID:
			iconPath += "shapeshift";
			break;
		case HUNTER:
			iconPath += "steady_shot";
			break;
		case MAGE:
			iconPath += "fireblast";
			break;
		case PALADIN:
			iconPath += "reinforce";
			break;
		case PRIEST:
			iconPath += "lesser_heal";
			break;
		case ROGUE:
			iconPath += "dagger_mastery";
			break;
		case SHAMAN:
			iconPath += "totemic_call";
			break;
		case WARLOCK:
			iconPath += "life_tap";
			break;
		case WARRIOR:
			iconPath += "armor_up";
			break;
		default:
			iconPath += "unknown";
			break;

		}
		iconPath += ".png";
		return iconPath;
	}

	public static String getImageUrl(String imageName) {
		//System.out.println(new File("").getAbsolutePath());
		return RESOURCE_PATH + "/img/" + imageName;
	}

	public static Color getRarityColor(Rarity rarity) {
		Color color = Color.BLACK;
		switch (rarity) {
		case COMMON:
			color = Color.WHITE;
			break;
		case EPIC:
			// a335ee
			color = Color.rgb(163, 53, 238);
			break;
		case LEGENDARY:
			// ff8000
			color = Color.rgb(255, 128, 0);
			break;
		case RARE:
			// 0070dd
			color = Color.rgb(0, 112, 221);
			break;
		default:
			color = Color.GRAY;
			break;
		}
		return color;

	}

	public static Image getSummonHelper() {
		String iconPath = RESOURCE_PATH + "/img/common/arrow_down_blue.png";
		return new Image(iconPath);
	}

	public static Image getTargetIcon() {
		String iconPath = RESOURCE_PATH + "/img/common/target.png";
		return new Image(iconPath);
	}

	private IconFactory() {
	}
}