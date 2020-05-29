package model;

public enum Type {
    LINEAR (25),
    HYPERBOLA (5),
    POWER (2),
    SQUARE (7),
    EXP (2),
    LOG (5);

    private int bias;

    Type(int bias) {
        this.bias = bias;
    }

    public int getBias() {
        return bias;
    }
}
