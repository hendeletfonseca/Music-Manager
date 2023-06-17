module music_manager.gui {
    exports musicmanager.application.gui;

    requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;

    opens musicmanager.application.gui to javafx.graphics;
    exports musicmanager.application.service;
    opens musicmanager.application.service to javafx.graphics;
}
