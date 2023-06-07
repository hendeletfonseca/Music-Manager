module music_manager.gui {
    exports music_manager.gui;

    requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;

    opens music_manager.gui to javafx.graphics;
}
