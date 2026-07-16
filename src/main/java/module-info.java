module com.astroidsgame.main {
    requires javafx.base;
    requires javafx.graphics;

    opens com.astroidsgame.ui to javafx.graphics;

}