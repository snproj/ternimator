package com.notmoonset.terminalmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerminalManager {
    public static TerminalManager instance;
    private GridPointRepresentation gpr;
    private DetectedOS detectedOS;

    private class GridPointRepresentation {
        private GridPoint[][] body;
        private int maxHeight;
        private int maxWidth;

        public boolean checkInBounds(int x, int y) {
            return (x >= 0 && x <= this.maxWidth) || (y >= 0 && y <= this.maxHeight);
        }

        public GridPoint getPoint(int x, int y) {
            try{
                if (!checkInBounds(x, y)) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                return body[y][x];
            } catch(ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                return null;
            }
        }

        private void renderPoint(GridPoint gp) {
            int maxDrawHeight = -1;
            GridPointComponent maxGPC = null;
            for (GridPointComponent gpc : gp.hm.values()) {
                if (gpc.drawHeight > maxDrawHeight) {
                    maxGPC = gpc;
                }
            }
            if (maxGPC != null) {
                System.out.print(String.format("\033[%d;%dH", gp.y, gp.x));
                System.out.print(maxGPC.c);
            }
        }

        public void update(TerminalUpdate u) {
            for (TerminalUpdateComponent tuc : u.updateContent) {
                GridPoint targetGP = getPoint(tuc.x, tuc.y);
                GridPointComponent gpc = targetGP.hm.get(tuc.gpc.tt);
                if (tuc.isEraseCommand) {
                    gpc.reset();
                } else {
                    gpc.update(tuc.gpc);
                }
                renderPoint(targetGP);
            }
        }

        public GridPointRepresentation(int x, int y) {
            this.maxHeight = y+1;
            this.maxWidth = x+1;
            this.body = new GridPoint[y+1][x+1];
            for (int i=0; i<y; i++) {
                for (int j=0; j<x; j++) {
                    this.body[y][x] = new GridPoint(x, y);
                }
            }
        }
    }

/* 
    public Collision checkTextCollision() {

    }

    public Collision checkScreenEdgeCollision() {

    } */

    private enum DetectedOS {
        WINDOWS,
        MAC,
        LINUX,
        UNKNOWN
    }

    public DetectedOS detectOS() {
        DetectedOS res = DetectedOS.UNKNOWN;
        String osString = System.getProperty("os.name").toLowerCase();
        if (osString.contains("windows")) {res = DetectedOS.WINDOWS;}
        if (osString.contains("mac") || osString.contains("darwin")) {res = DetectedOS.MAC;}
        if (osString.contains("linux")) {res = DetectedOS.LINUX;}
        return res;
    }

    private class NumPair {
        public int x, y;
        public NumPair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public NumPair getDimensions() {
        NumPair res;
        int x = -1;
        int y = -1;
        String str;
        Process p;
        BufferedReader br;
        try {
            switch(this.detectedOS) {
                case WINDOWS:
                    p = new ProcessBuilder("mode").start();
                    br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((str = br.readLine()) != null) {
                        if (str.contains("Lines")) {
                            y = Integer.parseInt(str.replaceAll("[^0-9]", ""));
                        }
                        if (str.contains("Columns")) {
                            x = Integer.parseInt(str.replaceAll("[^0-9]", ""));
                        }
                    }
                    break;

                case MAC: // FALLTHROUGH
                case LINUX:
                    p = new ProcessBuilder("tput", "lines").start();
                    br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((str = br.readLine()) != null) {
                        String[] temp = str.split(" ");
                        for (String s : temp) System.out.println(s);
                        y = Integer.parseInt(temp[0]);
                    }

                    p = new ProcessBuilder("tput", "cols").start();
                    br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((str = br.readLine()) != null) {
                        String[] temp = str.split(" ");
                        for (String s : temp) System.out.println(s);
                        x = Integer.parseInt(temp[0]);
                    }
                    break;
                
                default:
                    throw new Exception("System undetected?");
                    
            }

            if (x < 0) {throw new Exception("x uninitialized");}
            if (y < 0) {throw new Exception("y uninitialized");}
        } catch(Exception e) {
            e.printStackTrace();
        }

        res = new NumPair(x, y);
        return res;
    }

    public void update(TerminalUpdate u) {
        this.gpr.update(u);
    }

    private TerminalManager() {
        this.detectedOS = detectOS();
        NumPair np = getDimensions();
        this.gpr = new GridPointRepresentation(np.x, np.y);
    }

    public static synchronized TerminalManager getInstance() {
        if (instance == null) {
            instance = new TerminalManager();
        }
        return instance;
    }
}