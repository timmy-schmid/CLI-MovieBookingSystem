package R18_G2_ASM2;

public enum Person {
    Chird(0.5),Student(0.75),Adult(1),Senior(0.8);
    private double value;
    private Person(double value){
        this.value =  value;
    }
    public double getValue(){
        return this.value;
    }


}
