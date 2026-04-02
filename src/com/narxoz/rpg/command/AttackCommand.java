package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaOpponent;

public class AttackCommand implements ActionCommand {
    private final ArenaOpponent target;
    private final int attackPower;
    private int damageDealt;

    public AttackCommand(ArenaOpponent target, int attackPower) {
        this.target = target;
        this.attackPower = attackPower;
    }

    public void execute() {
        int hpBefore = target.getHealth();
        target.takeDamage(attackPower);
        damageDealt = hpBefore - target.getHealth();
        System.out.println("[Attack] Dealt " + damageDealt + " damage to "
                + target.getName() + ". Opponent HP: " + target.getHealth());
    }

    @Override
    public void undo() {
        target.restoreHealth(damageDealt);
        System.out.println("[Undo Attack] Restored " + damageDealt + " HP to " + target.getName());
    }

    @Override
    public String getDescription() {
        return "Attack for " + attackPower + " damage";
    }
}
