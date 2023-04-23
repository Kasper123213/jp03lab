package lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private File file = new File("resources/menu.txt");
    private Scanner in = new Scanner(file);
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Float> price = new ArrayList<>();
    public Menu() throws FileNotFoundException {setMenu();}

    private void setMenu(){
        String[] s;
        while (in.hasNextLine()) {
            s = in.nextLine().split(";");
            name.add(s[1]);
            price.add(Float.parseFloat(s[2]));
        }
    }

    @Override
    public String toString(){
        String menu="";
        for(int i=0;i<name.size();i++){
            menu+=(i+1)+"."+name.get(i)+" cena: "+price.get(i)+"\n";
        }
        return menu;
    }
    public String showName(int index){return name.get(index);}

    public float sum(ArrayList<Integer> list) {
        setMenu();
        float sum=0;
        for(int i:list){
            sum+=price.get(i);//?
        }
        return sum;
    }

    public boolean isIn(int food) {
        if(food-1<name.size()&&food>0)return true;
        else return false;
    }
}
