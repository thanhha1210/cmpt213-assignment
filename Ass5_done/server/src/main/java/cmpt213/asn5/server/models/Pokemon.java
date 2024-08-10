package cmpt213.asn5.server.models;
/**
 * Represents a Pokemon entity with various attributes like id, name, type, image, rarity, and hp.
 * This class provides getter and setter methods to access and modify these attributes.
 * @Author Irene Luu
 * @version 01
 */
public class Pokemon {
    private long id;
    private String name;
    private String type;
    private String image;
    private int rarity;
    private int hp;

    public Pokemon() {}

    public Pokemon(String name, String type, int rarity, String img, int hp) {
        this.name = name;
        this.type = type;
        this.image = img;
        this.hp = hp;
        this.rarity = rarity;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getRarity() {
        return rarity;
    }
    public String getImage() {
        return image;
    }
    public int getHp() {
        return hp;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
    public void setImage(String img) {
        this.image = img;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }

}
