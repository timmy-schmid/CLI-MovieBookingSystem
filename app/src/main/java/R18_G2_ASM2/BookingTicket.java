package R18_G2_ASM2;

import java.util.*;

public class BookingTicket {
    private final double price = 50;
    private Showing showing;
    private User user;
    private boolean successBooking = false;
    private Integer Ct =1;
    private int count = 0;
    private int indexofarray = 0;
    // cancel preparation;
    private char[] rowLetters = new char[1000];
    private int[] colNum = new int[1000];
    private LinkedHashMap<Person,Integer> bookPerson = new LinkedHashMap<>();


    public BookingTicket(Showing showing, User user){
        this.showing = showing;
        this.user = user;
        this.bookPerson.put(Person.Child,0);
        this.bookPerson.put(Person.Senior,0);
        this.bookPerson.put(Person.Student,0);
        this.bookPerson.put(Person.Adult,0);
    }

    public void run(){
        count = 0;
        Scanner scan = new Scanner(System.in);
        while(true){
            this.askForBooking();
            System.out.println(count);
            for(int i =0; i<count; i++){
                System.out.println(i);
                System.out.println(count);
                System.out.println("works?");
                this.bookingShowingSection();
                this.bookingASeat();
                if(!this.checkFullorNot()){
                    break;
                }
            }
            System.out.println("Do you want to continue? Y for booking and other input for end");
            String str = scan.next();
            if(str.equals("Y")){}else{
                this.printBookingMessage();
                //return the default page
                break;
            }
        }
    }
    public boolean checkFullorNot(){
        if(!showing.isShowingFull()){
            System.out.println("No empty seat for the current show :(");
        }
        System.out.println("DO I GET HERE2");
        return showing.isShowingFull();
    }

    public void askForBooking(){
        Person bookingType = null;
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("Select type of ticket you want to book: ");
            System.out.println("1 - Child -$\n" +
                    "2 - Student -$\n" +
                    "3 - Adult - $$\n" +
                    "4 -Senior - $\n"+"" +
                    "C - Cancel");
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
                    this.bookPerson.replace(bookingType,numperson);
                    this.bookingTicketForPersons(bookingType, numperson);
                    count += numperson;
                    this.user.AddTicketMessage();
                    break;
                } else {
                    System.out.println("Invalid input,please try again: ");
                }
            }

            Ct =this.Continue();
            if(Ct == 2){
                Ct = 1;
                break;
            }
        }
    }


    public int Continue(){
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("1-Continue\n" +
                    "2-End Adding Person\n"+"C-Cancel\n");
            String str = scan.next();

            if(str.equals("1")){return 1;}
            else if(str.equals("2")){
                return 2;
            }else if(str.equals("C")){
                this.cancelBooking();
            }
            else {
                System.out.println("Invalid input,please try again: ");}
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
            System.out.println("Cancel-cancel\n");
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
                System.out.println("works?");
                this.showing.getMovieSeat().showAllSeats();
                System.out.println("works?");
                break;
            }else if(str.equals("Cancel")){
                this.cancelBooking();
                break;
            }else{
                System.out.println("Invaild input, please try again.");
            }

        }
    }

    public void bookingASeat(){
        Scanner scan = new Scanner(System.in);
        int col = 0;
        char rowLetter = 'Z';
        while (true){
            System.out.println("The row you chosen: ");
            char[] row = scan.next().toUpperCase().toCharArray();
            if(row.length == 1){
                rowLetter = row[0];
                System.out.println("The column you chosen: ");
                try{
                    col = scan.nextInt();
                    if(!this.bookingTicketForSeat(rowLetter,col)){
                        throw new Exception();
                    }
                    rowLetters[indexofarray] = rowLetter;
                    colNum[indexofarray] = col;
                    indexofarray++;
                }catch (Exception e){
                    System.out.println("Invalid input, please try again.");
                    System.out.println("Do you want to continue? [Y/N]\n"+"C -cancel\n");
                    char[] mes = scan.next().toUpperCase().toCharArray();
                    if(mes[0] == 'N'){
                        //return to the default page
                        break;}else if (mes[0] == 'C'){
                        //return to the default page -> how to do that?
                        this.cancelBooking();
                        break;
                    }}
            }else{
                System.out.println("Invalid message, please try again.");
                System.out.println("Do you want to continue? [Y/N]\n"+"C -cancel\n");
                char[] mes = scan.next().toUpperCase().toCharArray();
                if(mes[0] == 'N'){
                    //return to the user default page
                    break;
                }else if (mes[0] == 'C'){
                    //return to the default page -> how to do that?
                    this.cancelBooking();
                    break;
                }
            }
        }
    }

    public void cancellingBookingForPerson(Person person,int num){
        this.user.cancelTicket(person,num);
    }

    public void cancelSeatForShow(char row, int col){
        try{
        this.showing.getMovieSeat().cancelReservation(row,col);}
        catch (Exception e){
            System.out.println("Invalid message cancel seat for show, please try again.");
        }
    }

    public void cancelBooking(){
        for(Person key:bookPerson.keySet()) {
            this.cancellingBookingForPerson(key, bookPerson.get(key));
            bookPerson.replace(key,0);
        }
        if(indexofarray != 0){
            for(int i = 0; i<indexofarray+1;i++){
                this.cancelSeatForShow(rowLetters[i],colNum[i]);
                rowLetters[i] = 'z';
                colNum[i] = 0; }
        }
        count = 0;
        indexofarray = 0;
        System.out.println("Successfully cancel.");
    }
}
