import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PokerGameTest {
	PokerGame game = new PokerGame();
	private String longInstructions	= 	"Black: 8D 8D AS QC KH  White: 2C 7H 4S 10C 10H\n" +  //single pair
										"Black: 2H 4S 8C 2D 4H  White: 6H 8S AC QS 3S\n"  +  //two pair
										"Black: 8D 9D 2S AC KH  White: 2C 3H 3S 3C 8H\n"  +  //3 of a kind
										"Black: 3H 5S 6C 7D 4H  White: 6C 8H AS QS 3S\n"  +  //straight
										"Black: 8D 5D AS QC KH  White: 2H 3H 4H 5H 8H\n"  +  //Flush
										"Black: 2H AS AC AD 2H  White: 2C 7H 4S 4C 4H\n"  +  //Full House
										"Black: 8D 8D 8S 7C KH  White: 2C AH AS AC AD\n" +   //4 of a kind
										"Black: 2H 4S 4C 2D 4H  White: 10H JH QH KH AH\n";   //Straight Flush
										
										
	//NOTE: 4 of a kind, 3 of a kind, and full houses cannot ever be tied
	private String junkTie	=	 "Black: 2D 7S 4C AS 6S  White: 2C 7H 4S AC 6H";  //junk 
	private String pairTie	=	 "Black: 2S 7D 4D AD AS  White: 2C 7H 4S AC AH";  //Pair Tie
	private String twoPairTie =  "Black: 8D 8C AD KC KH  White: 8H 8S AS KS KD";  //2 two pair
	private String straightTie = "Black: 2D 3C 4D 5C 6D  White: 2C 3H 4S 5D 6H";  //straight tie
	private String flushTie =	 "Black: 6H 8H AH QH 3H  White: 6S 8S AS QS 3S";  //flush tie
	private String strFlushTie = "Black: 10S JS QS KS AS  White: 10D JD QD KD AD";  //Straight Flush Tie
	
	//These hands will be used for nearly tied hands
	private String closeJunkTie	=	 "Black: 2D KS 4C AS 6S  White: 2C 7H 4S AC 6H";  //junk - Black should win
	private String closePairTie	=	 "Black: 2S 7D 4D AD AS  White: 2C 10H 4S AC AH";  //Pair Tie - White should win
	private String closeTwoPairTie =  "Black: 8D 8C 2D KC KH  White: 8H 8S AS KS KD";  //2 two pair - White should win
	private String closeStraightTie = "Black: 7D 3C 4D 5C 6D  White: 2C 3H 4S 5D 6H";  //straight tie - Black should win
	private String closeFlushTie =	 "Black: 6H 8H AH QH 3H  White: 6S 8S AS QS JS";  //flush tie - White should win
	private String closeStrFlushTie = "Black: 10S JS QS KS 9S  White: 10D JD QD KD AD";  //Straight Flush Tie - White should win. 
	
	private String closeTwoPair1 =  "Black: 10D 10C 2D KC KH  White: 2H 2S AS KS KD";  //2 two pair with different low pair - Black should win
	private String closeTwoPair2 =  "Black: 2H 2S AS KS KD  White: 10D 10C 2D KC KH";  //2 two pair with different low pair - Black should win
	
	//test different hands individually for both black and white to ensure results
	private String testPair1 = 		"Black: 8D 8D AS QC KH  White: 2C 7H 4S 10C 6H";
	private String testPair2 = 		"Black: 2C 7H 4S 10C AH  White: 2D 2D AS QC KH"; //any pair should beat Ace high
	
	private String test2Pair1 = 	"Black: 8D 8D AS AC KH  White: 2C 7H 4S 10C 6H";
	private String test2Pair2 = 	"Black: 2C 7H 10S 10C 6H  White: 8D 8D AS AC KH"; // 2 pair should be single pair
	
	private String test3Kind1 = 	"Black: 8D AD AS AC KH  White: 2C 7H 4S 9C 6H";
	private String test3Kind2 = 	"Black: 7C 7H 4S 6C 6H  White: 8D AD AS AC KH"; // 3 of a kind should beat 2 pair
	
	private String testStraight1 = 	"Black: 8D 9D 10S JC QH  White: 2C 7H 4S 9C 6H";
	private String testStraight2 = 	"Black: 2C 7H 9S 9C 9H  White: 8D 9D 10S JC QH"; //straight should beat a 3 of a kind
	
	private String testFlushHigh1 = "Black: 8D 9D AD QD KD  White: 2C 7H 4S 9C 6H";
	private String testFlushHigh2 = "Black: 8C 7H 5S 9C 6H  White: 8D 9D AD QD KD"; //Flush should beat a straight
	
	private String testStrFlushLow1 = 	"Black: 8D 8D AS QC KH  White: 2H 3H 4H 5H 6H";
	private String testStrFlushLow2 = 	"Black: 2H 3H 4H 5H 6H  White: 8D 9D 10S JC 10H"; //Flush should beat a straight
	
	private String testFullHouse1 = "Black: 8D 8D QS QC QH  White: 2C 7H 4S 9C 6H";
	private String testFullHouse2 = "Black: 7H 8H AH QH KH  White: 8D 8D QS QC QH"; //full house should beat a flush
	
	
	private String[] test1 = {"2D", "4D", "7S", "10C", "AH"}; //junk hand
	private String[] test2 = {"2D", "2D", "KS", "QC", "AH"}; //test single pair
	private String[] test3 = {"8D", "8D", "AH", "KC", "KH"}; //test test two pair
	private String[] test4 = {"8D", "8H", "8S", "8C", "KH"}; //test four of a kind
	private String[] test5 = {"AS", "KS", "QS", "JS", "10S"}; //test Royal Flush also test 10
	private String[] test6 = {"8C", "3C", "2C", "5C", "7C"}; //test flush
	private String[] test7 = {"7D", "8D", "9S", "10C", "JH"}; //test Straight
	private String[] test8 = {"8D", "8H", "8S", "KC", "KH"}; //test full house with the lowest value as 3 of a kind
	private String[] test9 = {"8D", "8S", "KS", "KC", "KH"}; //test full house with the high value as 3 of a kind
	private String[] test10 = {"2D", "10S", "10H", "10C", "AH"}; //test three of a kind
	
	//test your most standard and common hands, and ensure that different hands have proper precedence. 
	@Test
	void testBasicSingleHands() {	
		assertTrue(game.playGame(testPair1).equals("Black wins. - with Single Pair: 8"));
		assertTrue(game.playGame(testPair2).equals("White wins. - with Single Pair: 2"));
		
		assertTrue(game.playGame(test2Pair1).equals("Black wins. - with 2 pair: Aces and 8s"));
		assertTrue(game.playGame(test2Pair2).equals("White wins. - with 2 pair: Aces and 8s"));
		
		assertTrue(game.playGame(test3Kind1).equals("Black wins. - with Three of a kind: Ace"));
		assertTrue(game.playGame(test3Kind2).equals("White wins. - with Three of a kind: Ace"));
		
		assertTrue(game.playGame(testStraight1).equals("Black wins. - with Straight: Queen High"));
		assertTrue(game.playGame(testStraight2).equals("White wins. - with Straight: Queen High"));
		
		assertTrue(game.playGame(testFlushHigh1).equals("Black wins. - with Flush: Ace"));
		assertTrue(game.playGame(testFlushHigh2).equals("White wins. - with Flush: Ace"));
		
		assertTrue(game.playGame(testStrFlushLow1).equals("White wins. - with Straight Flush: 6 High"));
		assertTrue(game.playGame(testStrFlushLow2).equals("Black wins. - with Straight Flush: 6 High"));
		
		assertTrue(game.playGame(testFullHouse1).equals("Black wins. - with Full House: Queen over 8"));
		assertTrue(game.playGame(testFullHouse2).equals("White wins. - with Full House: Queen over 8"));
		}
	
	//test situations where the hands are nearly tied and kicker cards are the deciding factor in a tie

	@Test
	void testSingleHandTies() {
		assertTrue(game.playGame(junkTie).equals("Tie."));
		assertTrue(game.playGame(pairTie).equals("Tie."));
		assertTrue(game.playGame(twoPairTie).equals("Tie."));
		assertTrue(game.playGame(straightTie).equals("Tie."));
		assertTrue(game.playGame(flushTie).equals("Tie."));
		assertTrue(game.playGame(strFlushTie).equals("Tie."));
		
	}
	
	//test situations where the hands are nearly tied and kicker cards are the deciding factor in a victory
	@Test
	void testNearTies() {
		assertTrue(game.playGame(closeJunkTie).equals("Black wins. - with High Card: Ace"));
		assertTrue(game.playGame(closePairTie).equals("White wins. - with Single Pair: Ace"));
		assertTrue(game.playGame(closeTwoPairTie).equals("White wins. - with 2 pair: Kings and 8s"));
		assertTrue(game.playGame(closeStraightTie).equals("Black wins. - with Straight: 7 High"));
		assertTrue(game.playGame(closeFlushTie).equals("White wins. - with Flush: Ace"));
		assertTrue(game.playGame(closeStrFlushTie).equals("White wins. - with Straight Flush: Ace High"));
		
		assertTrue(game.playGame(closeTwoPair1).equals("Black wins. - with 2 pair: Kings and 10s"));
		assertTrue(game.playGame(closeTwoPair2).equals("White wins. - with 2 pair: Kings and 10s"));
	}
	
	@Test
	void testLongInstructions() 
	{
		assertTrue(game.playGame(longInstructions).equals(
				"Black wins. - with Single Pair: 8\n" + 
				"Black wins. - with 2 pair: 4s and 2s\n" + 
				"White wins. - with Three of a kind: 3\n" + 
				"Black wins. - with Straight: 7 High\n" + 
				"White wins. - with Flush: 8\n" + 
				"Black wins. - with Full House: Ace over 2\n" + 
				"White wins. - with Four of a kind: Ace\n" + 
				"White wins. - with Straight Flush: Ace High\n"));
	}
	
	//test the logic for the method of getting the results of a single hand
	@Test
	void testGetResults() 
	{
		String check1 = game.getResult(test1);
		String check2 = game.getResult(test2);
		String check3 = game.getResult(test3);
		String check4 = game.getResult(test4);
		String check5 = game.getResult(test5);
		String check6 = game.getResult(test6);
		String check7 = game.getResult(test7);
		String check8 = game.getResult(test8);
		String check9 = game.getResult(test9);
		String check10 = game.getResult(test10);
		
		assertTrue("0 14 cards: 14 10 7 4 2".equals(check1));
		assertTrue("1 2 cards: 14 13 12 2 2".equals(check2));
		assertTrue("2 13 over 8 cards: 14 13 13 8 8".equals(check3));
		assertTrue("7 8 cards: 13 8 8 8 8".equals(check4));
		assertTrue("8 14 cards: 14 13 12 11 10".equals(check5));
		assertTrue("5 8 cards: 8 7 5 3 2".equals(check6));
		assertTrue("4 11 cards: 11 10 9 8 7".equals(check7));
		assertTrue("6 8 over 13 cards: 13 13 8 8 8".equals(check8));
		assertTrue("6 13 over 8 cards: 13 13 13 8 8".equals(check9));
		assertTrue("3 10 cards: 14 10 10 10 2".equals(check10));
	}

}
