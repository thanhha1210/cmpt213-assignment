package cmpt213.asn5.client.ui;

/**
 * UI class for adding a new Pokemon to the system. Provides input fields for entering Pokemon details
 * such as name, type, rarity, health points, and image. Includes a button to submit the new Pokemon.
 * @Author Irene Luu
 * @version 01
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class AddPokemonUI {

    private Text title;
    private Label name;
    private Label type;
    private Label rarity;
    private Label hp;
    private Label img;
    private Label previewImage;

    private TextField nameInput;
    private ComboBox<String> typeInput;
    private Slider rarityInput;
    private TextField hpInput;
    private ComboBox<String> imgInput;
    private ImageView pokemonImage;
    private Text message;
    private Button addPokemonBtn;

    public AddPokemonUI() {
        title = new Text("Add Pokemon");
        title.setFont(new Font("Arial", 30));
        title.setStyle("-fx-font-weight: bold");

        name = new Label("Name: ");
        nameInput = new TextField();
        nameInput.setPromptText("Enter a pokemon name");

        type = new Label("Type: ");
        typeInput = new ComboBox<>();
        typeInput.getItems().addAll(
                "Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting",
                "Fire", "Flying", "Ghost", "Grass", "Ice", "Normal",
                "Poison", "Psychic", "Rock", "Steel", "Water"
        );
        typeInput.getSelectionModel().selectFirst();

        rarity = new Label("Rarity: ");
        rarityInput = new Slider(0, 10, 5);
        rarityInput.setShowTickLabels(true);
        rarityInput.setMajorTickUnit(1);
        rarityInput.setBlockIncrement(1);
        rarityInput.setOnMouseReleased(e -> {
            double value = rarityInput.getValue();
            rarityInput.setValue(Math.round(value));
        });

        hp = new Label("Health Point: ");
        hpInput = new TextField();
        hpInput.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,4}")) {
                hpInput.setText(s);
            }
        }));
        hpInput.setPromptText("Enter hp from 0 - 9999");

        img = new Label("Image: ");
        imgInput = new ComboBox<>();
        imgInput.getItems().addAll(
                "pokemon_ball.png", "chicken.png", "fire_dragon.png", "munchlax.png", "psy_duck.png",
                "vaporeon.png", "pikachu.png", "sandslash.png", "turtle.png"
        );
        imgInput.getSelectionModel().selectFirst();
        imgInput.setOnAction(event -> handlePreviewImage());

        previewImage = new Label("Preview Image: ");
        previewImage.setPrefWidth(200);
        pokemonImage = new ImageView("http://localhost:8080/img/" + imgInput.getSelectionModel().getSelectedItem());
        pokemonImage.setFitHeight(130);
        pokemonImage.setPreserveRatio(true);

        message = new Text("");
        addPokemonBtn = new Button("Add");
        addPokemonBtn.setOnAction(event -> handleAddEvent());
    }

    public VBox createScene() {
        VBox root = new VBox(20,
                createHBox(title),
                createHBox(name, nameInput),
                createHBox(type, typeInput),
                createHBox(rarity, rarityInput),
                createHBox(hp, hpInput),
                createHBox(img, imgInput),
                createHBox(previewImage, pokemonImage),
                createHBox(addPokemonBtn),
                createHBox(message));
        root.setPadding(new Insets(20));
        return root;
    }

    private HBox createHBox(Node...children) {
        for (Node child : children) {
            if (child instanceof Region) {
                ((Region) child).setMinWidth(150);
            }
        }
        HBox hbox = new HBox(10, children);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private boolean checkValidInput() {
        return !nameInput.getText().isBlank() && !hpInput.getText().isBlank();
    }

    public void handlePreviewImage() {
        String imageURL = "http://localhost:8080/img/" + imgInput.getSelectionModel().getSelectedItem();
        pokemonImage.setImage(new Image(imageURL));
    }

    private void handleAddEvent() {
        if (checkValidInput()) {
            try {
                URI uri = new URI("http://localhost:8080/api/tokimon/add");
                URL url = uri.toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                String name = nameInput.getText();
                String type = typeInput.getSelectionModel().getSelectedItem();
                String rarity = String.valueOf(rarityInput.getValue());
                String hp = hpInput.getText();
                String img = "http://localhost:8080/img/" + imgInput.getSelectionModel().getSelectedItem();

                String jsonInputString = String.format(
                        "{\"name\": \"%s\", \"type\": \"%s\", \"rarity\": %s, \"hp\": \"%s\", \"image\": \"%s\"}",
                        name, type, rarity, hp, img
                );

                try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream())) {
                    wr.write(jsonInputString);
                    wr.flush();
                }

                int responseCode = connection.getResponseCode();
                System.out.println("Sending JSON: " + jsonInputString);
                System.out.println(responseCode);  // Should be 201 if successful
                connection.disconnect();

                message.setText("Added pokemon successfully");

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            message.setText("Error: Please enter a valid form");
        }
    }
}
