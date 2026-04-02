package com.narxoz.rpg.tournament;

import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.ArmorHandler;
import com.narxoz.rpg.chain.BlockHandler;
import com.narxoz.rpg.chain.DefenseHandler;
import com.narxoz.rpg.chain.DodgeHandler;
import com.narxoz.rpg.chain.HpHandler;
import com.narxoz.rpg.command.ActionQueue;
import com.narxoz.rpg.command.AttackCommand;
import com.narxoz.rpg.command.DefendCommand;
import com.narxoz.rpg.command.HealCommand;
import java.util.Random;

public class TournamentEngine {
    private final ArenaFighter hero;
    private final ArenaOpponent opponent;
    private Random random = new Random(1L);

    public TournamentEngine(ArenaFighter hero, ArenaOpponent opponent) {
        this.hero = hero;
        this.opponent = opponent;
    }

    public TournamentEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public TournamentResult runTournament() {
        TournamentResult result = new TournamentResult();
        int round = 0;
        final int maxRounds = 20;


        DodgeHandler dodge = new DodgeHandler(hero.getDodgeChance(), random.nextLong());
        BlockHandler block = new BlockHandler(hero.getBlockRating() / 100.0);
        ArmorHandler armor = new ArmorHandler(hero.getArmorValue());
        HpHandler hp = new HpHandler();
        DefenseHandler defenseChain = dodge;
        dodge.setNext(block).setNext(armor).setNext(hp);

        ActionQueue actionQueue = new ActionQueue();

        while (hero.isAlive() && opponent.isAlive() && round < maxRounds) {
            round++;
            result.addLine("=== Round " + round + " ===");


            actionQueue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
            if (hero.getHealth() < hero.getMaxHealth() / 2) {
                actionQueue.enqueue(new HealCommand(hero, 20));
            }
            actionQueue.enqueue(new DefendCommand(hero, 0.05));


            StringBuilder queueLog = new StringBuilder("  Hero actions: ");
            for (String desc : actionQueue.getCommandDescriptions()) {
                queueLog.append("[").append(desc).append("] ");
            }
            result.addLine(queueLog.toString());


            actionQueue.executeAll();


            if (opponent.isAlive()) {
                result.addLine("  " + opponent.getName() + " attacks for "
                        + opponent.getAttackPower() + "!");
                defenseChain.handle(opponent.getAttackPower(), hero);
            }


            String roundLog = String.format("  [Round %d] %s HP: %d | %s HP: %d",
                    round,
                    opponent.getName(), opponent.getHealth(),
                    hero.getName(), hero.getHealth());
            result.addLine(roundLog);
        }


        if (hero.isAlive()) {
            result.setWinner(hero.getName());
        } else if (opponent.isAlive()) {
            result.setWinner(opponent.getName());
        } else {
            result.setWinner("Draw");
        }
        result.setRounds(round);
        return result;
    }
}
