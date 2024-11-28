package entities;

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
        System.out.println("полки " + conditionOfShelves);
    }
    public class  Dust{
        private String purity;
        public Dust(String purity){
            this.purity = purity;
        }
        public void mesAbDust(){
            System.out.print("Кроме того, что " );
            messageAboutTheConditionOfShelves();
            System.out.println("На этих полках " + purity);
        }
    }
}
