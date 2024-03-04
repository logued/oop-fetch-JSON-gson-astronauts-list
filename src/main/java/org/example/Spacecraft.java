package org.example;
import java.util.ArrayList;

/**
 * Spacecraft class represents a spacecraft
 * - stores spacecraft name and its crew (a List of Astronauts)
 */
public class Spacecraft {
    private String craftName;   // e.g ISS
    private ArrayList<Astronaut> crew;

    /**
     * Constructor that takes an existing list of Astronauts
     * @param craftName
     * @param crewList - an ArrayList of Astronaut objects
     */
    public Spacecraft(String craftName, ArrayList<Astronaut> crewList) {
        this.craftName = craftName;
        this.crew = crewList;
    }

    public Spacecraft(String craftName) {
        this.craftName = craftName;
        this.crew = new ArrayList<Astronaut>();
    }

    public String getCraftName() {
        return craftName;
    }

    public void setCraftName(String craftName) {
        this.craftName = craftName;
    }

    public ArrayList<Astronaut> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Astronaut> crew) {
        this.crew = crew;
    }

    @Override
    public String toString() {
        return "Spacecraft{" +
                "craftName='" + craftName + '\'' +
                ", crew=" + crew +
                '}';
    }
}
