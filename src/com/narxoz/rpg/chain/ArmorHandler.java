package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class ArmorHandler extends DefenseHandler {
    private final int armorValue;

    public ArmorHandler(int armorValue) {
        this.armorValue = armorValue;
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int absorbed = Math.min(armorValue, incomingDamage);
        int remaining = Math.max(0, incomingDamage - armorValue);
        System.out.println("[Armor] Absorbed " + absorbed + " damage. Remaining: " + remaining);
        passToNext(remaining, target);
    }
}
