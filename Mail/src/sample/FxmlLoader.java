package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Paths;

public class FxmlLoader {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static Stage mainStage;

    public static void setMainStage(Stage mainStage) {
        FxmlLoader.mainStage = mainStage;
        FxmlLoader.mainStage.setResizable(true);
        FxmlLoader.mainStage.setTitle("Fenchitter");
        FxmlLoader.mainStage.initStyle(StageStyle.DECORATED);
        FxmlLoader.mainStage.getIcons().add(new Image(Paths.get("./src/sample/Resources/email-hd-png-hd-painting-64.png").toUri().toString()));
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void load(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        FxmlLoader.mainStage.setScene(new Scene(root,WIDTH,HEIGHT));
        FxmlLoader.mainStage.show();
    }
    public void load(String url,Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
        fxmlLoader.setController(controller);
        fxmlLoader.load();
    }
}
