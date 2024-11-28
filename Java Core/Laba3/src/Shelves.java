public class Shelves {
    private String conditionOfShelves;
    private int distanceBetweenTheShelves;
    public Shelves(String conditionOfShelves, int distanceBetweenTheShelves){
        this.conditionOfShelves = conditionOfShelves;
        this.distanceBetweenTheShelves = distanceBetweenTheShelves;
    }
    public void messageAboutTheDistanceBetweenTheShelves(){
        if (distanceBetweenTheShelves == 1){
            System.out.println("Расстояние между полками очень мало, коротышки еле помещаются");
        } else {
            System.out.println("Ого! Расстояние между полками очень велико");
        }
    }
    public void messageAboutTheConditionOfShelves(){
        System.out.println("Полки " + conditionOfShelves);
    }
}
