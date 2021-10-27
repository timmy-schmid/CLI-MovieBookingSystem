package R18_G2_ASM2;

import java.util.Locale;
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

    public void run(){
       while(this.checkFullorNot()){
           this.bookingShowingSection();
           this.bookingASeat();
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


    public boolean bookingTicketForSeat(char rowLetter, int col){
        try{
        return this.showing.getMovieSeat().bookSeat(rowLetter,col);
        }
        catch (Exception e){
            System.out.println("error message");
            return false;
        }
    }

    public void bookingShowingSection(){
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("Which area you wanna choose?");
            System.out.println("1-Front");
            System.out.println("2-Middle");
            System.out.println("3-Rear");
            System.out.println("4-All");
            System.out.println("Q-quit\n");
            String str = scan.next();
            if(str.equals("1")){
                this.showing.getMovieSeat().showFrontSeats();
                break;
            }else if(str.equals("2")){
                this.showing.getMovieSeat().showMiddleSeats();
                break;
            }else if(str.equals("3")){
                this.showing.getMovieSeat().showRearSeats();
                break;
            }else if(str.equals("4")){
                this.showing.getMovieSeat().showAllSeats();
                break;
            }else if(str.equals("Q")){
                // return exit;
                break;
            }else{
                System.out.println("Invaild input, please try again.");
            }

        }
    }

    public void bookingASeat(){
        Scanner scan = new Scanner(System.in);
        int col = 0;
        char[] row = null;
        char rowLetter = 'Z';
        while (true){
            System.out.println("The row you chosen: ");
            row = scan.next().toUpperCase().toCharArray();
            if(row.length == 1){
                rowLetter = row[0];
                System.out.println("The column you chosen: ");
                try{
                    col = scan.nextInt();
                    if(!this.bookingTicketForSeat(rowLetter,col)){
                        throw new Exception();
                    }
                }catch (Exception e){
                    System.out.println("Invalid input, please try again.");
                    System.out.println("Do you want to continue? [Y/N]");
                    char[] mes = scan.next().toUpperCase().toCharArray();
                    if(mes[0] == 'N'){
                        //return to the default page
                        break;}}
            }else{
                System.out.println("Invalid message, please try again.");
                System.out.println("Do you want to continue? [Y/N]");
                char[] mes = scan.next().toUpperCase().toCharArray();
                if(mes[0] == 'N'){
                    //return to the default page
                    break;
                }
            }
        }
    }
}
