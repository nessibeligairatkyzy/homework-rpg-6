package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaFighter;

public class DefendCommand implements ActionCommand {
    private final ArenaFighter target;
    private final double dodgeBoost;

    public DefendCommand(ArenaFighter target, double dodgeBoost) {
        this.target = target;
        this.dodgeBoost = dodgeBoost;
    }

    @Override
    public void execute() {
        target.modifyDodgeChance(dodgeBoost);
        System.out.println("[Defend] " + target.getName() + " dodge chance boosted by +"
                + dodgeBoost + ". New dodge: "
                + String.format("%.2f", target.getDodgeChance()));
    }

    @Override
    public void undo() {
        target.modifyDodgeChance(-dodgeBoost);
        System.out.println("[Undo Defend] Removed dodge boost from " + target.getName());
    }

    @Override
    public String getDescription() {
        return "Defend (dodge boost: +" + dodgeBoost + ")";
    }
}
