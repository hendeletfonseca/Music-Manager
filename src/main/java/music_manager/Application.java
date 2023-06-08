package music_manager;

import music_manager.collections.MusicCollection;
import music_manager.collections.MusicCollectionArrayList;

public class Application {

    private String gui;
    private String coreCollectionFileName;
    private String usersFileName;
    private String loginUsersFileName;
    private String usersParticularCollectionsFileName;
    private MusicCollectionArrayList coreCollection;

    public Application(String coreCollectionFileName, String usersFileName, String loginUsersFileName, String usersParticularCollectionsFileName) {
        gui = "App";
        this.coreCollectionFileName = coreCollectionFileName;
        this.usersFileName = usersFileName;
        this.loginUsersFileName = loginUsersFileName;
        this.usersParticularCollectionsFileName = usersParticularCollectionsFileName;
        getDataFromFile();
    }

    public String getGui() {
        return gui;
    }

    public void setGui(String gui) {
        this.gui = gui;
    }

    public String getCoreCollectionFileName() {
        return coreCollectionFileName;
    }

    public void setCoreCollectionFileName(String coreCollectionFileName) {
        this.coreCollectionFileName = coreCollectionFileName;
    }

    public String getUsersFileName() {
        return usersFileName;
    }

    public void setUsersFileName(String usersFileName) {
        this.usersFileName = usersFileName;
    }

    public String getLoginUsersFileName() {
        return loginUsersFileName;
    }

    public void setLoginUsersFileName(String loginUsersFileName) {
        this.loginUsersFileName = loginUsersFileName;
    }

    public String getUsersParticularCollectionsFileName() {
        return usersParticularCollectionsFileName;
    }

    public void setUsersParticularCollectionsFileName(String usersParticularCollectionsFileName) {
        this.usersParticularCollectionsFileName = usersParticularCollectionsFileName;
    }

    public MusicCollection getCoreCollection() {
        return coreCollection;
    }

    private void getDataFromFile() {

    }

}
