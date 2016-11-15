#include <iostream>
#include <string>
#include <fstream>
#include "Variable.h"
#include "Equation.h"
#include <vector>
#include <unordered_set>
#include <list>
#include <algorithm>

//Hash used for unordered_set of variables
namespace std {
	template <>
	struct hash<Variable> {
		size_t operator () (const Variable &f) const { return f.hashCode(); }
	};
}

void solveEquations(std::list<Equation> &equations, std::unordered_set<Variable> &variables);
void printSolution(std::unordered_set<Variable> &variables);
void readFileGenerateEquation(std::string &fileName, std::list<Equation> &equations);

/**********************************************************************************************//**
 * @fn	int main(int argc, char* argv[])
 *
 * @brief	Main entry-point for this application.
 *
 * @param	argc	The number of command-line arguments provided. Shoudld be 2 for this application
 * @param	argv	An array of command-line argument strings. argv[1] is the file name
 *
 * @return	Exit-code for the process - 0 for success, else an error code.
 **************************************************************************************************/

int main(int argc, char* argv[]) {
	//Varibles for storing Equations and variables
	std::list<Equation> equations;
	//Variables will contain the solution
	std::unordered_set<Variable> variables;

	//Read the file and generate expresssions
	
	// Check the number of parameters
	if (argc < 2) {
		// Tell the user how to run the program
		std::cerr << "Usage: " << argv[0] << " FileName" << std::endl;
		return 1;
	}
	std::string fileName = argv[1];
	readFileGenerateEquation(fileName, equations);	

	//Solve Equation
	solveEquations(equations, variables);

	//Print Solution
	printSolution(variables);

	return 0;
}

/**********************************************************************************************//**
 * @fn	void readFileGenerateEquation(std::string &fileName, std::list<Equation> &equations)
 *
 * @brief	Reads file generate Equation.
 *
 * @param [in,out]	fileName   	name of the file.
 * @param [in,out]	Equations	The equations.
 **************************************************************************************************/

void readFileGenerateEquation(std::string &fileName, std::list<Equation> &equations) {
	
	std::string line;
	std::ifstream inputFile(fileName);
	if (inputFile.is_open())
	{
		while (std::getline(inputFile, line)) {
			std::cout << line << std::endl;
			//Generate Equation from each line of input
			equations.push_back(Equation(line));
		}
		inputFile.close();
	}
	else 
		std::cout << "Unable to open file";
}

/**********************************************************************************************//**
 * @fn	void solveEquations()
 *
 * @brief		//Solve the Equation and get the results in variables
 **************************************************************************************************/

void solveEquations(std::list<Equation> &equations, std::unordered_set<Variable> &variables) {
	bool allSolved = false;
	while (!allSolved) {
		allSolved = true;
		for (auto equation : equations) {
			if (!equation.solved()) {
				if (equation.solve(variables)) {
					variables.insert(*(equation.result()));
				}
				else
					allSolved = false;
			}
		}
	}
}

/**********************************************************************************************//**
 * @fn	void printSolution()
 *
 * @brief	Prints the solutions. Copying to vector as sort method is not support by unordered_set

 **************************************************************************************************/

void printSolution(std::unordered_set<Variable> &variables) {
	//Print the results
	std::cout << std::endl << "Solution" << std::endl;
	std::vector<Variable> result;
	result.assign(variables.begin(), variables.end());
	std::sort(result.begin(), result.end());
	for (Variable var : result)
		std::cout << var << std::endl;
}