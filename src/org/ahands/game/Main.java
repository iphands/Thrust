package org.ahands.game;

public class Main {
    /**
     * Application init
     *
     * @param args Commandline args
     */
    public static void main(String[] args) {
        boolean fullscreen = (args.length == 1 && args[0].equals("-fullscreen"));
        Thrust thrust = new Thrust();
        try {
            thrust.init(fullscreen);
            thrust.run();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            // Sys.alert(GAME_TITLE, "An error occurred and the game will exit.");
        } finally {
            // cleanup();
        }
        System.exit(0);
    }
}
