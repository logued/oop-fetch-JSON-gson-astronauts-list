package org.example;

/**
 * Class to represent an Astronaut
 */
public class Astronaut {
    private String name;    // full name of astronaut
    private String craft;   // spacecraft that astronaut is currently on

    public Astronaut(String name, String craft) {
        this.name = name;
        this.craft = craft;
    }

    @Override
    public String toString() {
        return "Astronaut{" +
                "name='" + name + '\'' +
                ", craft='" + craft + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }
}
