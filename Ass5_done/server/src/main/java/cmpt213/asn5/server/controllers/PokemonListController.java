package cmpt213.asn5.server.controllers;
/**
 * REST controller for managing Pokemon entities. Provides endpoints to perform CRUD operations on Pokemon.
 * @Author Irene Luu
 * @version 01
 */
import cmpt213.asn5.server.models.Pokemon;
import cmpt213.asn5.server.models.PokemonList;
import cmpt213.asn5.server.services.LoadFileService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PokemonListController {

    private AtomicInteger nextId;
    private PokemonList pokemonList;
    private LoadFileService loadFileService;

    // Construct before everything
    @PostConstruct
    public void init() {
        pokemonList = new PokemonList();
        loadFileService = new LoadFileService();
        nextId = new AtomicInteger(1);
    }

    @GetMapping("/api/tokimon/{id}")
    public Pokemon getPokemon(@PathVariable long id, HttpServletResponse response) {
        Pokemon pokemon = pokemonList.getPokemon(id);
        if (pokemon == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return pokemon;
    }

    @GetMapping("/api/tokimon/get/{type}")
    public List<Pokemon> getAllPokemonType(@PathVariable String type, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return pokemonList.getTypePokemons(type);
    }

    @GetMapping("/api/tokimon/all")
    public List<Pokemon> getAllPokemons(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return pokemonList.getAllPokemons();
    }

    @PostMapping("/api/tokimon/add")
    public Pokemon addPokemon(@RequestBody Pokemon newPokemon, HttpServletResponse response) {
        newPokemon.setId(nextId.getAndIncrement());
        pokemonList.addPokemon(newPokemon);
        loadFileService.loadRenewPokemonFile(pokemonList);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return newPokemon;
    }

    @PutMapping("/api/tokimon/edit/{id}")
    public Pokemon editPokimon(@PathVariable long id, @RequestBody Pokemon newPokemon, HttpServletResponse response) {
        Pokemon editedPokemon = pokemonList.editPokemon(id, newPokemon);
        loadFileService.loadRenewPokemonFile(pokemonList);
        response.setStatus(HttpServletResponse.SC_OK);
        return editedPokemon;
    }

    @DeleteMapping("/api/tokimon/{id}")
    public void deletePokimon(@PathVariable long id, HttpServletResponse response) {
        pokemonList.deletePokemon(id);
        loadFileService.loadRenewPokemonFile(pokemonList);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}