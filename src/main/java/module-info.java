module com.astroidsgame.main {
    requires javafx.base;
    requires javafx.graphics;

    opens com.astroidsgame.main to javafx.graphics;

}