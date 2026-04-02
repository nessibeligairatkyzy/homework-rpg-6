package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaFighter;

public class HealCommand implements ActionCommand {
    private final ArenaFighter target;
    private final int healAmount;
    private int actualHealApplied;

    public HealCommand(ArenaFighter target, int healAmount) {
        this.target = target;
        this.healAmount = healAmount;
    }

    @Override
    public void execute() {
        if (target.getHealPotions() <= 0) {
            System.out.println("[Heal] No potions left! Cannot heal.");
            actualHealApplied = 0;
            return;
        }
        int hpBefore = target.getHealth();
        target.heal(healAmount);
        actualHealApplied = target.getHealth() - hpBefore;
        System.out.println("[Heal] " + target.getName() + " healed for "
                + actualHealApplied + " HP. HP: " + target.getHealth()
                + "/" + target.getMaxHealth());
    }

    @Override
    public void undo() {
        target.takeDamage(actualHealApplied);
        System.out.println("[Undo Heal] Removed " + actualHealApplied + " HP from " + target.getName());
    }

    @Override
    public String getDescription() {
        return "Heal for " + healAmount + " HP";
    }
}
