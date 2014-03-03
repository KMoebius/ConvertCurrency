ConvertCurrency 
takes exactly one argument formated as a number (with at least one number and at most one decimal) and prints out the string representation (as it would appear on a check).

Invalid inputs include anything except [0-9] and one '.' or any number with more than two digits after the '.'
The program will not except a negative number.
Numbers that do not have a '.' are interpreted to be only a dollar amount.
If there is only one number (#) following the '.' it is assumed to be (# * 10) cents. For example .9 = 90/100 dollars
It will accept input up to 2^64-1.

===============
