package Generation;

public enum Mapflag {
    zero(0),
    one(1),
    two(2),
    three(3),
    used(4);

    private int value;

    public int getValue() {
        return this.value;
    }

    private Mapflag(int i) {
        this.value = i;
    }
}
