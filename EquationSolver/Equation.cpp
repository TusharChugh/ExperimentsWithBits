#include "Equation.h"
#include <sstream>

namespace std {
	template <>
	struct hash<Variable> {
		size_t operator () (const Variable &f) const { return f.hashCode(); }
	};
}

Equation::Equation(std::string inputEquation) {
	this->inputEquation = inputEquation;
	if (inputEquation.empty())
		throw "Exception: Equation can't be empty";
	parseEquation();
	isSolved = false;
}

bool Equation::solve(std::unordered_set<Variable> &variables) {
	int total = 0;
	isSolved = true;
	std::list<Variable> itemsToRemove;
	for (auto var : rhsUnsolvedVariables) {
		auto got = variables.find(var);
		if (got == variables.end())
			isSolved = false;
		else {
			var.store(got->value());
			rhsSolvedVariables.push_back(var);
			itemsToRemove.push_back(var);
		}
	}
	for (auto var : itemsToRemove) {
		rhsUnsolvedVariables.remove(var);
	}
	if (isSolved) {
		for (auto var : rhsSolvedVariables)
			total += var.value();
		for (auto num : rhsConstants)
			total += num;
		lhs->store(total);
	}
	return isSolved;
}

Variable* Equation::result() {
	if (isSolved)
		return new Variable(lhs->name(), lhs->value()) ;
	else
		return NULL;
}

bool Equation::solved()
{
	return isSolved;
}

void Equation::parseEquation() {
	bool equalsEncountered = false;
	std::istringstream inputStream(inputEquation);
	for (std::string input; inputStream >> input; ) {
		if (input.compare("=") == 0)
			equalsEncountered = true;
		else if (!equalsEncountered) {
			if (isValidString(input))
				lhs = new Variable(input, variableInitVal);
			equalsEncountered = true;
		}
		else {
			if (isValidString(input))
				rhsUnsolvedVariables.push_back(Variable(input, variableInitVal));
			else if (isValidNumber(input))
				rhsConstants.push_back(std::stoi(input));
		}
	}
	if(!equalsEncountered)
		throw "Exception: Equation can't be empty";
}


bool Equation::isValidNumber(std::string &input) {
	return input.find_first_not_of("0123456789") == std::string::npos;
}


bool Equation::isValidString(std::string &input) {
	for (char& c : input) {
		if (!isalpha(c))
			return false;
	}
	return true;
}

bool operator==(const Equation & exp1, const Equation & exp2) {
	if  (std::strcmp(exp1.lhs->name().data(), exp2.lhs->name().data()))
		return false;
	return true;
}
