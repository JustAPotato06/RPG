package dev.potato.highlands.core.rpgclass;

public abstract class RpgClass {

    private final String name;

    public RpgClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
