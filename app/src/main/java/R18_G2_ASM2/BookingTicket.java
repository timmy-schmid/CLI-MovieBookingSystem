package R18_G2_ASM2;

import java.awt.print.Book;
import java.util.Calendar;
import java.util.Scanner;

public class BookingTicket {
    private final double price = 50;
    private Showing showing;
    private User user;
    private boolean successBooking = false;
    private Integer Ct =1;

    public BookingTicket(Showing showing, User user){
        this.showing = showing;
        this.user = user;
    }

//    public static void main(String[] args){
//
//    }

    public void run(){
       while(this.checkFullorNot()){
            this.askForBooking();
            if (Ct == 2){
                // go back to the default user page
                this.printBookingMessage();
                break;
            }
        }
    }
    public boolean checkFullorNot(){
        if(!showing.isShowingFull()){
            System.out.println("No empty seat for the current show :(");
        }
        return showing.isShowingFull();
    }

    public void askForBooking(){
        Person bookingType = null;
        Scanner scan = new Scanner(System.in);
        while(Ct == 1) {
            System.out.println("Select type of ticket you want to book: ");
            System.out.println("1. Child -$\n" +
                    "2. Student -$\n" +
                    "3. Adult - $$\n" +
                    "4. Senior - $\n");
            while (true) {
                String num = scan.next();
                if (num.equals("1")) {
                    bookingType = Person.Child;
                    break;
                } else if (num.equals("2")) {
                    bookingType = Person.Student;
                    break;
                } else if (num.equals("3")) {
                    bookingType = Person.Adult;
                    break;
                } else if (num.equals("4")) {
                    bookingType = Person.Senior;
                    break;
                } else {
                    System.out.println("Invalid input,please try again");
                }
            }

            System.out.println("Select number of tickets: ");
            while (true) {
                Integer numperson = scan.nextInt();
                if (numperson > 0) {
                    this.bookingTicketForPersons(bookingType, numperson);
                    this.user.AddTicketMessage();
                    break;
                } else {
                    System.out.println("Invalid input,please try again: ");
                    break;
                }
            }
            Ct =this.Continue();
        }
    }


    public int Continue(){
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("1. Continue\n" +
                    "2. End\n");
            String str = scan.next();

            if(str.equals("1")){return 1;}
            else if(str.equals("2")){
                return 2;
            }
            else {
                System.out.println("Invalid input,please try again: ");
                return 3;
        }
        }
    }

    public void printBookingMessage(){
        this.user.totalPrice();
        System.out.println(this.user.getTicketMessage());
        System.out.println("Total = "+this.user.getTotalPrice()*price);
    }

    public void bookingTicketForPersons(Person person,int num){
        this.user.bookingTicket(person,num);
    }

}
