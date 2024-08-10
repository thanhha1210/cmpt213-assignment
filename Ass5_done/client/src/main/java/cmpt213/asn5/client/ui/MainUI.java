package cmpt213.asn5.client.ui;

import cmpt213.asn5.client.models.Pokemon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Main UI class for displaying a list of Pokemon cards.
 * Provides functionality to add, search, and filter Pokemon by type.
 * @Author Irene Luu
 * @version 01
 */
public class MainUI {
    private final Text title;
    private final Text typeTitle;
    private final Button addBtn;
    private final ComboBox<String> comboType;
    private final Button searchBtn;
    private final TextField searchInput;
    private final Text message;

    private TableView<Pokemon> table;

    public MainUI() {
        title = new Text("Pokemon Cards");
        title.setFont(new Font("Arial", 30));
        title.setStyle("-fx-font-weight: bold");

        addBtn = new Button("Add Pokemon");
        addBtn.setOnAction(event -> handleAddEvent());

        typeTitle = new Text("Type: ");
        typeTitle.setFont(new Font("Arial", 16));
        typeTitle.setStyle("-fx-font-weight: bold");

        searchBtn = new Button("Search");
        searchBtn.setOnAction(event -> handleSearchEvent());

        searchInput = new TextField();
        searchInput.setPromptText("Enter Pokemon Id");

        message = new Text("");

        comboType = new ComboBox<>();
        comboType.getItems().addAll(
                "None", "All",
                "Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting",
                "Fire", "Flying", "Ghost", "Grass", "Ice", "Normal",
                "Poison", "Psychic", "Rock", "Steel", "Water"
        );
        comboType.getSelectionModel().selectFirst();
        comboType.valueProperty().addListener((observation, oldVal, newVal) -> handleDisplayEvent());

        table = new TableView<>();
        setupTable();
    }

    private void setupTable() {
        TableColumn<Pokemon, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(150);

        TableColumn<Pokemon, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<Pokemon, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(150);

        table.getColumns().addAll(idCol, nameCol, typeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public VBox createScene() {
        HBox hBoxTitle = new HBox(20, title, addBtn);
        hBoxTitle.setAlignment(Pos.CENTER);

        HBox hBoxDisplay = new HBox(20, typeTitle, comboType);
        hBoxDisplay.setAlignment(Pos.CENTER);

        HBox hBoxSearch = new HBox(20, searchInput, searchBtn);
        hBoxSearch.setAlignment(Pos.CENTER);

        HBox hBoxMessage = new HBox(message);
        hBoxMessage.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, hBoxTitle, hBoxSearch, hBoxMessage, hBoxDisplay, table);
        root.setPadding(new Insets(10));
        return root;
    }

    private List<Pokemon> parsePokemonList(String json) {
        Gson gson = new Gson();
        Type pokemonListType = new TypeToken<List<Pokemon>>() {}.getType();
        return gson.fromJson(json, pokemonListType);
    }

    private Pokemon parsePokemon(String json) {
        Gson gson = new Gson();
        Type pokemonType = new TypeToken<Pokemon>() {}.getType();
        return gson.fromJson(json, pokemonType);
    }

    private boolean checkValidInput(String query) {
        try {
            Long.parseLong(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void handleAddEvent() {
        AddPokemonUI addPokemonUI = new AddPokemonUI();
        Stage popUpStage = new Stage();
        Scene popUpScene = new Scene(addPokemonUI.createScene());
        popUpStage.setTitle("Add Pokemon");
        popUpStage.setScene(popUpScene);
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        Window owner = addBtn.getScene().getWindow();
        popUpStage.initOwner(owner);
        popUpStage.setOnHidden(event -> handleDisplayEvent());
        popUpStage.showAndWait();
    }

    private void handleDisplayEvent() {
        table.getItems().clear();
        String selectedType = comboType.getSelectionModel().getSelectedItem();
        String endpoint = selectedType.equals("All") ? "/api/tokimon/all" : "/api/tokimon/get/" + selectedType;

        if (!selectedType.equals("None")) {
            try {
                URI uri = new URI("http://localhost:8080" + endpoint);
                URL url = uri.toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                List<Pokemon> pokemonList = parsePokemonList(response.toString());
                table.getItems().addAll(pokemonList);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSearchEvent() {
        String query = searchInput.getText();
        if (checkValidInput(query)) {
            try {
                long id = Long.parseLong(query);
                URI uri = new URI("http://localhost:8080/api/tokimon/" + id);
                URL url = uri.toURL();

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = br.readLine();
                if (line != null && !line.isEmpty()) {
                    Pokemon pokemon = parsePokemon(line);
                    handleDisplayPokemon(pokemon);
                    message.setText("");
                }
                else {
                    message.setText("Error: No Pokémon found with the given ID.");
                }
                br.close();
                connection.disconnect();
            }
            catch (Exception e) {
                e.printStackTrace();
                message.setText("Error: Unable to retrieve Pokémon. Please try again.");
            }
        }
        else {
            searchInput.setText("");
            message.setText("Error: Please enter a valid ID.");
        }
    }

    private void handleDisplayPokemon(Pokemon pokemon) {
        Stage popUpStage = new Stage();
        DisplayPokemon displayPokemon = new DisplayPokemon(pokemon, popUpStage);
        Scene popUpScene = new Scene(displayPokemon.createScene());
        popUpStage.setTitle("Display Pokemon");
        popUpStage.setScene(popUpScene);
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        Window owner = addBtn.getScene().getWindow();
        popUpStage.initOwner(owner);
        popUpStage.setOnHidden(event -> handleDisplayEvent());
        popUpStage.showAndWait();
    }
}
