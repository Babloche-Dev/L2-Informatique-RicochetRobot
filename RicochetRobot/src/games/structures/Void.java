package games.structures;

public class Void implements Box {

    private boolean lockCase;


    public Void(){
        this.lockCase = false;
    }

    public void setLockCase(boolean lockCase) {
        this.lockCase = lockCase;
    }

    @Override
    public boolean isLockCase() {
        return lockCase;
    }
}
