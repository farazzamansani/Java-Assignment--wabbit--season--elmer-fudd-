/**
 * KIT101 Assignment 2
 *
 * Wabbit Season -- Organiser Class
 *
 * @author Faraz Zamansani 386070 (farazz)
 * @version 1.1
 *
 * Stage Reached: 2(user is asked if they want to play at start and end of games but it does not remember user moves)
 *
 * This code is a game called Wabbit Season in which the user hunts down wabbits or ducks
 */

import java.util.Scanner;


public class WabbitSeason {
  
  // Non-final instance variables
  Boolean tracing=false; //this enables/disables tracing
  Boolean season=false; //this is ssent to toonbot to tell it if its wabbit or duck season
  int play=1;//this is used for a while loop, if user wants to play or not
  int failedshots=0;//this records how many missed shots the user makes
  String wabbitorduckseason; //used for user choosing what season they want
  String currenttarget; //stores current target
  int wheretoshoot=0;//used for user choosing where to shoot
  int wheretowalk=0;//used for user choosing where to walk
  
  
  public void explain() {
    //method explains game to user
    System.out.println("welcome to wabbit season");
    System.out.println("you can choose to either hunt rabbits or ducks");
    System.out.println("you are in a zoo, the areas are 0-9");
    System.out.println("you need to hunt down your target and shoot them with darts 3 times to win");
    System.out.println("if you run into the same area as your target you become enraged and they flee");
    System.out.println("you lose if you miss 3 times, or run into the same area as the target while enraged");
  }
  
  
  public void play() {
    // method to play
    Scanner input = new Scanner(System.in);
    System.out.println("Would you like to play wabbit Season? yes/no"); //asks user if they would like to play
    String yesorno = input.next();
    Scanner op = new Scanner(System.in);
    if (!yesorno.equals ("yes"))//if user chooses anything other then "yes" we exit
    {
      System.exit(0); 
    }
    
    explain();//calls explain to explain the game to user
    ToonBot toony;
    toony = new ToonBot(false);
    
    
    System.out.println("wabbit season or duck season?"); //asks user what they would like to hunt
    wabbitorduckseason = input.next();
    Scanner op1 = new Scanner(System.in);
    if (wabbitorduckseason.equals ("wabbit"))//if users input is wabbit we set season to true
    {
      System.out.println("you chose wabbit season");
      season=true;
    }
    else{ //if user types anything other then wabbit then we set season to false making it duck season
      System.out.println("you chose duck season");   
      season=false;
    }
    toony.setTracing(tracing);//if true it traces, if false it doesnt
    toony.newGame(season);//this tells ToonBot cod what season we are using
    
    while (play==1)//the game is ran within this while loop, as long as play is 1 the user wants to play, if they say they dont want to play this becomes 0 and the loop ends, ending the game
    {
      System.out.println("you have missed "+failedshots);//tells user how many times they have missed
      Result istargetnear=toony.targetNear();//finds out if target is nearby
      if (istargetnear==Result.ENRAGED)//if user runs into same area as rabbit they become enraged
      {
        System.out.println("you are "+istargetnear);//this tells user they are enraged
      }
      else //if user isnt enraged we tell the user more information
      {
        if (istargetnear==Result.NEAR)//if target is near we tell the player
        {
          System.out.println("you can hear the target nearby ");//this tells user they are enraged
        }
        if (istargetnear==Result.FAR)//if target is far we tell them they are far
        {
          System.out.println("you cant hear the target, they must be far away ");//this tells user they are enraged
        }  
        currenttarget=toony.getTarget();
        System.out.println("your current target is  "+currenttarget);
        String currentshots=toony.getShotCount();
        System.out.println("successful shots:  "+currentshots);
      }
      int currentarea=toony.getCurrentArea();
      System.out.println("you are currently in area "+currentarea);//regardless if the user is enraged or not we tell the their current area
      
      
      int areconnected= toony.nextArea('n');
      System.out.print("you can interact with area's "+areconnected);//we find out from nextArea where the user can go then tell the player
      areconnected= toony.nextArea('e');
      System.out.print(","+areconnected);
      areconnected= toony.nextArea('s');
      System.out.print(","+areconnected);
      areconnected= toony.nextArea('w');
      System.out.println(","+areconnected);
      
      
      
      Scanner actioninput = new Scanner(System.in);
      System.out.println("1 to move, 2 to shoot, 3 to reset or 4 to end the game");//tell the user what to input for shoot etc
      int action=0;
      action = actioninput.nextInt();
      Scanner actioninput1 = new Scanner(System.in);
      
      if (action==1)//if user types 1 (move) we do this
      {
        
        Scanner movetoinput = new Scanner(System.in);
        System.out.println("where would you like to move?");//ask where they want to move
        wheretowalk = movetoinput.nextInt();
        
        Result resulting=toony.tryWalk(wheretowalk);
        if (resulting==Result.IMPOSSIBLE)
        {
          System.out.println("The area you have chosen is not connected to your current position, you cannot move there ");
        }
        if (resulting==Result.SUCCESS)
        {
          System.out.println("You have succesfuly moved to "+wheretowalk);
        }
        if (resulting==Result.FAILURE)
        {
          System.out.println("You have moved into the targets area while enraged and shot yourself in the foot");
          System.out.println("Would you like to play again? (yes/no)"); //asks user if they would like to play again
          yesorno = input.next();
          if (!yesorno.equals ("yes"))//if user chooses anything other then "yes" we exit
          {
            System.exit(0); 
          } 
          toony.reset(); //if user chooses yes we reset the game to play again
        }
        
      }
      if (action==2)//if user types 2 (shoot) we do this
      {
        Scanner shoottoinput = new Scanner(System.in);
        System.out.println("where would you like to shoot?");//ask where they want to shoot
        wheretoshoot = shoottoinput.nextInt();
        
        Result resulting=toony.shootDart(wheretoshoot);
        System.out.println("result is "+resulting);
        
        if (resulting==Result.CAPTURED)//we check if we have captured the wabbit/duck if we have we tell the player they won
        {
          System.out.println("congratz you win!!!!!!! you "+resulting+" "+currenttarget);
          System.out.println("Would you like to play again? (yes/no)"); //asks user if they would like to play again
          yesorno = input.next();
          if (!yesorno.equals ("yes"))//if user chooses anything other then "yes" we exit
          {
            System.exit(0); 
          } 
          toony.reset(); //if user chooses yes we reset the game to play again
          
        }
        if (resulting==Result.FAILURE)//if the player fails we add 1 to failed shots
        {
          failedshots++;
          System.out.println("you have disturbed the other animals in the zoo ");
        }
        if (failedshots==3)//if failed shots gets to 3 we tell the player they lost
        {
          System.out.println("you have disturbed the other animals in the zoo too much and have been arrested ");  
          failedshots=0;
          System.out.println("Would you like to play again? (yes/no)"); //asks user if they would like to play again
          yesorno = input.next();
          if (!yesorno.equals ("yes"))//if user chooses anything other then "yes" we exit
          {
            System.exit(0); 
          } 
          toony.reset(); //if user chooses yes we reset the game to play again
          
        }
      }
      if (action==3)//if user chooses action 3 the game resets
      {
        System.out.println("Reseting.... ");
        toony.reset();
        failedshots=0;
      }
      if (action==4)//if user chooses action 4 the game ends
      {
        System.out.println("Game ending.... ");
        play=0;
      }
    }
    if (play==0)
    {
      System.exit(0); 
    }
  }
  
  
  public void setTracing(boolean onOff) {//just used for tracing
    tracing = onOff;
  }
  
  
  public void trace(String message) {//just used for tracing
    if (tracing) {
      System.out.println("WabbitSeason: " + message);
    }
  }
  
}
