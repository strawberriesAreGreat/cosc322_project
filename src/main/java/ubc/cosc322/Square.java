package ubc.cosc322;

public class Square {

    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    public static final int FIRE = 3;



    private int value;

    private int qdist1, qdist2, kdist1, kdist2;

    public Square(int value){
        setValue(value);

        //Starting distance is "infinity"
        qdist1 = Integer.MAX_VALUE;
        qdist2 = Integer.MAX_VALUE;
        kdist1 = Integer.MAX_VALUE;
        kdist2 = Integer.MAX_VALUE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if(value < EMPTY || value > FIRE){
            System.out.println("Invalid square value.");
            return;
        }
        this.value = value;
    }

    public int getQdist1() {
        return qdist1;
    }

    public void setQdist1(int qdist1) {
        this.qdist1 = qdist1;
    }

    public int getQdist2() {
        return qdist2;
    }

    public void setQdist2(int qdist2) {
        this.qdist2 = qdist2;
    }

    public int getKdist1() {
        return kdist1;
    }

    public void setKdist1(int kdist1) {
        this.kdist1 = kdist1;
    }

    public int getKdist2() {
        return kdist2;
    }

    public void setKdist2(int kdist2) {
        this.kdist2 = kdist2;
    }

    @Override
    public String toString(){
        return "Value: " + value + ", QDist1: " + getQdist1() + ", QDist2: " + getQdist2() + ", KDist1: " + getKdist1() + ", KDist2: " + getKdist2();
    }





}
