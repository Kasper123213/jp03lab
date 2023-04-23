package lists;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//dane dotycz¹ce zamówienia: identyfikator zamówienia, szczegó³y zamówienia (lista identyfikatorów zamówionych dañ), status zamówienia;
//0-czeka na relizacje przez kucharza, 1-zamowienie w realizacji, 2-zamowienie gotowedo odbioru

public class Orders {

    private File file = new File("resources/orders.txt");
    private Scanner in = new Scanner(file);
    private Menu menu = new Menu();
    private Completed completed = new Completed();
    private OrderTime time = new OrderTime();

//    listy z pliku
    private ArrayList<Integer> ordersId = new ArrayList<>();
    private ArrayList<ArrayList> ordersDetails = new ArrayList<>();
    private ArrayList<Integer> ordersStatus = new ArrayList<>();

//    lista robocza
    private ArrayList<Integer>  ordersList = new ArrayList<>();
    private ArrayList<Integer>  idAndStatus = new ArrayList<>();


    public Orders() throws IOException {}


    @Override
    public String toString(){
        String order ="";
        for(int i:ordersList){
            order+=i+1;
            order+=",";
        }
        return order;
    }

    private void setOrders() throws FileNotFoundException {
        in = new Scanner(file);
        String[] s;
        String[] list;
        int index = 0;
        while (in.hasNextLine()) {
            s = in.nextLine().split(";");

            ordersId.add(Integer.parseInt(s[0]));

            ordersDetails.add(new ArrayList<Integer>());
            list = s[1].split(",");
            for (int i = 0; i < list.length; i++) {
                ordersDetails.get(index).add(Integer.parseInt(list[i]));
            }

            ordersStatus.add(Integer.parseInt(s[2]));
            index++;
        }

    }

    private void resetLists() {
        ordersId.clear();
        ordersDetails.clear();
        ordersStatus.clear();
        ordersList.clear();
        idAndStatus.clear();
    }


    public int setID() throws IOException {
        boolean isFree;
        try{
            setOrders();
        }catch(FileNotFoundException e){}
        int id;
        for (int i = 1; ; i++) {
            isFree = true;
            for (int ids : ordersId) {
                if (i == ids) isFree = false;
            }
            if(!completed.isFreeId(i))isFree=false;
            if(!time.isFreeId(i))isFree=false;

            if (isFree) {
                id=i;
                ordersId.add(i);
                ordersDetails.add(ordersList);
                ordersStatus.add(0);
                writeOrders();
                return id;
            }
        }
    }

    private void writeOrders() throws IOException {
        FileWriter fWriter = new FileWriter(file);
        String s = "";
        for (int i = 0; i < ordersId.size(); i++) {
            s += ordersId.get(i) + ";";
            for (int j = 0; j < ordersDetails.get(i).size(); j++) {
                s += ordersDetails.get(i).get(j);
                if (j != ordersDetails.get(i).size() - 1) s += ",";
            }
            s += ";";

            s += ordersStatus.get(i) + "\n";
        }


        fWriter.write(s);
        fWriter.close();
    }


    public void addPossition(int meal){ordersList.add(meal);}

    public void removePossition(Object meal)
    {ordersList.remove(meal);}



    public int getId() throws IOException {
        resetLists();
        setOrders();
        for(int i=0;i<ordersStatus.size();i++){
            if(ordersStatus.get(i)==0){
                ordersStatus.set(i,1);
                writeOrders();
                ordersList.clear();
                ordersList.addAll(ordersDetails.get(i));
                return ordersId.get(i);
            }
        }
        return 0;
    }


    public ArrayList<Integer> getOrder() { return ordersList; }

    public void ready(int clientId) throws IOException {
        ordersStatus.set(ordersId.indexOf(clientId),2);
        writeOrders();
    }


    public float[] getReadyIdAndSum() throws IOException {
        resetLists();
        setOrders();
        int id=0;
        float sum=0;
        float[] toReturn = new float[2];
        for(int i=0;i<ordersStatus.size();i++){
            if(ordersStatus.get(i)==2){
                id=ordersId.get(i);
                sum = menu.sum(ordersDetails.get(i));

                ordersStatus.remove(i);
                ordersId.remove(i);
                ordersDetails.remove(i);
                writeOrders();
                break;
            }
        }
        toReturn[0]=id;
        toReturn[1]=sum;
        return toReturn;
    }

    public int showStatus(int id) throws FileNotFoundException {
        resetLists();
        setOrders();
        for(int i=0;i<ordersId.size();i++){
            if(ordersId.get(i)==id) return ordersStatus.get(i);
        }
        return 3;
    }

}

