Build the code
Linux: g++ EquationSolver.cpp -o EquationSolver Variable.h Variable.cpp Equation.cpp Equation.h -std=c++11
Windows: Import the files in Visual Studio 2015 and in properties->debugger (of project) give the fileName

Run the code:
EquationSolver fileName
eg: EquationSolver input.txt

Example Output: 
offset = 4 + random  + 1
location = 1 +  origin + offset
origin = 3    +    5
random = 2

Solution
location = 16
offset = 7
origin = 8
random = 2

Possible improvements: 
1. The problem can be effeciently solved with Matix inversion. Here we have number of equations, equal to the number of unknown, so we will get unique solution for the square and full rank matrix. 

2. Reading file and expression solver can be independent components. But I ran out of time. 

3. I used friend functions of operator overloading. I can be done without that as I am not accessing any privates using friend. This can become a security loophole. 

4. Used unordered_set because it will have constant time look-up and would be beneficial when we have a large number of equations. The downside is that the sort method provided in STL doesn't work with unordered_set, so I had to copy that in a vector to sort.

5. Parsing is not robutst.


