package R18_G2_ASM2;

public class StaffScreen extends Screen{
  private EditStaffScreen editStaffScreen;
  private StaffAddMovieScreen addMovieScreen;
  private StaffDeleteMovieScreen deleteMovieScreen;
  private AddShowing addShowing;
  private StaffBookingSummaryScreen bookingSummaryScreen ;
  private UpdateGiftCardsScreen gcs;
  private ComingMovieReport cmr;
  private StaffUpdateMovieScreen staffUpdateMovieScreen;

  public StaffScreen(MovieSystem mSystem) {
    super(mSystem);

    if (mSystem.getUser().getUserType() == UserType.GUEST ||
        mSystem.getUser().getUserType() == UserType.CUSTOMER) {
      throw new IllegalArgumentException("Invalid user type for this screen");
    }
    title = "STAFF PAGE";
  }

  @Override
  protected void chooseOption() {
    switch (selectedOption) {
      case "1"://TODO check it works
        //to update gift card status
        gcs = new UpdateGiftCardsScreen(mSystem);
        gcs.print();
        break;

      case "2": //TODO check it works
        addMovieScreen = new StaffAddMovieScreen();
        try{
          addMovieScreen.retriveMoiveInfo();

        }catch (Exception e){
          e.printStackTrace();
        }
        break;

      case "3": //TODO check it works
        deleteMovieScreen = new StaffDeleteMovieScreen();
        try{
          deleteMovieScreen.retriveMoiveInfo();

        }catch (Exception e){
          e.printStackTrace();
        }
        break;

      case "4"://TODO add functionality
        staffUpdateMovieScreen = new StaffUpdateMovieScreen();
        try{
          staffUpdateMovieScreen.run();

        }catch (Exception e){
          e.printStackTrace();
        }
        break;

      case "5": //TODO check it works
        addShowing = new AddShowing(mSystem);
        addShowing.run();
        break;

      case "6"://TODO check it works
        cmr = new ComingMovieReport(mSystem);
        cmr.run();
      break;

      case "7": //TODO check it works
        bookingSummaryScreen =  new StaffBookingSummaryScreen();
      try{
        bookingSummaryScreen.run();

      }catch (Exception e){
        e.printStackTrace();
      }
      break;

      case "8": //TODO add functionality
        TransactionSummary.printTransactionSummary();
      break;

      case "9": ///TODO check it works
        editStaffScreen = new EditStaffScreen(mSystem);
        editStaffScreen.run();
       break;

      case "Q": case "q":
        System.out.print("SEE YOU NEXT TIME! :)\n");
        System.exit(0);
        break;
      default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
    }
    
  }

  @Override
  protected void setOptions() {

    if (mSystem.getUser().getUserType() == UserType.STAFF) {
      maxInputInt = 7;
    } else if (mSystem.getUser().getUserType() == UserType.MANAGER)  {
      maxInputInt = 9;
    } else {
      throw new IllegalArgumentException("Invalid user type for this screen");
    }
    options.add("q"); options.add("Q");
  }

  @Override
  public void print() {
    clearScreen();
    printHeader();
    printOptionsText();

    //TODO Checkover wording once parts implemented
    System.out.print(formatANSI("1",ANSI_USER_OPTION) + " - Add/Reactivate gift card\n");
    System.out.print(formatANSI("2",ANSI_USER_OPTION) + " - Add a new movie to the database\n");
    System.out.print(formatANSI("3",ANSI_USER_OPTION) + " - Remove an existing movie from the database\n");
    System.out.print(formatANSI("4",ANSI_USER_OPTION) + " - Update an existing movie in the database\n"); 
    System.out.print(formatANSI("5",ANSI_USER_OPTION) + " - Add new showings\n"); 
    System.out.print(formatANSI("6",ANSI_USER_OPTION) + " - Generate a movie showings report (For the following week)\n");  
    System.out.print(formatANSI("7",ANSI_USER_OPTION) + " - Generate a booking summary report\n");  

    if (mSystem.getUser().getUserType() == UserType.MANAGER) {
      System.out.print(formatANSI("8",ANSI_USER_OPTION) + " - Generate a cancelled transactions report\n");  
      System.out.print(formatANSI("9",ANSI_USER_OPTION) + " - Edit staff member\n");
//      System.out.print(formatANSI("9",ANSI_USER_OPTION) + " - Remove an existing staff member\n");
    }
    System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Quit and logout.\n\n");  
  }


}
