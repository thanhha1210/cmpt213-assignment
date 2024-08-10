package cmpt213.asn5.server;

import cmpt213.asn5.server.models.Pokemon;
import cmpt213.asn5.server.models.PokemonList;
import cmpt213.asn5.server.services.LoadFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PokemonListControllerTest {

    @MockBean
    private PokemonList pokemonList;

    @MockBean
    private LoadFileService loadFileService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Pokemon pokemon3;
    private Pokemon pokemon4;
    private List<Pokemon> pokemons;

    @BeforeEach
    void setUp() {
        System.out.println("Run before each test");

        pokemon1 = new Pokemon("Pikachu", "Electric", 8, "http://localhost:8080/img/pikachu.png", 120);
        pokemon1.setId(1);

        pokemon2 = new Pokemon("Pikachu2", "Electric", 8, "http://localhost:8080/img/pikachu.png", 120);
        pokemon2.setId(2);

        pokemon3 = new Pokemon("Pikachu3", "Electric", 8, "http://localhost:8080/img/pikachu.png", 120);
        pokemon3.setId(3);

        pokemon4 = new Pokemon("Charmander", "Fire", 6, "http://localhost:8080/img/pikachu.png", 100);
        pokemon4.setId(4);

        pokemons = new ArrayList<>();
        pokemons.add(pokemon1);
        pokemons.add(pokemon2);
        pokemons.add(pokemon3);
        pokemons.add(pokemon4);
    }

    @Test
    void contextLoads() throws Exception {
        System.out.println("Context loaded successfully.");
    }

    @Test
    void testValidAddPokemon() throws Exception {
        when(pokemonList.getAllPokemons()).thenReturn(pokemons);
        when(pokemonList.addPokemon(pokemon1)).thenReturn(pokemon1);
        when(pokemonList.addPokemon(pokemon2)).thenReturn(pokemon2);

        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(objectMapper.writeValueAsString(pokemon1))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1, \"name\": \"Pikachu\", \"type\": \"Electric\", \"rarity\": 8, \"hp\": 120, \"image\": \"http://localhost:8080/img/pikachu.png\"}"));

        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(objectMapper.writeValueAsString(pokemon2))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":2, \"name\": \"Pikachu2\", \"type\": \"Electric\", \"rarity\": 8, \"hp\": 120, \"image\": \"http://localhost:8080/img/pikachu.png\"}"));

        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(objectMapper.writeValueAsString(pokemon3))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(objectMapper.writeValueAsString(pokemon4))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testValidGetAPokemon() throws Exception {
        // Mock the behavior of getAllPokemonType
        when(pokemonList.getAllPokemons()).thenReturn(pokemons);
        when(pokemonList.getPokemon(1)).thenReturn(pokemon1);

        // Perform the GET request and verify the response
        this.mockMvc.perform(
                        get("/api/tokimon/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1, \"name\": \"Pikachu\", \"type\": \"Electric\", \"rarity\": 8, \"hp\": 120, \"image\": \"http://localhost:8080/img/pikachu.png\"}"));
    }

    @Test
    void testValidGetPokemonType() throws Exception {
        List<Pokemon> pokemonType = new ArrayList<>();
        pokemonType.add(pokemon1);
        pokemonType.add(pokemon2);
        pokemonType.add(pokemon3);
        when(pokemonList.getTypePokemons("Electric")).thenReturn(pokemonType);

        this.mockMvc.perform(
                        get("/api/tokimon/get/Electric")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testValidGetAllPokemon() throws Exception {
        when(pokemonList.getAllPokemons()).thenReturn(pokemons);

        this.mockMvc.perform(
                        get("/api/tokimon/all")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testValidDeletePokemon() throws Exception {
        when(pokemonList.getPokemon(3)).thenReturn(pokemon3);
        doNothing().when(pokemonList).deletePokemon(3);

        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 3)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
