package music_manager.users;

public abstract class AbstractUser {

    private String name;
    private int id;

    public AbstractUser(String name) {
        this.name = name;
        this.id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
