import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TicTacToe_Client extends Application {
	//Server serverConnection;
	Client clientConnection;
	String difficulty = new String();
	String portString = new String();
	String ipString = new String();
	int port;
	int ip;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("T^3 start");
		
		/*___________________________________________*/
		BorderPane loginPane = new BorderPane();
		loginPane.setPrefSize(800,800);
		loginPane.setPadding(new Insets(100));
		TextField portText = new TextField("Port #");
		TextField ipText = new TextField("IP Address");
		portText.setPrefSize(50, 10);
		portText.setAlignment(Pos.CENTER);
		portText.setPadding(new Insets(15, 15, 15, 15));
		ipText.setPrefSize(50, 10);
		ipText.setAlignment(Pos.CENTER);
		ipText.setPadding(new Insets(15, 15, 15, 15));
		Button startConnection = new Button("Connect");
		startConnection.setPadding(new Insets(15, 15, 15, 15));
		VBox loginBox = new VBox();
		loginBox.getChildren().addAll(portText, ipText, startConnection);
		loginBox.setAlignment(Pos.CENTER);
		loginBox.setPrefSize(300, 300);
		loginPane.setCenter(loginBox);
		loginPane.setMinSize(600, 600);
		Scene portScene = new Scene(loginPane);
		
		/*__________________________________________*/
		
		BorderPane difficultyPane = new BorderPane();
		difficultyPane.setPadding(new Insets(70));
		//ListView<String> serverList = new ListView<String>();
		
		VBox modeBox = new VBox(5);
		VBox difficultyBox = new VBox(40);
		Button easyButton = new Button("Easy");
		Button medButton = new Button("Medium");
		Button expButton = new Button("Expert");
		Button startButton = new Button("Start");
		easyButton.setPrefSize(150, 150);
		easyButton.setAlignment(Pos.CENTER);
		easyButton.setPadding(new Insets(15, 15, 15, 15));
		medButton.setPrefSize(150, 150);
		medButton.setAlignment(Pos.CENTER);
		medButton.setPadding(new Insets(15, 15, 15, 15));
		expButton.setPrefSize(150, 150);
		expButton.setAlignment(Pos.CENTER);
		expButton.setPadding(new Insets(15, 15, 15, 15));
		
		startButton.setPrefSize(150, 50);
		startButton.setAlignment(Pos.CENTER);
		startButton.setPadding(new Insets(15, 15, 15, 15));
		
		modeBox.getChildren().addAll(easyButton, medButton, expButton);
		modeBox.setAlignment(Pos.CENTER);
		difficultyBox.getChildren().addAll(modeBox, startButton);
		difficultyBox.setAlignment(Pos.CENTER);
		difficultyPane.setCenter(difficultyBox);
		difficultyBox.setPrefSize(300,300);
		difficultyPane.setPrefSize(800,800);
		Scene difficultyScene = new Scene(difficultyPane);
		
		/*_________________________________________*/
		
		BorderPane gamePane = new BorderPane();
		gamePane.setPrefSize(800,800);
		gamePane.setPadding(new Insets(100));
		
		ListView<String> leaderBoard = new ListView<String>();
		Button backToMenu = new Button("Difficulty Selection");
		Button quit = new Button("Quit");
		GridPane gameBoard = new GridPane();
		gameBoard.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		gameBoard.setVgap(10); //vertical gap in pixels
		gameBoard.setPadding(new Insets(10, 10, 10, 10)); //margins around the whole grid
		gameBoard.setGridLinesVisible(true);
		gameBoard.setMinSize(400, 400);
		gameBoard.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		leaderBoard.setMinSize(200, 400);
		
		ArrayList<Button> buttonList = new ArrayList<Button>();
		int col = 0, row = 0;
		for(int i = 0; i < 9; i++) {
			buttonList.add(new Button("  "));
			buttonList.get(i).setPrefSize(150, 150);
			buttonList.get(i).setAlignment(Pos.CENTER);
			buttonList.get(i).setPadding(new Insets(15, 15, 15, 15));
			gameBoard.add(buttonList.get(i), col, row);
			col+=1;
			if(col == 3) {
				row+=1;
				col = 0;
			}// end of if
		}//end of for
		
		
		//leaderBoard.setPrefWidth(gameBoard.getWidth());
		HBox centerBox = new HBox(50);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setMaxHeight(gameBoard.getHeight());
		centerBox.getChildren().addAll(leaderBoard, gameBoard);
		//centerBox.setMinSize(gameBoard.getWidth(), gameBoard.getHeight());
		
		HBox buttonBox = new HBox(30);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(backToMenu, quit);
		
		gamePane.setCenter(centerBox);
		gamePane.setBottom(buttonBox);
		
		Scene gameScene = new Scene(gamePane);
		
		
		
		
		/*_________________________________________*/
		
		//Client connects to server action //TODO 
		startConnection.setOnAction(e-> {
		
		portString = portText.getText();
		ipString = ipText.getText();
		if(isNumeric(portString)) {
			port = Integer.parseInt(portString);
			clientConnection = new Client(data->{
				Platform.runLater(()->{leaderBoard.getItems().add(data.toString());});}, ipString, port);
					clientConnection.start();
			primaryStage.setScene(difficultyScene);
			primaryStage.setTitle("T^3 Select Difficulty");
		}
		
		});
		
		
		//Set Difficulties
		easyButton.setOnAction(e->{ difficulty = "easy";});//end of easyButton
		medButton.setOnAction(e->{ difficulty = "medium";});//end of medButton
		expButton.setOnAction(e->{ difficulty = "expert";});//end of expButton
		
		
		//Start GameScene
		startButton.setOnAction(e->{ primaryStage.setScene(gameScene);
		//TODO add start game
		});//end of startButton
		
		
		/*___________________________________________*/
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		primaryStage.setScene(portScene);
		primaryStage.show();
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Integer d = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

}
