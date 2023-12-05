import Trolls.GameTroll;

public class printThread implements Runnable {
    GameTroll gameTroll;

    public printThread(GameTroll gameTroll) {
        this.gameTroll = gameTroll;
    }

    @Override
    public void run() {
        System.out.println("game ended" + gameTroll.won);
    }
}
