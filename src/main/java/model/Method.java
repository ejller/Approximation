package model;

public enum Method {
    LINEAR (25),
    HYPERBOLA (5),
    POWER (2),
    SQUARE (10),
    EXP (2),
    LOG (10);

    private int bias;

    Method(int bias) {
        this.bias = bias;
    }

    public int getBias() {
        return bias;
    }
}
