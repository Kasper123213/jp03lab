package actors;

import lists.Menu;
import lists.Orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChefApp {

    static Orders orders;
    static Menu menu;

    static {
        try {
            orders = new Orders();
            menu = new Menu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static Scanner sc = new Scanner(System.in);

    private static ArrayList<Integer> toDo =new ArrayList<>();
    private static int clientId=0;





    public static void main(String[] args) throws IOException {
        while(true) {
            System.out.println("Jesli chcesz przyjac nastepne zamowienie kliknij 1, jesli nie: 0");
            if(sc.nextInt()==1) {start();}
            else{break;}
        }
    }


    private static void start() throws IOException {
        clientId = orders.getId();
        toDo = orders.getOrder();



        for(int i=0;i<toDo.size();i++){
            System.out.println(menu.showName(toDo.get(i)));
            System.out.println("Gotowe-kliknij 1");
            sc.nextInt();
        }

        try{
            orders.ready(clientId);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Brak nowych zamowien");
        }
        clientId=0;
        toDo.clear();

    }
}
