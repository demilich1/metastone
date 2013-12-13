package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class PlayModePanel extends JPanel {
	private JLabel p1HpLabel;
	private JLabel p1HeroLabel;
	private JLabel p1ManaLabel;
	private JPanel p1HandCardPanel;
	private JPanel p2HandCardPanel;
	private JLabel p2HpLabel;
	private JLabel p2HeroLabel;
	private JLabel p2ManaLabel;

	/**
	 * Create the panel.
	 */
	public PlayModePanel() {
		setLayout(new BorderLayout(0, 0));
		setSize(1024, 768);
		
		JPanel p1Panel = new JPanel();
		add(p1Panel, BorderLayout.SOUTH);
		p1Panel.setLayout(new MigLayout("", "[100%,grow,center,nogrid]", "[100!][80!]"));
		
		p1HpLabel = new JLabel("Hp: 30/30");
		p1Panel.add(p1HpLabel, "flowx,cell 0 0");
		
		p1HeroLabel = new JLabel("");
		p1HeroLabel.setIcon(new ImageIcon(PlayModePanel.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/heroes/jaina.png")));
		p1Panel.add(p1HeroLabel, "cell 0 0");
		
		p1ManaLabel = new JLabel("Mana: 10/10");
		p1Panel.add(p1ManaLabel, "cell 0 0");
		
		p1HandCardPanel = new JPanel();
		p1HandCardPanel.setBorder(null);
		p1Panel.add(p1HandCardPanel, "cell 0 1");
		p1HandCardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel p2Panel = new JPanel();
		add(p2Panel, BorderLayout.NORTH);
		p2Panel.setLayout(new MigLayout("", "[100%,grow,center,nogrid]", "[80!][100!]"));
		
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
	}
	
	public void update(GameContext context) {
		updatePlayerStatus(p1HpLabel, p1ManaLabel, context.getPlayer1());
		updatePlayerStatus(p2HpLabel, p2ManaLabel, context.getPlayer2());
		updateHandCardView(p1HandCardPanel, context.getPlayer1());
		updateHandCardView(p2HandCardPanel, context.getPlayer2());
		revalidate();
	}
	
	private void updateHandCardView(JPanel panel, Player player) {
		panel.removeAll();
		for (Card card : player.getHand()) {
			panel.add(createHandCard(card));
		}
	}
	
	private void updatePlayerStatus(JLabel hpLabel, JLabel manaLabel, Player player) {
		hpLabel.setText("Hp: " + player.getHero().getHp() + "/" + player.getHero().getMaxHp());
		manaLabel.setText("Mana: " + player.getMana() + "/" + player.getMaxMana());
	}
	
	private JComponent createHandCard(Card card) {
		JButton cardButton = new JButton(card.getName());
		return cardButton;
	}
}
