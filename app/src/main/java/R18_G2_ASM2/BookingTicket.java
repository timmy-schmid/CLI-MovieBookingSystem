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
    private boolean cancelBook = false;
    // cancel is true


    public BookingTicket(Showing showing, User user){
        this.showing = showing;
        try {
            this.showing.setMovieSeatForTest();
        }catch (Exception e){
            System.out.println("aaaa");
        }

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
            this.cancelBook = false;
            this.askForBooking();
            if(this.cancelBook){
                break;
            }
            for(int i =0; i<count; i++){
                this.bookingShowingSection();
                if (this.cancelBook){
                    break;
                }
                this.bookingASeat();
                if (this.cancelBook){
                    break;
                }
                System.out.println(count-i-1);
                if(!this.checkFullorNot()){
                    this.cancelBooking();
                    break;
                }
            }
            System.out.println("Do you want to continue? Y for booking and other input for exit");
            String str = scan.next();
            if(str.equals("Y")){}else{
                this.printBookingMessage();
                break;
            }
        }
    }

    public boolean checkFullorNot(){
        if(showing.isShowingFull()){
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
                } else if (num.equals("C")) {
                   this.cancelBooking();
                   this.cancelBook = true;
                    break;
                } else {
                    System.out.println("Invalid input,please try again");
                }
            }
            if(!cancelBook){
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
                if(this.cancelBook){
                    break;
                }else{
                    Ct =this.Continue();
                    if(Ct == 2 || Ct == -1 ){
                        Ct = 1;
                        break;
                    }
                }
            }else{break;}
        }
    }


    public int Continue(){
        Scanner scan = new Scanner(System.in);
        while(true){
            if(this.cancelBook){
                return -1;
            }
            System.out.println("1-Continue\n" +
                    "2-End Adding Person\n"+"C-Cancel\n");
            String str = scan.next();

            if(str.equals("1")){return 1;}
            else if(str.equals("2")){
                return 2;
            }else if(str.equals("C")){
                this.cancelBooking();
                this.cancelBook =true;
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
            System.out.println("error message something wrong in the bookseat");
            return false;
        }
    }

    public void bookingShowingSection(){
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("Please choose the section --in total Left : " + this.showing.getMovieSeat().totalSeatsLeft());
            System.out.println("1-Front        "+"Already be booked: " +Integer.toString(this.showing.getMovieSeat().frontSeatBooked()));
            System.out.println("2-Middle       "+"Already be booked: "+Integer.toString(this.showing.getMovieSeat().middleSeatBooked()));
            System.out.println("3-Rear         "+"Already be booked: "+Integer.toString(this.showing.getMovieSeat().rearSeatBooked()));
            System.out.println("4-All          "+"Already be booked: "+Integer.toString(this.showing.getMovieSeat().totalSeatsBooked()));
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
                this.showing.getMovieSeat().showAllSeats();
                break;
            }else if(str.equals("Cancel")){
                this.cancelBooking();
                this.cancelBook = true;
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
                    boolean sc = this.bookingTicketForSeat(rowLetter,col);
                    if(!sc){
                        throw new Exception();
                    }
                    rowLetters[indexofarray] = rowLetter;
                    colNum[indexofarray] = col;
                    indexofarray++;
                    break;
                }catch (Exception e){
                    System.out.println("Invalid input, please try again.");
                    System.out.println("Do you want to continue? [Y/C]\n"+"C -cancel\n");
                    char[] mes = scan.next().toUpperCase().toCharArray();
                    if(mes[0] == 'C'){
                        // return to the welcome page -> how to deal with that?
                        this.cancelBooking();
                        this.cancelBook = true;
                        break;
                    }
                }
            }else{
                System.out.println("Invalid message, please try again.");
                System.out.println("Do you want to continue? [Y/N]\n"+"C -cancel\n");
                char[] mes = scan.next().toUpperCase().toCharArray();
                if(mes[0] == 'C'){
                    // return to the welcome page -> how to deal with that?
                    this.cancelBooking();
                    this.cancelBook = true;
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
        this.printBookingMessage();
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
        // return to the welcome page ->
    }
}
