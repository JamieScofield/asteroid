package com.astroidsgame.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTypeTests {

    @Test
    void hasExactlyThreeValuesInDeclarationOrder() {
        assertArrayEquals(
                new EntityType[] { EntityType.SHIP, EntityType.ASTEROID, EntityType.BULLET },
                EntityType.values()
        );
    }

    @Test
    void valueOfReturnsMatchingConstant() {
        assertEquals(EntityType.SHIP, EntityType.valueOf("SHIP"));
        assertEquals(EntityType.ASTEROID, EntityType.valueOf("ASTEROID"));
        assertEquals(EntityType.BULLET, EntityType.valueOf("BULLET"));
    }
}
