package org.icanon.assignement1.model;

/**
 * The type Stat.
 */
public class Stat implements Comparable<Stat> {
    private int frequency;
    private int id;
    private char charToBeCounted;

    /**
     * Instantiates a new Stat.
     *
     * @param id              the id
     * @param charToBeCounted the char to be counted
     */
    public Stat(int id, char charToBeCounted) {
        this.id = id;
        this.charToBeCounted = charToBeCounted;
    }


    /**
     * Sets frequency.
     *
     * @param frequency the frequency
     * @return the frequency
     */
    public Stat setFrequency(int frequency) {
        this.frequency = frequency;
        return this;
    }

    /**
     * Gets frequency.
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets char to be counted.
     *
     * @return the char to be counted
     */
    public char getCharToBeCounted() {
        return charToBeCounted;
    }


    @Override
    public String toString() {
        char[] arrayOfBytes = new char[this.frequency];
        for (int i = 0; i < this.frequency; i++) {
            arrayOfBytes[i] = this.charToBeCounted;
        }
        return String.format("%s:%s", this.id > 0 ? Integer.toString(this.id) : "=", new String(arrayOfBytes));
    }

    @Override
    public int compareTo(Stat o2) {
        if (this.frequency == o2.frequency) {
            return this.toString().compareTo(o2.toString());
        } else {
            return Integer.compare(o2.toString().length(), this.toString().length());
        }
    }
}
