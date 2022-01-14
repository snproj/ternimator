package com.notmoonset.scriptparser;

import com.notmoonset.terminalmanager.TerminalManager;

public class ScriptParser {
    public static void main(String[] args) {
        TerminalManager tm = TerminalManager.getInstance();
        System.out.println(tm.detectOS());
    }

    
}