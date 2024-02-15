package net.hidme.chaoyue.cli;

import net.hidme.chaoyue.core.Util;

public class Main {
    public static void main(String[] args) {
        Util.echo("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
    }
}