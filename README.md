Problem: The TARDIS has been infected by a virus which means it is up to Doctor Who to manually enter
calculations into the TARDIS interface. The calculations necessary to make the TARDIS work properly
involve real, imaginary and complex numbers. The Doctor has asked you to create a program that will
analyze numerical expressions so that he can quickly enter the information into the TARDIS.
Details:
• Classes
o Number class
? Attributes
• Real number (double)
? Methods
• Constructor – pass in value for number
• Accessor
• Mutator
• toString
• equals
o Complex number class
? Extends number class (-5 points)
? Attributes:
• Imaginary number (double)
? Methods
• Constructor – pass in real and imaginary
o Call super constructor
• Accessor
• Mutator
• toString
• equals
• Read in the entire expression as a string and parse it into the proper objects
o Store each part of the in the correct attribute of the object
• Use the toString function to display the object when necessary
• Both the real and imaginary parts may be floating point values
• Validate that each expression contains no invalid characters
o The only valid letter in a complex number is i (lower case)
o Validate that each expression contains a valid operator
• Each number in the expression must be stored in the appropriate object
o Real numbers are not to be stored in a complex object
• If a line contains invalid data, ignore the line
• Numbers may be represented in 3 ways
o Complex (real + imaginary)
? <number><+ or -><number>i
o Real only
? <number>
o Imaginary only
? <number>i
• Numbers may be positive or negative
• Results for arithmetic operators will be numerical
• Results for relational operators will be Boolean
• Calculating less/greater than of a complex or imaginary number will be determined by analyzing
the modulus (or absolute value) of each complex number
• All functions in Main.java that would normally require a real or complex number parameter
object must use Object parameters instead (-10 points)
o Use the instanceof operator to determine the type of object and perform the proper
actions based on the object type
? For example, adding complex numbers would add both the real and imaginary
parts, where as adding real numbers would only add the real parts
• Comment your code generously. Refer to the grading rubric for more details
• Use as few variables as possible. Don’t waste memory holding a calculation just so you can print
it out one time.
User Interface: There will be no user interface for this program. All I/O will be performed with a files
Input:
• All input will come from a file named expressions.txt.
• Each line in the file will contain an expression to be evaluated
• Valid line format: <number><space><operator><space><number>
o <number> can be real or complex
• Valid operators
o + (add)
o – (subtract)
o * (multiply)
o / (divide)
o < (less than)
o > (greater than)
o = (equal)
o /= (not equal)
• There will be a newline at the end of each line except for the last line (which may or may not
have a newline)
Output:
• All output will be written to a file named results.txt.
• Format all output into a table with 2 columns
o Column 1: original expression
? Use toString to print out the numbers in the expression
o Column 2: value
• All values in the columns will be left justified
• Numerical values will be rounded to 2 decimal places.
• Relational operators should evaluate to true or false
• Each expression will be listed on separate lines