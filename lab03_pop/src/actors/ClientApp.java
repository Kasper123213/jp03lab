package actors;

import exceptions.PossitionNotFoundInMenuException;
import lists.Completed;
import lists.Menu;
import lists.OrderTime;
import lists.Orders;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Kasper Radom, 264023
 * kompilowanie: javac nazwapliku.java
 * uruchamianie "java -jar nazwa_pliku.jar" lub "java nazwa_pliku" jesli chcemy odpalic plik .class
 */

public class ClientApp {
    static Scanner sc = new Scanner(System.in);
    private static int id;

    static Orders order;
    static Menu menu;
    static Completed completed;

    static {
        try {
            order = new Orders();
            menu = new Menu();
            completed = new Completed();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) throws IOException, InterruptedException, PossitionNotFoundInMenuException {
        start();
    }


    public static void start() throws IOException, InterruptedException, PossitionNotFoundInMenuException {
        System.out.println("""
				1-Pokaz menu
				2-Twoje zamowienie
				3-Anuluj zamowienie
				""");

        int wybor;
        do {
            wybor=sc.nextInt();
        }while(wybor!=1&&wybor!=2&&wybor!=3);

        System.out.println("---------------------------------");
        if(wybor==1)showMenu();
        else if(wybor==2) showOrder();

    }



    private static void showMenu() throws IOException, InterruptedException, PossitionNotFoundInMenuException {
        System.out.println(menu);
        System.out.println("---------------------------------");
        start();
    }


    private static void showOrder() throws IOException, InterruptedException, PossitionNotFoundInMenuException {
        System.out.println("twoje zamowienie:");
        System.out.println(order);

        System.out.println("---------------------------------");
        System.out.println("""
				1-Dodaj pozycje do zamowienia
				2-Usun pozycje z zamowienia
				3-Zaakceptuj
				4-Powrot
				5-Anuluj zamowienie
				""");



        int wybor;

        do {
            wybor=sc.nextInt();
        }while(wybor!=1&&wybor!=2&&wybor!=3&&wybor!=4&&wybor!=5);
        switch (wybor){
            case 1:{
                addPossition();
                break;
            }
            case 2:{
                removePossition();
                break;
            }
            case 3:{
                waitForOrder();
                break;
            }
            case 4:{
                start();
                break;
            }
            case 5:break;

        }

    }


    private static void waitForOrder() throws IOException, InterruptedException {
        OrderTime time = new OrderTime();
        id=order.setID();
        System.out.println("Trwa przygotowywanie zamowienia, prosz czekac");

        time.addNewTimer(id);

        int status=-1;
        while(!completed.isReady(id)) {

            Thread.sleep(1000);
            int ostatus=order.showStatus(id);
            if (ostatus!= status) {
                status = ostatus;

                if(status==0)System.out.println("Zamowienie czeka na realizacje przez kucharza\n");
                else if (status==1) System.out.println("Zamowienie w realizacji\n");
                else if (status==2) System.out.println("Zamowienie gotowe do odbioru\n");

            }
        }
        time.deleteTime(id);
        System.out.println("Zamowienie gotowe do odbioru\n");
        System.out.println("---------------------------------");
        pay();
    }

    private static void pay() throws IOException {
        System.out.println("Do zaplaty "+completed.howMuch(id));
        System.out.println("---------------------------------");
        System.out.println("""
                Metoda platnosci:
                    1-BLIK
                    2-gotowka
                    3-zblizeniowo
                    
                """);
        int method=sc.nextInt();
        System.out.println("Aby zaplacic nacisnij 1");
        sc.nextInt();
        completed.setMethod(id,method);
        System.out.println("Smacznego!!!");
    }

    private static void removePossition() throws IOException, InterruptedException, PossitionNotFoundInMenuException {
        System.out.println("---------------------------------");
        System.out.println("Podaj pozycje menu ktora chcesz usunac");
        order.removePossition(sc.nextInt()-1);
        showOrder();
    }

    private static void addPossition() throws IOException, InterruptedException, PossitionNotFoundInMenuException {
        System.out.println("---------------------------------");
        System.out.println("Podaj pozycje menu ktora chcesz dodac");
        int food=sc.nextInt();
//        if(menu.isIn(food)) {                 //wersja bez wyjatku
//            order.addPossition(food-1);
//        }
        isPossitionOk(food);
        order.addPossition(food-1);
        showOrder();
    }

    private static void isPossitionOk(int food) throws PossitionNotFoundInMenuException{
        if(!menu.isIn(food))throw new PossitionNotFoundInMenuException("Danie nie znajduje sie w menu");
    }


}
