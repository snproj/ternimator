package com.notmoonset.terminalmanager;

public class TerminalUpdateComponent {
    public int x, y;
    public boolean isEraseCommand;
    public GridPointComponent gpc;

    public TerminalUpdateComponent(int x, int y, boolean isEraseCommand, GridPointComponent gpc) {
        this.x = x;
        this.y = y;
        this.isEraseCommand = isEraseCommand;
        this.gpc = gpc; 
    }
}
