package com.notmoonset.terminalmanager;

import java.util.HashMap;

import com.notmoonset.terminaltext.TerminalText;

public class GridPoint {
    public int x, y;
    HashMap<TerminalText, GridPointComponent> hm;

    public GridPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.hm = new HashMap<TerminalText, GridPointComponent>();
    }
}
