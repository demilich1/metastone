package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class PlayModePanel extends JPanel {
	private JLabel p1HpLabel;
	private JLabel p1HeroLabel;
	private JLabel p1ManaLabel;
	private JPanel p1HandCardPanel;
	private JPanel p2HandCardPanel;
	private JLabel p2HpLabel;
	private JLabel p2HeroLabel;
	private JLabel p2ManaLabel;
	private JPanel p1MinionPanel;
	private JPanel p2MinionPanel;

	/**
	 * Create the panel.
	 */
	public PlayModePanel() {
		setLayout(new BorderLayout(0, 0));
		setSize(1024, 768);
		
		JPanel p1Panel = new JPanel();
		add(p1Panel, BorderLayout.SOUTH);
		p1Panel.setLayout(new MigLayout("", "[100%,grow,center,nogrid]", "[160!,grow][100!][80!]"));
		
		p1MinionPanel = new JPanel();
		p1Panel.add(p1MinionPanel, "cell 0 0,grow");
		
		p1HpLabel = new JLabel("Hp: 30/30");
		p1Panel.add(p1HpLabel, "flowx,cell 0 1");
		
		p1HeroLabel = new JLabel("");
		p1HeroLabel.setIcon(new ImageIcon(PlayModePanel.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/heroes/jaina.png")));
		p1Panel.add(p1HeroLabel, "cell 0 1");
		
		p1ManaLabel = new JLabel("Mana: 10/10");
		p1Panel.add(p1ManaLabel, "cell 0 1");
		
		p1HandCardPanel = new JPanel();
		p1HandCardPanel.setBorder(null);
		p1Panel.add(p1HandCardPanel, "cell 0 2,grow");
		p1HandCardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel p2Panel = new JPanel();
		add(p2Panel, BorderLayout.NORTH);
		p2Panel.setLayout(new MigLayout("", "[100%,grow,center,nogrid]", "[80!][100!][160!,grow]"));
		
		p2HandCardPanel = new JPanel();
		p2HandCardPanel.setBorder(null);
		p2Panel.add(p2HandCardPanel, "cell 0 0,grow");
		
		p2HpLabel = new JLabel("Hp: 30/30");
		p2Panel.add(p2HpLabel, "flowx,cell 0 1");
		
		p2HeroLabel = new JLabel("");
		p2HeroLabel.setIcon(new ImageIcon(PlayModePanel.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/heroes/rexxar.png")));
		p2Panel.add(p2HeroLabel, "cell 0 1");
		
		p2ManaLabel = new JLabel("Mana: 10/10");
		p2Panel.add(p2ManaLabel, "cell 0 1");
		
		p2MinionPanel = new JPanel();
		p2Panel.add(p2MinionPanel, "cell 0 2,grow");
	}
	
	private JComponent createHandCard(Card card) {
		JButton cardButton = new JButton(card.getName());
		return cardButton;
	}
	
	private JPanel createMinionCard(Minion minion) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel nameLabel = new JLabel(minion.getName());
		panel.add(nameLabel, BorderLayout.CENTER);
		JPanel bottomPanel = new JPanel(new FlowLayout());
		JLabel attackLabel = new JLabel(minion.getAttack() + "");
		attackLabel.setOpaque(true);
		attackLabel.setBackground(Color.YELLOW);
		JLabel hpLabel = new JLabel(minion.getHp() + "");
		hpLabel.setOpaque(true);
		hpLabel.setBackground(Color.RED);
		bottomPanel.add(attackLabel);
		bottomPanel.add(hpLabel);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		return panel;
	}
	
	private Icon getIcon(Hero hero) {
		String iconName = null;
		switch (hero.getHeroClass()) {
		case DRUID:
			iconName = "malfurion";
			break;
		case HUNTER:
			iconName = "rexxar";
			break;
		case MAGE:
			iconName = "jaina";
			break;
		case PALADIN:
			iconName = "paladin";
			break;
		case PRIEST:
			iconName = "anduin";
			break;
		case ROGUE:
			iconName = "valeera";
			break;
		case SHAMAN:
			iconName = "thrall";
			break;
		case WARLOCK:
			iconName = "guldan";
			break;
		case WARRIOR:
			iconName = "garrosh";
			break;
		default:
		case ANY:
			break;
		
		}
		return new ImageIcon(PlayModePanel.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/heroes/" + iconName + ".png"));
	}
	
	public void update(GameContext context) {
		updatePlayerStatus(p1HeroLabel, p1HpLabel, p1ManaLabel, context.getPlayer1());
		updatePlayerStatus(p2HeroLabel, p2HpLabel, p2ManaLabel, context.getPlayer2());
		updateHandCardView(p1HandCardPanel, context.getPlayer1());
		updateHandCardView(p2HandCardPanel, context.getPlayer2());
		updateMinionView(p1MinionPanel, context.getPlayer1());
		updateMinionView(p2MinionPanel, context.getPlayer2());
		revalidate();
		repaint();
	}
	
	private void updateHandCardView(JPanel panel, Player player) {
		panel.removeAll();
		for (Card card : player.getHand()) {
			panel.add(createHandCard(card));
		}
	}
	
	private void updateMinionView(JPanel panel, Player player) {
		panel.removeAll();
		for (Minion minion : player.getMinions()) {
			panel.add(createMinionCard(minion));
		}
	}
	
	private void updatePlayerStatus(JLabel iconLabel, JLabel hpLabel, JLabel manaLabel, Player player) {
		iconLabel.setIcon(getIcon(player.getHero()));
		hpLabel.setText("Hp: " + player.getHero().getHp() + "/" + player.getHero().getMaxHp());
		manaLabel.setText("Mana: " + player.getMana() + "/" + player.getMaxMana());
	}
}
