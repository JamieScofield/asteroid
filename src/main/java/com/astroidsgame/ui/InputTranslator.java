package com.astroidsgame.ui;

import com.astroidsgame.simulation.GameKey;
import com.astroidsgame.simulation.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class InputTranslator {

    private InputTranslator() {
    }

    public static Optional<InputEvent> keyDown(KeyEvent event) {
        return gameKey(event).map(InputEvent.KeyDown::new);
    }

    public static Optional<InputEvent> keyUp(KeyEvent event) {
        return gameKey(event).map(InputEvent.KeyUp::new);
    }

    public static InputEvent mouseClicked(MouseEvent event) {
        return new InputEvent.MouseClicked(event.getX(), event.getY());
    }

    private static Optional<GameKey> gameKey(KeyEvent event) {
        return switch (event.getCode()) {
            case W -> Optional.of(GameKey.W);
            case A -> Optional.of(GameKey.A);
            case S -> Optional.of(GameKey.S);
            case D -> Optional.of(GameKey.D);
            case SPACE -> Optional.of(GameKey.SPACE);
            default -> Optional.empty();
        };
    }
}
