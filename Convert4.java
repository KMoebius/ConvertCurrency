import java.util.*;
import java.math.*;

class Convert4
{
 	private String[] suffix = {"","thousand", "million", "billion", "trillion", "quadrillian", "quintillion",
	 			   "sextillion", "septillion", "octillion","nonillion","decillion","undecillion","duodecillion","tredecillion",
	  			   "quatttuor-decillion","quinecillion","sexdecillion","septen-decillion","octodecillion"};
 	private String[] numToTwenty = {"","one","two","three","four","five","six","seven","eight",
					"nine","ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
					"sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
	private String[] tensPlaces = {"","ten","twenty","thirty","fourty","fifty","sixty","seventy","eighty","ninety"};
	
	/*
	* Takes the origional value and prints out the 
	* string representation.
	*/
	private void doStuff(String origValue)
	{
		String myOrigValue = origValue;
		String cents;
		String dollars;
		//The final output buffer
		StringBuilder myFinalStringBuilder = new StringBuilder();
		if(checkValidity(myOrigValue))
		{
			//Grabs the dollar string value
			dollars = getDollars(myOrigValue);
			//Grags the cents string value
			cents = getCents(myOrigValue);
		
			//Puts the dollar and cents together
			myFinalStringBuilder.append(dollars);
			myFinalStringBuilder.append(cents);
	
			//Capatitalize the first letter
			String firstCharacter = myFinalStringBuilder.substring(0,1).toUpperCase();
			myFinalStringBuilder.replace(0,1,firstCharacter);
			System.out.println(myFinalStringBuilder.toString());
		}
		else
		{
			System.out.println("Poorly formated number");
			System.exit(1);
		}
	
	}

	
	/*
	* Takes a string value and checks that it only contains numbers
	* and exactly 1 decimal point. Returns True if there is only
	* numbers and at most 1 decimal point. Returns False otherwise
	*/
	private boolean checkValidity(String theValue)
	{
		//Checks that the value provided only contains and at least 1 numbers
		//and at most 1 decimal point and at least one number
		// Returns false if theses are not met
		boolean retVal;
		if  ((!(theValue.matches("^[0-9]*\\.?[0-9]*$"))) || (!centsIsValid(theValue)) || theValue.equals("."))
		{
			retVal = false;
		}
		else
		{
			retVal = true;
		}
		return retVal;
	}
	
	/*
	* Takes an input value and checks that the cents 
	* value (after the decmal) of the string is valid
	* Must not be longer than 2 characters.
	*/
	private boolean centsIsValid(String val)
	{

		String cents= "00";
		//If there is a period and it is not the last character pull out cents
		if( (val.indexOf(".") != -1 ) && (val.indexOf(".") != (val.length()-1) ))
		{ 
			cents = val.substring(val.indexOf(".")+1);
		}	
		//return false only if cents is > 2
		return (!(cents.length() > 2));
		
	}
	
	
	/*
	* Takes a value and returns the portion after the '.'
	* If the cents portion has one number it will add 
	* a trailing 0.
	*/
	private String getFormatCents(String val)
	{
		String cents;
		//Get the cents and format it to be exactly 2 characters long

		if( (val.indexOf(".") != -1 ) && (val.indexOf(".") != (val.length()-1) ))
		{ 
			cents = val.substring(val.indexOf(".")+1);
		}	
		else
		{
			cents = "00";
		}
		//If there is only one demical place
		if (cents.length() == 1)
		{
			//add a trailing 0
			cents = cents + "0";
		}
		
		return cents;
	}
	
	/*
	* Returns the cents portion of the final string
	* formated for final output
	*/
	private String getCents(String val)
	{
		StringBuilder tcents = new StringBuilder();
		String cents = getFormatCents(val);
		
		
		tcents.append("and ");
		tcents.append(cents);
		tcents.append("/100 dollars");
		return tcents.toString();
	}
	
	/*
	* Returns the dollar portions (the portion preceding the decimal)
	* of the input. If there is no decimal returns the entire String
	* If there is no input before the demical it returns the dollar 
	* amount of "0".
	*/
	private String formatDollarDecimal(String dollar)
	{
		String retValue = dollar;

		//There is a deciaml 
		if((retValue.contains(".")))
		{
			retValue = retValue.split("[.]")[0];
			//If the number starts with a . (only cents)
			if(retValue.equals(""))
			{
				//set dollars to 0
				retValue = "0";
			}
		}
		return retValue;
	}
	
	/*
	* Returns if the input has a dollar amount representing 0
	*/
	private boolean isZeroDollars(String theVal)
	{
		boolean retVal = false;
		BigInteger intDol = new BigInteger(theVal);
		if (theVal.equals("0") || intDol.compareTo(BigInteger.ZERO)==0)
		{
		 	retVal = true;
		}
		return retVal;
	}
	
	/*
	* Takes the input and retrns the string representation of the numbers
	*/
	private String buildDollarString(String theFValue)
	{
		String temp;
		StringBuilder dollarBuilder = new StringBuilder();
		String retVal = "";
		int i =0;
		if (isZeroDollars(theFValue))
		{
			retVal = "zero " ;
		}
		else
		{
			while(i < theFValue.length())
			{

				//Check to see if there is a number 0 - 20
				if( ((i % 3) == 1) && ((Integer.parseInt(theFValue.substring((i),(i+2))) % 100 )< 20))
				{

					build20(theFValue, dollarBuilder, i);
					i++;
				}
				//Tens place
				else if ((i % 3) == 1)
				{

					build10(theFValue, dollarBuilder, i);

				}

				//ones
				else
				{

			        build1(theFValue, dollarBuilder, i);

				}

				//Add "hundred"
				if (((i % 3) == 0 )&& (Integer.parseInt(theFValue.substring((i),(i+1))) != 0))
				{

					add100(dollarBuilder);

				}
				//Add other prefix
				if(((i % 3) == 2) && (Integer.parseInt(theFValue.substring((i-2),(i+1))) != 0))
				{

					addPrefix(theFValue, dollarBuilder, i);

				}
				i++;

			}
			retVal = dollarBuilder.toString();
		}
	
		return retVal;
	}
	/* 
	 *Takes the input string and builds the dollar ammount. 
	 * It will return a formatted dollar amount.
	 */
	private String getDollars(String theValue)
	{
		String dollars = theValue;
		//Gets the dollar portion of theVak=lue
		dollars = formatDollarDecimal(dollars);
		
		//Formats the Input
		String theFValue = formatString(dollars);
		
		//Builds the output
		String dollarString = buildDollarString(theFValue);
		return dollarString;
	}
	
	/*
	* Appends the appropriate prefix to the string builder and returns it.
	*/
	private void addPrefix(String valF, StringBuilder dollar, int i)
	{
		
		//Uses the lenght of the formated dollar portion
		//divide length by three (how many units of 3)
		//subract i+1/3 to get correct subsection out
		dollar.append(suffix[(((valF.length()/3))-((i+1)/3))]);
		
		if ( ((((valF.length()/3))-((i+1)/3)))!= 0)
		{
			dollar.append(" ");	
		}
	}
	
	/*
	* Appends "hundred" to the StringBuilder and returns it
	*/
	private void add100 (StringBuilder dollar)
	{
		dollar.append("hundred");
		dollar.append(" ");
	}
	
	/*
	* Checks if the tens and ones portion of a string contains 0-20. If 
	* it does it appends the appropriate value to the string bulder.
	* Returns a String Builder.
	*/	
	private void build20(String valF, StringBuilder dollar, int i)
	{
		int reti = i;
		dollar.append(numToTwenty[Integer.parseInt(valF.substring((i),(i+2))) ]);
		if ( Integer.parseInt(valF.substring((i),(i+2))) != 0)
		{
			dollar.append(" ");	
		}
	}
	
	/*
	* Appends the appropriate tens place to the string builder. Adds a - for values 21-99.
	* Returns the String Value.
	*/
	private void build10(String valF, StringBuilder dollar, int i)
	{
		dollar.append(tensPlaces[Integer.parseInt(valF.substring((i),(i+1)))]);
		
		if ( (Integer.parseInt(valF.substring((i),(i+1))) != 0) && (Integer.parseInt(valF.substring((i+1),(i+2))) == 0))
		{
			dollar.append(" ");	
		}
		if ( Integer.parseInt(valF.substring((i+1),(i+2))) != 0)
		{
			dollar.append("-");	
		}
	}
	
	/*
	* Adds the appropriate 1 digit to the string builder and returns the StringBuilder
	*/
	private void build1(String valF, StringBuilder dollar, int i)
	{
		dollar.append(numToTwenty[Integer.parseInt(valF.substring((i),(i+1)))]);	
		if ( Integer.parseInt(valF.substring((i),(i+1))) != 0)
		{
			dollar.append(" ");	
		}
    }

	/* Takes the string and formats the dollar amount such that 
	 * It has a length that is a multiple of 3.  It fills in
	 * the beginning of the  number with 0 to achieve this
	 */
 	private String formatString(String theValue)
	{ 
		String theRetValue;
		// pad the number so the lenght is mult 3
		int valLen = theValue.length();
		if ((valLen % 3) !=0)
		{
			//get the desired length of formatated string
			int desiredLen = ( ((valLen/3) + 1) * 3);
			
			//Build the formatting string
			StringBuilder myformatBuilder = new StringBuilder();
			myformatBuilder.append("%0");
			myformatBuilder.append(Integer.toString(desiredLen));
			myformatBuilder.append("d");
			String myFormat = myformatBuilder.toString();

			//Make a big intger out of the value passed in
			BigInteger theBigNum = new BigInteger(theValue);
			
			//Format the big integer and set the return value to the string
			theRetValue = String.format(myFormat, theBigNum);

		}
		else //The string is already formated - return it
		{
			theRetValue = theValue;		
		}
		return theRetValue;
		
	}
	
	/*
	* Will read in the commadn line argument 
	* and call convert a valid entry to a string
	* currency amount.  
	*/
    public static void main(String[] args)
	{

		//Check there is exactly one argument
		if(args.length == 1)
		{
			Convert4 myConverter = new Convert4();
			myConverter.doStuff(args[0]);
		}
		else
		{
			System.out.println("There needs to be exctly one argument. Please run again.");
		}
	}
		
}
