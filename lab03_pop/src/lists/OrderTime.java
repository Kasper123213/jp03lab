package lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderTime {
    private File file = new File("resources/time.txt");
    Scanner in = new Scanner(file);

    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<Integer> time = new ArrayList<>();

    public OrderTime() throws IOException {}



    private void setTime() throws FileNotFoundException {
        in = new Scanner(file);
        String[] s;
        while (in.hasNextLine()) {
            s = in.nextLine().split(";");
            try {
                ids.add(Integer.parseInt(s[0]));
                time.add(Integer.parseInt(s[1]));
            }catch(NumberFormatException e){}
        }
    }


    private void writeTime() throws IOException {
        setTime();
        FileWriter fWriter = new FileWriter(file);
        String s = "";
        for (int i = 0; i < ids.size(); i++) {
            s += ids.get(i) + ";";
            s += time.get(i)+"\n";
        }
        fWriter.write(s);
        fWriter.close();

    }


    public int getTime(int id) throws FileNotFoundException {
        setTime();
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) == id) return time.get(i);
        }
        return 0;
    }


    public void addNewTimer(int id) throws IOException {
        ids.add(id);
        time.add(0);
        writeTime();

    }

    public void deleteTime(int id) throws IOException {
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) == id){
                ids.remove(i);
                time.remove(i);
                break;
            }
        }
        writeTime();
    }


    public boolean isFreeId(int id) throws FileNotFoundException {
        setTime();
        for(int ids:ids){
            if(id==ids)return false;
        }
        return true;
    }
}
