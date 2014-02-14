package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javafx.scene.image.Image;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;

public class IconFactory {

	private static final String RESOURCE_PATH = "/net/pferdimanzug/hearthstone/analyzer/resources";
	
	private IconFactory() {
	}

	public static JComponent createHandCard(Card card, Player player) {
		JPanel cardPanel = new JPanel(new BorderLayout());
		cardPanel.setPreferredSize(new Dimension(100, 110));
		JTextArea nameArea = new JTextArea(card.getName());
		nameArea.setLineWrap(true);
		nameArea.setWrapStyleWord(true);
		nameArea.setEditable(false);
		nameArea.setFont(new Font("Arial", Font.BOLD, 12));
		cardPanel.add(nameArea, BorderLayout.CENTER);
		JLabel costLabel = new JLabel(card.getManaCost(player) + "");
		costLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		cardPanel.add(costLabel, BorderLayout.NORTH);

		cardPanel.setBorder(createRarityBorder(card.getRarity()));
		return cardPanel;
	}

	public static Border createRarityBorder(Rarity rarity) {
		Color color;
		int thickness = 2;
		switch (rarity) {
		case COMMON:
			color = Color.WHITE;
			break;
		case EPIC:
			// a335ee
			color = new Color(163, 53, 238);
			break;
		case LEGENDARY:
			// ff8000
			color = new Color(255, 128, 0);
			thickness = 3;
			break;
		case RARE:
			// 0070dd
			color = new Color(0, 112, 221);
			break;
		default:
			color = Color.GRAY;
			break;
		}
		return BorderFactory.createLineBorder(color, thickness);

	}

	public static String getHeroIconUrl(Hero hero) {
		String iconPath = RESOURCE_PATH + "/img/heroes/";
		switch (hero.getHeroClass()) {
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
			break;

		}
		return iconPath + ".png";
	}

	public static String getHeroPowerIconUrl(HeroPower heroPower) {
		String iconPath = RESOURCE_PATH + "/img/powers/";
		switch (heroPower.getClassRestriction()) {
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
			break;
	
		}
		iconPath += ".png";
		return iconPath;
	}
	
	public static Image getTargetIcon() {
		String iconPath = RESOURCE_PATH + "/img/common/target.png";
		return new Image(iconPath);
	}
	
	public static Image getSummonHelper() {
		String iconPath = RESOURCE_PATH + "/img/common/arrow_down_blue.png";
		return new Image(iconPath);
	}
}