package actors;


import lists.Completed;
import lists.OrderTime;
import lists.Orders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class SellerApp {
    static Scanner sc = new Scanner(System.in);
    static Completed completed;
    static Orders orders;

    static {
        try {
            completed = new Completed();
            orders = new Orders();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) throws IOException {
        while(true) {
            System.out.println("Jesli chcesz sprawdzic czy jest nowe gotowe zamowienie kliknij 1, jesli chcesz wyjsc kliknij 0");
            if(sc.nextInt()==0)break;

            checkReady();
        }
    }


    private static void checkReady() throws IOException {
        float[] idAndSum = orders.getReadyIdAndSum();

        OrderTime time = new OrderTime();
        if(time.getTime((int)idAndSum[0])>=30)idAndSum[1]=idAndSum[1]/2;

        if(idAndSum[0]!=0){
            System.out.println("Zamowienie dla klienta nr "+(int)idAndSum[0]);
            completed.addIdAndSum(idAndSum);
        }else System.out.println("Brak gotowych zamwien");
        idAndSum[0]=0;
    }
}