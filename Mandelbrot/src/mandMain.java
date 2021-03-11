
public class mandMain {
    public static void main(String[] args) {
        try
        {
            displaySystem display = new displaySystem(800, 800);
            display.add(new myPanel());
        }
        catch (Exception e){e.printStackTrace();}
    }
}
