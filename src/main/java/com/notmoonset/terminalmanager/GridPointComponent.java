package com.notmoonset.terminalmanager;

import com.notmoonset.terminaltext.TerminalText;

public class GridPointComponent {
    TerminalText tt;
    public char c;
    public int drawHeight;
    public char[] options;

    public void reset() {
        this.c = 0;
        this.drawHeight = 0;
        this.options = null;
    }

    public void update(GridPointComponent gpc) {
        try{
            if (gpc.tt != this.tt) {
                throw new IllegalAccessError();
            } else {
                this.c = gpc.c;
                this.drawHeight = gpc.drawHeight;
                this.options = gpc.options;
            }
        } catch (IllegalAccessError e) {
            e.printStackTrace();
        }
    }

    public GridPointComponent(TerminalText tt, char c, int drawHeight, char[] options) {
        this.tt = tt;
        this.c = c;
        this.drawHeight = drawHeight;
        this.options = options;
    }
}
