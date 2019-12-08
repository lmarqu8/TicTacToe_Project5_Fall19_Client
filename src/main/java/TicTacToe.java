/*import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TicTacToe extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Let's Play Tic Tac Toe!!!");

		FXMLLoader serverLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
		Parent root = serverLoader.load();

		Scene scene = new Scene(root,700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
	}

}*/

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class TicTacToe extends Application {
    //Server serverConnection;
    Client clientConnection;
    private GameInfo gameInfo;

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
        loginPane.setPrefSize(800, 800);
        loginPane.setPadding(new Insets(100));
        Label gameTitle = new Label("TicTacToe Client");
        TextField portText = new TextField("Port #");
        TextField ipText = new TextField("IP Address");
        gameTitle.setFont(new Font(40));
        portText.setPrefSize(50, 10);
        portText.setAlignment(Pos.CENTER);
        portText.setPadding(new Insets(15, 15, 15, 15));
        ipText.setPrefSize(50, 10);
        ipText.setAlignment(Pos.CENTER);
        ipText.setPadding(new Insets(15, 15, 15, 15));
        Button startConnection = new Button("Connect");
        startConnection.setPadding(new Insets(15, 15, 15, 15));
        VBox loginBox = new VBox();
        loginBox.getChildren().addAll(gameTitle, ipText, portText, startConnection);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPrefSize(200, 200);
        loginBox.setSpacing(10);
        loginPane.setCenter(loginBox);
        loginPane.setMinSize(600, 600);
        Scene portScene = new Scene(loginPane);

        /*__________________________________________*/

        BorderPane difficultyPane = new BorderPane();
        difficultyPane.setPadding(new Insets(70));
        //ListView<String> serverList = new ListView<String>();

        VBox modeBox = new VBox(5);
        VBox difficultyBox = new VBox(40);
        //Button easyButton = new Button("Easy");
        //Button medButton = new Button("Medium");
        //Button expButton = new Button("Expert");
        Label chooseDifficulty = new Label("Choose your desire level!!!");
        ChoiceBox<String> difficulties = new ChoiceBox<>();
        Button startButton = new Button("Start");
		/*easyButton.setPrefSize(150, 150);
		easyButton.setAlignment(Pos.CENTER);
		easyButton.setPadding(new Insets(15, 15, 15, 15));
		medButton.setPrefSize(150, 150);
		medButton.setAlignment(Pos.CENTER);
		medButton.setPadding(new Insets(15, 15, 15, 15));
		expButton.setPrefSize(150, 150);
		expButton.setAlignment(Pos.CENTER);
		expButton.setPadding(new Insets(15, 15, 15, 15));*/

        difficulties.getItems().add("Easy");
        difficulties.getItems().add("Medium");
        difficulties.getItems().add("Expert");
        difficulties.setValue("Easy");

        difficulties.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            int opt = newValue.intValue();
            if (opt == 0) {
                this.gameInfo.setDifficulty(0);
            } else if (opt == 1) {
                this.gameInfo.setDifficulty(1);
            } else {
                this.gameInfo.setDifficulty(2);
            }
        }));

        chooseDifficulty.setFont(new Font(30));

        startButton.setPrefSize(80, 20);
        startButton.setAlignment(Pos.CENTER);
        startButton.setPadding(new Insets(15, 15, 15, 15));

        //modeBox.getChildren().addAll(easyButton, medButton, expButton);
        modeBox.getChildren().addAll(chooseDifficulty, difficulties, startButton);
        modeBox.setAlignment(Pos.CENTER);
        modeBox.setSpacing(20);
        difficultyBox.getChildren().addAll(modeBox);
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyPane.setCenter(difficultyBox);
        difficultyBox.setPrefSize(300, 300);
        difficultyPane.setPrefSize(800, 800);
        Scene difficultyScene = new Scene(difficultyPane);

        /*_________________________________________*/

        BorderPane gamePane = new BorderPane();
        gamePane.setPrefSize(800, 800);
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

        ArrayList<Button> buttonList = new ArrayList<>();
        int col = 0, row = 0;
        for (int i = 0; i < 9; i++) {
            buttonList.add(new Button("  "));
            buttonList.get(i).setId("pos" + i);
            buttonList.get(i).setPrefSize(150, 150);
            buttonList.get(i).setAlignment(Pos.CENTER);
            buttonList.get(i).setPadding(new Insets(15, 15, 15, 15));
            gameBoard.add(buttonList.get(i), col, row);
            col += 1;
            if (col == 3) {
                row += 1;
                col = 0;
            }// end of
        }//end of for

        buttonList.forEach(button -> {
            button.setOnAction(event -> {
                button.setDisable(true);
                ImageView player = new ImageView(new Image("/nought.png", 70, 70, true, false));
                button.setGraphic(player);
                int index = Integer.parseInt(button.getId().substring(3));
                gameInfo.setPlayerMove(index);
                try {
                    clientConnection.out.writeObject(gameInfo);
                    System.out.print("sent player move");
                    this.gameInfo = (GameInfo) clientConnection.in.readObject();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });


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

        //Client connects to server action
        startConnection.setOnAction(e -> {
            primaryStage.setScene(difficultyScene);
            primaryStage.setTitle("This is a client");
            clientConnection = new Client("127.0.0.1", 5555);
            clientConnection.start();
            this.gameInfo = new GameInfo();
        });


        //TODO: Set Difficulties

        //Start GameScene
        startButton.setOnAction(e -> {
            primaryStage.setScene(gameScene);
            //TODO add start game
            try {
                clientConnection.out.writeObject(this.gameInfo);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });//end of startButton


        /*___________________________________________*/

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        quit.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        backToMenu.setOnAction(event -> {
            primaryStage.setScene(difficultyScene);
        });

        primaryStage.setScene(portScene);
        primaryStage.show();
    }

}
