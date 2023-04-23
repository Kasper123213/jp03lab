package lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
//dane dotycz¹ce p³atnoœci: identyfikator zamówienia, forma p³atnoœci, status p³atnoœci, kwota nale¿noœci.
public class Completed {

    ArrayList<Integer> completedId = new ArrayList<>();
    ArrayList<Integer> completedPaymentForm = new ArrayList<>();
    ArrayList<Integer> completedPaymentStatus = new ArrayList<>();
    ArrayList<Float> completedPaymentSum = new ArrayList<>();

    File file = new File("resources/completed.txt");
    Scanner in = new Scanner(file);
    public Completed() throws IOException {
    }


    private void setCompleted() throws FileNotFoundException {
        in = new Scanner(file);
        String[] s;
        while (in.hasNextLine()) {
            s = in.nextLine().split(";");
            try {
                completedId.add(Integer.parseInt(s[0]));
                completedPaymentForm.add(Integer.parseInt(s[1]));
                completedPaymentStatus.add(Integer.parseInt(s[2]));
                completedPaymentSum.add(Float.parseFloat(s[3]));
            }catch (NumberFormatException e){}
        }

    }

    private void writeOrders() throws IOException {
        FileWriter fWriter = new FileWriter(file);
        String s = "";
        for (int i = 0; i < completedId.size(); i++) {
            s += completedId.get(i) + ";";
            s += completedPaymentForm.get(i)+";";
            s += completedPaymentStatus.get(i)+";";
            s += completedPaymentSum.get(i) + "\n";
        }
        fWriter.write(s);
        fWriter.close();
    }


    public boolean isReady(int id) throws FileNotFoundException {
        setCompleted();
        for(int i:completedId){
            if(id==i){return true;}
        }
        return false;
    }

    public void addIdAndSum(float[] idAndSum) throws IOException {
        resetLists();
        setCompleted();
        completedId.add((int)idAndSum[0]);
        completedPaymentForm.add(0);
        completedPaymentStatus.add(0);
        completedPaymentSum.add(idAndSum[1]);
        writeOrders();
    }

    private void resetLists() {
        completedId.clear();
        completedPaymentSum.clear();
        completedPaymentForm.clear();
        completedPaymentStatus.clear();
    }

    public void setMethod(int id, int form) throws IOException {
        resetLists();
        setCompleted();
        for(int i=0;i<completedId.size();i++){
            if(completedId.get(i)==id) {
                completedPaymentForm.set(i,form);
                completedPaymentStatus.set(i,1);
            }
        }
        writeOrders();
    }

    public float howMuch(int id) {
        for(int i=0;i<completedId.size();i++){
            if(completedId.get(i)==id) return completedPaymentSum.get(i);
        }
        return 0;
    }

    public boolean isFreeId(int id) throws FileNotFoundException {
        setCompleted();
        for(int ids:completedId){
            if(id==ids)return false;
        }
        return true;
    }


}
