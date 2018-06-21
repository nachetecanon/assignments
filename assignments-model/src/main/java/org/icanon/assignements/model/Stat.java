package org.icanon.assignements.model;

/**
 * The type Stat.
 */
public class Stat implements Comparable<Stat> {
    private int frequency;
    private String id;
    private char charToBeCounted;
    private String equalsSymbol;


    public Stat(String id, char charToBeCounted, String equalsSymbol) {
        this.id = id;
        this.charToBeCounted = charToBeCounted;
        this.equalsSymbol = equalsSymbol;
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        return String.format("%s:%s", this.id, getRepeatedChar());
    }

    public String getRepeatedChar() {
        char[] arrayOfBytes = new char[this.frequency];
        for (int i = 0; i < this.frequency; i++) {
            arrayOfBytes[i] = this.charToBeCounted;
        }
        return new String(arrayOfBytes);
    }

    @Override
    public int compareTo(Stat o2) {
        if (this.getId().contains(this.equalsSymbol)) {
            if (o2.getId().contains(this.equalsSymbol)) {
                return this.frequency == o2.frequency ?
                        Integer.compare(this.charToBeCounted, o2.charToBeCounted) :
                        Integer.compare(o2.frequency, this.frequency);
            } else {
                return this.frequency == o2.frequency ?
                        1 :
                        Integer.compare(o2.frequency, this.frequency);
            }
        } else {
            if (o2.getId().contains(this.equalsSymbol)) {
                return this.frequency == o2.frequency ?
                        -1 :
                        Integer.compare(o2.frequency, this.frequency);
            } else {
                return this.frequency == o2.frequency ?
                        Integer.compare(this.charToBeCounted, o2.charToBeCounted) :
                        Integer.compare(o2.frequency, this.frequency);
            }
        }
    }
}
