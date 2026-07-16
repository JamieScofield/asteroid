package com.astroidsgame.ui;

import com.astroidsgame.simulation.EntityId;
import com.astroidsgame.simulation.SimEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class RenderLoop extends AnimationTimer {

    private final LinkedBlockingQueue<SimEvent> outputQueue;
    private final Group root;
    private final Map<EntityId, Node> nodes = new HashMap<>();

    public RenderLoop(LinkedBlockingQueue<SimEvent> outputQueue, Group root) {
        this.outputQueue = outputQueue;
        this.root = root;
    }

    @Override
    public void handle(long now) {
        SimEvent event;
        while ((event = outputQueue.poll()) != null) {
            switch (event) {
                case SimEvent.EntitySpawned spawned -> {
                    Node node = EntityRenderer.createNode(spawned);
                    nodes.put(spawned.id(), node);
                    root.getChildren().add(node);
                }
                case SimEvent.EntityMoved moved -> {
                    Node node = nodes.get(moved.id());
                    if (node != null) {
                        EntityRenderer.applyMove(node, moved);
                    }
                }
                case SimEvent.EntityRemoved removed -> {
                    Node node = nodes.remove(removed.id());
                    if (node != null) {
                        root.getChildren().remove(node);
                    }
                }
            }
        }
    }
}
