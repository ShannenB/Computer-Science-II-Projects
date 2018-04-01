#Pr*blem: The TARDIS has been infected by a virus which means it is up t* D*ct*r Wh* t* manually enter
calculati*ns int* the TARDIS interface. The calculati*ns necessary t* make the TARDIS w*rk pr*perly
inv*lve real, imaginary and c*mplex numbers. The D*ct*r has asked y*u t* create a pr*gram that will
analyze numerical expressi*ns s* that he can quickly enter the inf*rmati*n int* the TARDIS.

#Details:
##Classes
	*Number class
		* Attributes
			* Real number (d*uble)
		* Meth*ds
			* C*nstruct*r – pass in value f*r number
			* Access*r
			
* Mutat*r
			* t*String
			* equals
	 C*mplex number class
		* Extends number class (-5 p*ints)
		* Attributes:
			* Imaginary number (d*uble)
		* Meth*ds
			* C*nstruct*r – pass in real and imaginary
				* Call super c*nstruct*r
			* Access*r
			* Mutat*r
			* t*String
			* equals
	* Read in the entire expressi*n as a string and parse it int* the pr*per *bjects
		* St*re each part *f the in the c*rrect attribute *f the *bject
	* Use the t*String functi*n t* display the *bject when necessary
	* B*th the real and imaginary parts may be fl*ating p*int values
	* Validate that each expressi*n c*ntains n* invalid characters
		* The *nly valid letter in a c*mplex number is i (l*wer case)
		* Validate that each expressi*n c*ntains a valid *perat*r
	* Each number in the expressi*n must be st*red in the appr*priate *bject
		* Real numbers are n*t t* be st*red in a c*mplex *bject
	* If a line c*ntains invalid data, ign*re the line
	* Numbers may be represented in 3 ways
		* C*mplex (real + imaginary)
			? <number><+ *r -><number>i
		* Real *nly
			? <number>
		* Imaginary *nly
			? <number>i
	* Numbers may be p*sitive *r negative
	* Results f*r arithmetic *perat*rs will be numerical	
	* Results f*r relati*nal *perat*rs will be B**lean
	* Calculating less/greater than *f a c*mplex *r imaginary number will be determined by analyzing the m*dulus (*r abs*lute value) *f each c*mplex number
	* All functi*ns in Main.java that w*uld n*rmally require a real *r c*mplex number parameter *bject must use *bject parameters instead (-10 p*ints)
		* Use the instance*f *perat*r t* determine the type *f *bject and perf*rm the pr*per acti*ns based *n the *bject type
? F*r example, adding c*mplex numbers w*uld add b*th the real and imaginary parts, where as adding real numbers w*uld *nly add the real parts
	* C*mment y*ur c*de gener*usly. Refer t* the grading rubric f*r m*re details
	* Use as few variables as p*ssible. D*n’t waste mem*ry h*lding a calculati*n just s* y*u can print it *ut *ne time.
	
#User Interface: There will be n* user interface f*r this pr*gram. All I/* will be perf*rmed with a files
	
#Input:
	* All input will c*me fr*m a file named expressi*ns.txt.
	* Each line in the file will c*ntain an expressi*n t* be evaluated
	* Valid line f*rmat: <number><space><*perat*r><space><number>
		* <number> can be real *r c*mplex
	* Valid *perat*rs
		* + (add)
		* – (subtract)
		* * (multiply)
		* / (divide)
		* < (less than)
		* > (greater than)
		* = (equal)
		* /= (n*t equal)
	* There will be a newline at the end *f each line except f*r the last line (which may *r may n*t have a newline)
#*utput:
	* All *utput will be written t* a file named results.txt.
	* F*rmat all *utput int* a table with 2 c*lumns
		* C*lumn 1: *riginal expressi*n
			? Use t*String t* print *ut the numbers in the expressi*n
		* C*lumn 2: value
	* All values in the c*lumns will be left justified
	* Numerical values will be r*unded t* 2 decimal places.
	* Relati*nal *perat*rs sh*uld evaluate t* true *r false
	* Each expressi*n will be listed *n separate lines