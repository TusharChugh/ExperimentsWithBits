#ifndef EQUATION_H					
#define EQUATION_H 

#include "Variable.h"
#include <string>
#include <vector>
#include <unordered_set>
#include <list>
#include <cstring>

class Equation {
	std::string inputEquation;
	Variable *lhs = nullptr;
	std::vector<int> rhsConstants;
	std::list<Variable> rhsSolvedVariables;
	std::list<Variable> rhsUnsolvedVariables;
	const unsigned int variableInitVal = 0;

	bool isSolved;

	bool isValidString(std::string &input);
	bool isValidNumber(std::string &input);
	void parseEquation();

public:

	/**********************************************************************************************//**
	 * @fn	Equation::Equation(std::string inputEquation);
	 *
	 * @brief	Constructor.
	 *
	 * @param	inputEquation	The input Equation (String).
	 **************************************************************************************************/

	Equation(std::string inputEquation);

	/**********************************************************************************************//**
	 * @fn	bool Equation::solve(std::unordered_set<Variable> &variables);
	 *
	 * @brief	Solves the equation with given variables.
	 *
	 * @param [in,out]	variables	The variables.
	 *
	 * @return	True if it succeeds, false if it fails.
	 **************************************************************************************************/

	bool solve(std::unordered_set<Variable> &variables);

	/**********************************************************************************************//**
	 * @fn	Variable* Equation::result();
	 *
	 * @brief	Gets the result.
	 *
	 * @return	Null if the Equation has not been solved, else a pointer to a Variable.
	 **************************************************************************************************/

	Variable* result();

	/**********************************************************************************************//**
	 * @fn	bool Equation::solved();
	 *
	 * @brief	returns whether the Equation has been solved or not
	 *
	 * @return	True if has been else false
	 **************************************************************************************************/

	bool solved();

	/**********************************************************************************************//**
	 * @fn	friend bool Equation::operator==(const Equation& exp1, const Equation& exp2);
	 *
	 * @brief	Equality operator.
	 *
	 * @param	exp1	The first instance to compare.
	 * @param	exp2	The second instance to compare.
	 *
	 * @return	True if the parameters are considered equivalent.
	 **************************************************************************************************/

	friend bool operator==(const Equation& exp1, const Equation& exp2);
};

#endif