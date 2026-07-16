package com.astroidsgame.simulation;

public sealed interface InputEvent {
    record KeyDown(GameKey key) implements InputEvent {}
    record KeyUp(GameKey key) implements InputEvent {}
    record MouseClicked(double x, double y) implements InputEvent {}
}
