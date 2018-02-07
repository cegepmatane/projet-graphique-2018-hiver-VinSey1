package vue;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class FenetreActions extends Application{
	
	private Text titre;
	private Label explications = new Label();
	private BorderPane miseEnPage = new BorderPane();
	private AnchorPane zoneChoix;
	private Button valider;

	@Override
    public void start(Stage primaryStage) {
		miseEnPage.setTop(explications);
		miseEnPage.setCenter(zoneChoix);
	}
	
	abstract int[] choixJoueur();

}