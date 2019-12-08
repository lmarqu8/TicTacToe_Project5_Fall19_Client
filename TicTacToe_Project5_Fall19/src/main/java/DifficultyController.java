import javafx.application.Platform;
import javafx.fxml.FXML;

public class DifficultyController {


    @FXML
    void handleCloseButton() {
        Platform.exit();
        System.exit(0);
    }
}
