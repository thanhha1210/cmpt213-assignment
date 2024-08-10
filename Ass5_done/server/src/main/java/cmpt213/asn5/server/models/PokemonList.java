package cmpt213.asn5.server.models;

/**
 * Manages a list of Pokemon objects. Provides methods to add, retrieve, edit, and delete Pokemon.
 * Also includes methods to retrieve all Pokemon or those of a specific type.
 * @Author Irene Luu
 * @version 01
 */
import java.util.ArrayList;
import java.util.List;

public class PokemonList {
    private List<Pokemon> pokemons;

    public PokemonList() {
        pokemons = new ArrayList<>();
    }

    public Pokemon getPokemon(long id) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getId() == id) {
                return pokemon;
            }
        }
        return null;
    }

    public List<Pokemon> getTypePokemons(String type) {
        List<Pokemon> chosenPokemons = new ArrayList<>();
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getType().equals(type)) {
                chosenPokemons.add(pokemon);
            }
        }
        return chosenPokemons;
    }

    public List<Pokemon> getAllPokemons() {
        return pokemons;
    }

    public Pokemon addPokemon(Pokemon newPokemon) {
        pokemons.add(newPokemon);
        return newPokemon;
    }

    public Pokemon editPokemon(long id, Pokemon editPokemon) {
        Pokemon pokemon = getPokemon(id);
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon with id " + id + " does not exist.");
        }
        pokemon.setName(editPokemon.getName());
        pokemon.setType(editPokemon.getType());
        pokemon.setImage(editPokemon.getImage());
        pokemon.setRarity(editPokemon.getRarity());
        pokemon.setHp(editPokemon.getHp());
        return pokemon;
    }


    public void deletePokemon(long id) {
        Pokemon pokemon = this.getPokemon(id);
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon with id " + id + " does not exist.");
        }
        pokemons.remove(pokemon);
    }

}
