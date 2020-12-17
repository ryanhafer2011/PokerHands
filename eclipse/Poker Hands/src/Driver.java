
public class Driver {

	public static void main(String args[])
	{
		
		String instructions2	=   "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH\n" + 
									"Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S\n" + 
									"Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH\n" + 
									"Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH";


		PokerGame game = new PokerGame();
		System.out.print(game.playGame(instructions2));
	}
}
