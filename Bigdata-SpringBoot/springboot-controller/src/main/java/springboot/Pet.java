package springboot;

public enum Pet {
    A("wod"), B("moo"), C("asc");

    private final String value;

    Pet(String wod) {
        this.value = wod;
    }
}
