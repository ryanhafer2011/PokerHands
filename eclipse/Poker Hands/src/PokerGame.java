import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class PokerGame {

	private final int MAX_HAND_SIZE = 5;

	String bHand[] = new String[MAX_HAND_SIZE];
	String wHand[] = new String[MAX_HAND_SIZE];

	String bResult = "";
	String wResult = "";

	/**
	 * simulates rounds of poker from instructions given in as a string and prints out the results prints the
	 * results in the console
	 * 
	 * @return a copy of the results as a string
	 */
	public String playGame(String inputInstructions) {
		Scanner inputScan = new Scanner(inputInstructions);

		ArrayList<String> lines = new ArrayList<String>();
		String blackRes = "";
		String whiteRes = "";
		String result = "";
		boolean whiteWin = false;
		boolean blackWin = false;
		
		// break up the instructions into individual lines
		while (inputScan.hasNext()) {
			lines.add(inputScan.nextLine());
		}

		// calculate the result of each hand line by line, one per complete for loop
		// here
		for (int i = 0; i < lines.size(); i++) 
		{
			String lineResults = "";
			// splits on spaces
			String[] pieces = lines.get(i).split(" ");

			// add the cards for black to the hand
			for (int j = 0; j < MAX_HAND_SIZE; j++) {
				bHand[j] = pieces[j + 1];
			}

			// add the cards for white to the hand
			for (int j = 0; j < MAX_HAND_SIZE; j++) {
				wHand[j] = pieces[j + 8];
			}

			// pass hands off to method to calculate winner
			blackRes = getResult(bHand);
			whiteRes = getResult(wHand);
			
			String[] blackVals = blackRes.split(" ");
			String[] whiteVals = whiteRes.split(" ");
			
			//get the kicker card values in case of ties
			ArrayList<Integer> blackKickers = new ArrayList<Integer>();
			ArrayList<Integer> whiteKickers = new ArrayList<Integer>();
			
			for(int k = 0; k < MAX_HAND_SIZE; k++)
			{
				blackKickers.add(Integer.parseInt(blackVals[blackVals.length - k - 1]));
				whiteKickers.add(Integer.parseInt(whiteVals[whiteVals.length - k - 1]));
			}
			
			//reverse the kickers so the lowest array slot contains the higher value
			Collections.reverse(blackKickers);
			Collections.reverse(whiteKickers);
			
			
			//figure out which hand is better, and if there is a tie, return a tie
			if(Integer.parseInt(blackRes.substring(0, 1)) > Integer.parseInt(whiteRes.substring(0, 1)))
			{
				lineResults = "Black wins. - with ";
				blackWin = true;
			} 
			else if (Integer.parseInt(whiteRes.substring(0, 1)) > Integer.parseInt(blackRes.substring(0, 1)))
			{
				lineResults = "White wins. - with ";
				whiteWin = true;
			} 
			else if (Integer.parseInt(whiteRes.substring(0, 1)) == Integer.parseInt(blackRes.substring(0, 1))) 
			{
				if(Integer.parseInt(whiteRes.substring(2, 3)) > Integer.parseInt(blackRes.substring(2, 3)))
				{
					lineResults = "White wins. - with ";
					whiteWin = true;
				} 
				else if (Integer.parseInt(whiteRes.substring(2, 3)) < Integer.parseInt(blackRes.substring(2, 3)))
				{
					lineResults = "Black wins. - with ";
					blackWin = true;
				} 
				else if (Integer.parseInt(whiteRes.substring(2, 3)) == Integer.parseInt(blackRes.substring(2, 3)))
				{
					//implement logic for specific cases of ties of both rank and first rank value
					
					//Straight Flush -- if two straight flushes have the same high value return a tie.
					if(Integer.parseInt(whiteVals[0]) == 8)
					{
						for (int k = 0; k < MAX_HAND_SIZE; k++)
						{
							if(blackKickers.get(k) > whiteKickers.get(k))
							{
								lineResults = "Black wins. - with ";
								blackWin = true;
								break;
							} 
							else if (blackKickers.get(k) < whiteKickers.get(k))
							{
								lineResults = "White wins. - with ";
								whiteWin = true;
								break;
							}
						}
						
						if(!(blackWin || whiteWin))
						{
							return "Tie.";
						}
					}
					
					//4 of a kind cannot tie
					
					//Full houses cannot tie
					
					//Flush -- if two flushes have the same high value then sequentially check each of the kicker cards
					//until a winner is found
					if(Integer.parseInt(whiteVals[0]) == 5)
					{
						for (int k = 0; k < MAX_HAND_SIZE; k++)
						{
							if(blackKickers.get(k) > whiteKickers.get(k))
							{
								lineResults = "Black wins. - with ";
								blackWin = true;
								break;
							} 
							else if (blackKickers.get(k) < whiteKickers.get(k))
							{
								lineResults = "White wins. - with ";
								whiteWin = true;
								break;
							} 
						}
						
						//if the flushes contain the exact same values, return a tie
						if(!(blackWin || whiteWin))
						{
							return "Tie.";
						}
					}
					
					//Straight -- if two straight have the same high value return a tie 
					if(Integer.parseInt(whiteVals[0]) == 4)
					{
						return "Tie.";
					}
					
					//three of a kind cannot tie
					
					//two pair -- if a two pair tie on the first value, look at the second pair, if those tie as well, check the final card 
					if(Integer.parseInt(whiteVals[0]) == 2)
					{
						if(Integer.parseInt(blackVals[3]) > Integer.parseInt(whiteVals[3]))
						{
							lineResults = "Black wins. - with ";
							blackWin = true;
						} 
						else if (Integer.parseInt(blackVals[3]) < Integer.parseInt(whiteVals[3]))
						{
							lineResults = "White wins. - with ";
							whiteWin = true;
						}
						
						if(!(blackWin || whiteWin))
						{
							int tBlk = -1;
							int tWht = -1;
						
							//if the second pairs are equal as well then try the final card
							for (int k = 0; k < MAX_HAND_SIZE; k++)
							{
								if(!(blackKickers.get(k) == Integer.parseInt(blackVals[1]) 
									|| (blackKickers.get(k) == Integer.parseInt(blackVals[3]))))
								{
									tBlk = blackKickers.get(k);
								}
 
								if(!(whiteKickers.get(k) == Integer.parseInt(whiteVals[1]) 
										|| (whiteKickers.get(k) == Integer.parseInt(whiteVals[3]))))
								{
									tWht = whiteKickers.get(k);
								}
							 
							}
						
						if(tBlk > tWht)
						{
							lineResults = "Black wins. - with ";
							blackWin = true;
							
						} 
						else if(tBlk < tWht)
							{
							lineResults = "White wins. - with ";
							whiteWin = true;
							
							}
						}
						
						if(!(blackWin || whiteWin))
						{
							return "Tie.";
						}
						
						
					}
					
					//Pair -- if the a set of pairs have the same value, the 3 kicker cards are compared starting with 1st highest in each hand, then 2nd, then 3rd, then if no winner is decided, it is a tie
					if(Integer.parseInt(whiteVals[0]) == 1)
					{
						for (int k = 0; k < MAX_HAND_SIZE; k++)
						{
							if(blackKickers.get(k) > whiteKickers.get(k) 
									&& !(Integer.parseInt(blackVals[1]) == blackKickers.get(k)) 
									&& !(Integer.parseInt(whiteVals[1]) == whiteKickers.get(k)))	
							{
								lineResults = "Black wins. - with ";
								blackWin = true;
								break;
							} 
							else if(blackKickers.get(k) < whiteKickers.get(k) 
									&& !(Integer.parseInt(blackVals[1]) == blackKickers.get(k)) 
									&& !(Integer.parseInt(whiteVals[1]) == whiteKickers.get(k)))
							{
								lineResults = "White wins. - with ";
								whiteWin = true;
								break;
							} 
						}
						
						if(!(blackWin || whiteWin))
						{
							return "Tie.";
						}
					}
					
					//Pair -- if the hand has no synergy, check for which hand has the highest card
					if(Integer.parseInt(whiteVals[0]) == 0)
					{
						for (int k = 0; k < MAX_HAND_SIZE; k++)
						{
							if(blackKickers.get(k) > whiteKickers.get(k))
							{
								lineResults = "Black wins. - with ";
								blackWin = true;
								break;
							} 
							else if (blackKickers.get(k) < whiteKickers.get(k))
							{
								lineResults = "White wins. - with ";
								whiteWin = true;
								break;
							}
						}
						if(!(blackWin || whiteWin))
						{
							return "Tie.";
						}
					}
				}
			}
			
			
			String winningRes = "";
			
			//set winning results to a new result to avoid superfluous switch statements
			if(whiteWin)
			{
				winningRes = whiteRes;
			} 
			else 
			{
				winningRes = blackRes;
			}
			
			String[] winPieces = winningRes.split(" ");
			
			//replace values with their appropriate names
			for(int j = 0; j < winPieces.length; j++)
			{
				if(winPieces[j].equals("14"))
				{
					winPieces[j] = "Ace";
				} 
				else if (winPieces[j].equals("13"))
				{
					winPieces[j] = "King";
				}
				else if (winPieces[j].equals("12"))
				{
					winPieces[j] = "Queen";
				}
				else if (winPieces[j].equals("11"))
				{
					winPieces[j] = "Jack";
				}
			}
			
			//based on the value of the winning hand, store the result here
			if(winningRes.substring(0, 1).equals("8"))
			{
				lineResults += "Straight Flush: " + winPieces[1] + " High";
			} 
			else if (winningRes.substring(0, 1).equals("7"))
			{
				lineResults += "Four of a kind: " + winPieces[1];
			}
			else if (winningRes.substring(0, 1).equals("6"))
			{
				lineResults += "Full House: " + winPieces[1] + " over " + winPieces[3];
			}
			else if (winningRes.substring(0, 1).equals("5"))
			{
				lineResults += "Flush: " + winPieces[1];
			}
			else if (winningRes.substring(0, 1).equals("4"))
			{
				lineResults += "Straight: " + winPieces[1] + " High";
			}
			else if (winningRes.substring(0, 1).equals("3"))
			{
				lineResults += "Three of a kind: " + winPieces[1];
			}
			else if (winningRes.substring(0, 1).equals("2"))
			{
				lineResults += "2 pair: " + winPieces[1] + "s and "  + winPieces[3] + "s";
			}
			else if (winningRes.substring(0, 1).equals("1"))
			{
				lineResults += "Single Pair: " + winPieces[1];
			}
			else if (winningRes.substring(0, 1).equals("0"))
			{
				lineResults += "High Card: " + winPieces[1];
			}
			
			whiteWin = false;
			blackWin = false;
			
			if(lines.size() > 1)
			{
				result += lineResults + "\n";
			} else {
				result += lineResults;
			}
		}
		
		
		inputScan.close();
		return result;
	}

	/**
	 * A method with the logic that compares two hands and returns the result
	 * 
	 * @param hand the hand to pass in to check
	 * @return The results for that hands
	 */
	public String getResult(String[] inputHand) {
		String retVal = "";

		boolean flush = true;
		boolean straight = true;
		boolean pair = false;
		boolean twoPair = false;
		boolean threeOfAKind = false;
		boolean fourOfAKind = false;
		boolean fullHouseLow = false;
		int pairVal1 = -1;
		int pairVal2 = -1;
		
		//check both hands to see if each contains 5 cards of the same suit
		for(int i = 0; i < 5; i++)
		{
			if(inputHand[0].substring(0,2).equals("10"))
			{
				if(inputHand[i].substring(0,2).equals("10"))
				{
					flush = flush && (inputHand[0].substring(2, 3).equals(inputHand[i].substring(2, 3)));
				}
				else
				{
					flush = flush && (inputHand[0].substring(2, 3).equals(inputHand[i].substring(1, 2)));
				}
			} 
			else if(inputHand[i].substring(0,2).equals("10"))
			{
				flush = flush && (inputHand[0].substring(1, 2).equals(inputHand[i].substring(2, 3)));
			}
			else
			{
				flush = flush && (inputHand[0].substring(1, 2).equals(inputHand[i].substring(1, 2)));
			}
		}
		
		//check both hands if they each contain a straight
		
		int[] valArray = new int[5];
		
		//loop through the inputHand and whiteHand and get the values associated with the card 2-14
		//put those into an array to be sorted
		for(int i = 0; i < MAX_HAND_SIZE; i++)
		{
			int black = -1;
			
			//convert Ace through jack into correlating values
			if(inputHand[i].substring(0, 1).equalsIgnoreCase("A"))
			{
				black = 14;
			} 
			else if (inputHand[i].substring(0, 1).equalsIgnoreCase("K"))
			{
				black = 13;
			} 
			else if (inputHand[i].substring(0, 1).equalsIgnoreCase("Q"))
			{
				black = 12;
			} 
			else if (inputHand[i].substring(0, 1).equalsIgnoreCase("J"))
			{
				black = 11;
			} 
			else if (inputHand[i].substring(0, 2).equalsIgnoreCase("10"))
			{
				black = 10;
			} 
			else
			{
				black = Integer.parseInt(inputHand[i].substring(0, 1));
			}

			
			valArray[i] = black;
		}
		
		//sort the resultant integer values of the card
		Arrays.sort(valArray);
		
		//check if ints are sequential 
		for(int i = 0; i < valArray.length - 1; i++)
		{
			if(straight)
			{
				straight = ((valArray[i] + 1) == (valArray[i + 1]));
			}
		}

		
		
		//check if there are pairs
		for (int i = 0; i < valArray.length; i++)
		{
			int counter = 0;
			int tVal = -1;
			
			for (int j = 0; j < valArray.length; j++)
			{
				if(valArray[i] == valArray[j] && (i != j ) && (pairVal1 != valArray[i]))
				{
					counter++;
					tVal = valArray[i];
				}
			}
			
			if(counter > 0 && pairVal1 == -1)
			{
				pairVal1 = tVal;
				pair = true;

				
				if(counter == 2)
				{
					threeOfAKind = true;
				}
				
				if(counter == 3)
				{
					fourOfAKind = true;
				}
			} 
			else if (counter > 0 && pairVal2 == -1 && pair)
			{
				pairVal2 = tVal;
				twoPair = true;
				
				if(counter == 2)
				{
					threeOfAKind = true;
				}
				
				if(counter == 3)
				{
					fourOfAKind = true;
				}
			}
		}
		
		//check if there is a full house, if there is, check whether the lower value has the three pair
		if(threeOfAKind && twoPair)
		{	
			int counter = 0;
			for(int i = 0; i < 5; i++)
			{
				if(pairVal1 == valArray[i])
				{
					counter++;
				}
			}
			if(counter == 2)
			{
				fullHouseLow = true;
			}
		}
	
		String tString = "";
		
		for(int i = 4; i > -1; i--)
		{	
			if (i > 0)
			{
				tString += + valArray[i] + " ";
			} 
			else 
			{
				tString += + valArray[i] + "";
			}
		}
		
		if(flush && straight)
		{
			return "8 " + valArray[4] + " cards: " + tString;
		} 
		else if (fourOfAKind)
		{
			return "7 " + pairVal1 + " cards: " + tString;
		} 
		else if (threeOfAKind && twoPair)
		{	
			if(fullHouseLow)
			{
				return "6 " + pairVal2 + " over " + pairVal1 + " cards: " + tString;
			}
			
			return "6 " + pairVal1 + " over " + pairVal2 + " cards: " + tString;
		}
		else if (flush)
		{
			return "5 " + valArray[4] + " cards: " + tString;
		}
		else if (straight)
		{
			return "4 " + valArray[4] + " cards: " + tString;
		}
		else if (threeOfAKind)
		{
			return "3 " + pairVal1 + " cards: " + tString;
		}
		else if (twoPair)
		{
			if(pairVal1 > pairVal2)
			{
				return "2 " + pairVal1 + " over " + pairVal2 + " cards: " + tString;
			}
			
			return "2 " + pairVal2 + " over " + pairVal1 + " cards: " + tString;
		}
		else if (pair)
		{
			return "1 " + pairVal1 + " cards: " + tString;
		}
		else
		{
			return "0 " + valArray[4] + " cards: " + tString;
		}
	}
}
