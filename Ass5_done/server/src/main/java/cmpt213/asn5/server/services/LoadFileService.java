package cmpt213.asn5.server.services;
/**
 * Service for loading and renewing the Pokemon file. Provides functionality to write the current state
 * of the PokemonList to a JSON file.
 * @Author Irene Luu
 * @version 01
 */
import cmpt213.asn5.server.models.PokemonList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class LoadFileService {

    private static final String FILE_PATH = "tokimoncards.json";

    public void loadRenewPokemonFile(PokemonList pokemonList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(FILE_PATH), pokemonList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



}
