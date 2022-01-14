package com.notmoonset.terminalmanager;

import java.util.Set;

public class TerminalUpdate {
    public Set<TerminalUpdateComponent> updateContent;

    public TerminalUpdate(Set<TerminalUpdateComponent> updateContent) {
        this.updateContent = updateContent;
    }
}
