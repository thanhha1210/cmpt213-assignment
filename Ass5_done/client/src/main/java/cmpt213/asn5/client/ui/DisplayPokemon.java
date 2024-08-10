package cmpt213.asn5.client.ui;

/**
 * UI class for displaying and editing the details of a specific Pokemon.
 * Provides input fields for modifying Pokemon attributes and buttons for saving or deleting the Pokemon.
 * @Author Irene Luu
 * @version 01
 */
import cmpt213.asn5.client.models.Pokemon;

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
import javafx.stage.Stage;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class DisplayPokemon {

    private Pokemon pokemon;
    private Stage stage;
    private Text title;

    private Label id;
    private Label name;
    private Label type;
    private Label rarity;
    private Label hp;
    private Label img;
    private Label previewImage;

    private Text idInput;
    private TextField nameInput;
    private ComboBox<String> typeInput;
    private Slider rarityInput;
    private TextField hpInput;
    private ComboBox<String> imgInput;
    private ImageView pokemonImage;

    private Text message;
    private final Button saveBtn;
    private final Button deleteBtn;

    public DisplayPokemon(Pokemon pokemon, Stage stage) {
        this.pokemon = pokemon;
        this.stage = stage;

        title = new Text("Display Pokemon");
        title.setFont(new Font("Arial", 30));
        title.setStyle("-fx-font-weight: bold");

        id = new Label("ID") ;
        name = new Label("Name");
        type = new Label("Type");
        rarity = new Label("Rarity");
        hp = new Label("HP");
        img = new Label("Image");
        previewImage = new Label("Preview Image: ");
        previewImage.setPrefWidth(200);

        idInput = new Text(pokemon.getId() + "");
        idInput.setWrappingWidth(150);
        nameInput = new TextField(pokemon.getName());

        typeInput = new ComboBox<>();
        typeInput.getItems().addAll(
                "Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting",
                "Fire", "Flying", "Ghost", "Grass", "Ice", "Normal",
                "Poison", "Psychic", "Rock", "Steel", "Water"
        );
        typeInput.getSelectionModel().select(pokemon.getType());

        rarityInput = new Slider(0, 10, pokemon.getRarity());
        rarityInput.setShowTickLabels(true);
        rarityInput.setMajorTickUnit(1);
        rarityInput.setBlockIncrement(1);
        rarityInput.setOnMouseReleased(e -> {
            double value = rarityInput.getValue();
            rarityInput.setValue(Math.round(value));
        });

        hpInput = new TextField(pokemon.getHp() + "");
        hpInput.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,4}")) {
                hpInput.setText(s);
            }
        }));

        imgInput = new ComboBox<>();
        imgInput.getItems().addAll(
                "pokemon_ball.png", "chicken.png", "fire_dragon.png", "munchlax.png", "psy_duck.png",
                "vaporeon.png", "pikachu.png", "sandslash.png", "turtle.png"
        );
        String imageURL = pokemon.getImage();
        String imgPath = imageURL.substring(imageURL.lastIndexOf("/") + 1);
        imgInput.getSelectionModel().select(imgPath);
        imgInput.setOnAction(event -> handlePreviewImage());
        pokemonImage = new ImageView("http://localhost:8080/img/" + imgPath);
        pokemonImage.setFitHeight(100);
        pokemonImage.setPreserveRatio(true);

        saveBtn = new Button("Save");
        saveBtn.setOnAction(event -> handleSaveChange());
        deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(event -> handleDelete());

        message = new Text("");
    }

    public VBox createScene() {
        VBox root = new VBox(20,
                createHBox(title),
                createHBox(id, idInput),
                createHBox(name, nameInput),
                createHBox(type, typeInput),
                createHBox(rarity, rarityInput),
                createHBox(hp, hpInput),
                createHBox(img, imgInput),
                createHBox(previewImage, pokemonImage),
                createHBox(saveBtn, deleteBtn),
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

    private void handleSaveChange() {
        if (checkValidInput()) {
            String query = idInput.getText();
            try {
                URI uri = new URI("http://localhost:8080/api/tokimon/edit/" + Long.parseLong(query));
                URL url = uri.toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                String name = nameInput.getText();
                String type = typeInput.getSelectionModel().getSelectedItem();
                String rarity = String.valueOf(rarityInput.getValue());
                String hp = hpInput.getText();
                String img = "http://localhost:8080/img/" + imgInput.getSelectionModel().getSelectedItem();

                String jsonInputString = String.format(
                        "{\"id\": \"%s\", \"name\": \"%s\", \"type\": \"%s\", \"rarity\": %s, \"hp\": \"%s\", \"image\": \"%s\"}",
                        query, name, type, rarity, hp, img
                );

                try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream())) {
                    wr.write(jsonInputString);
                    wr.flush();
                }

                int responseCode = connection.getResponseCode();
                System.out.println("Sending JSON: " + jsonInputString);
                System.out.println(responseCode);  // Should be 200 if successful
                connection.disconnect();

                message.setText("Saved the pokemon successfully");

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            message.setText("Error: Please enter a valid form");
        }
    }

    private void handleDelete() {
        String query = idInput.getText();
        try {
            URI uri = new URI("http://localhost:8080/api/tokimon/" + Long.parseLong(query));
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);  // Should be 204 if successful
            connection.disconnect();

            stage.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handlePreviewImage() {
        String imageURL = "http://localhost:8080/img/" + imgInput.getSelectionModel().getSelectedItem();
        pokemonImage.setImage(new Image(imageURL));
    }
}
