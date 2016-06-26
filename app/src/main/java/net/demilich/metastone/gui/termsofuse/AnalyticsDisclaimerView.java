package net.demilich.metastone.gui.termsofuse;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.analytics.MetastoneAnalytics;
import net.demilich.metastone.utils.MetastoneProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AnalyticsDisclaimerView extends BorderPane {

    private final String ANALYTICS_DISCLAIMER_TEXT = "analytics_disclaimer.txt";
    @FXML
    protected TextArea disclaimerTextArea;

    @FXML
    protected CheckBox optOutCheckbox;

	@FXML
    protected Button backButton;

    public AnalyticsDisclaimerView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AnalyticsDiscalimer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(ANALYTICS_DISCLAIMER_TEXT).toURI())));
            disclaimerTextArea.setText(content);

            if (MetastoneProperties.hasProperty(MetastoneAnalytics.ANALYTICS_OPT_OUT_PROPERTY)) {
                optOutCheckbox.setSelected(MetastoneProperties.getBoolean(MetastoneAnalytics.ANALYTICS_OPT_OUT_PROPERTY));
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        optOutCheckbox.setOnAction(event -> {
            if (event.getSource() == optOutCheckbox) {
                try {
                    MetastoneProperties.setBoolean(MetastoneAnalytics.ANALYTICS_OPT_OUT_PROPERTY, optOutCheckbox.isSelected());
                    ApplicationFacade.getInstance().sendNotification(GameNotification.ANALYTICS_OPT_OUT_TOGGLED, optOutCheckbox.isSelected());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        backButton.setOnAction(event -> {
            if (event.getSource() == backButton) {
                NotificationProxy.sendNotification(GameNotification.MAIN_MENU);
            }
        });
    }

}
