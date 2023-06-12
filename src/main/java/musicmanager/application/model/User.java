package musicmanager.application.model;

import musicmanager.application.persistence.MusicCollectionPersistence;

import java.io.Serializable;

public abstract class User implements Serializable {
    private final int id;
    private String login;
    private String name;
    private TYPE_USER type;

    public User(int id, String login, String name, TYPE_USER type) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TYPE_USER getType() {
        return type;
    }

    public MusicCollection loadMusicCollection(String fileName) {
        MusicCollectionPersistence mcp = new MusicCollectionPersistence();
        return mcp.load(fileName);
    }

    public void saveMusicCollection(MusicCollection musicCollection, String fileName) {
        MusicCollectionPersistence mcp = new MusicCollectionPersistence();
        mcp.save(musicCollection, fileName);
    }


    public Music searchMusicFromCollection(MusicCollection musicCollection, String title) {
        return musicCollection.searchMusic(title);
    }


    public boolean addMusicToCollection(MusicCollection musicCollection, Music music) {
        return musicCollection.addMusic(music);
    }


    public boolean removeMusicFromCollection(MusicCollection musicCollection, Music music) {
        Music m = musicCollection.removeMusic(music);
        return m != null;
    }
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
