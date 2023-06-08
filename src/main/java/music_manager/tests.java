package music_manager;

import music_manager.users.User;

public class tests {

    public static void main(String[] args) {
        String dir = "./src/datas/particular_collections/";
        User user = new User(dir + "hendel", 1520);
        user.createParticularMusicCollection();

    }

}
