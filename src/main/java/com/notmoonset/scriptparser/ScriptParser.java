package com.notmoonset.scriptparser;

import com.notmoonset.animations.Animations;

import java.io.IOException;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class ScriptParser {
    public static void main(String[] args) {
        System.out.println("hello");
        Animations.tester();

        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            terminal.clearScreen();

            terminal.setCursorPosition(10, 5);
            terminal.putString("Hello from 1");

            Terminal terminal2 = new DefaultTerminalFactory().createTerminal();

            terminal2.setCursorPosition(20, 4);
            terminal2.putString("Hello from 2");
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    
}