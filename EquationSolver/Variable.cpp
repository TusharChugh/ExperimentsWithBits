#include "Variable.h"

Variable::Variable(std::string varName, unsigned int varValue) {
	this->varName = varName;
	if(varName.empty())
		throw "Exception: String can't be empty";
	this->varValue = varValue;
}

void Variable::store(unsigned int varValue) {
	this->varValue = varValue;
}

const std::string Variable::name() const
{
	return varName;
}

const unsigned int Variable::value() const {
	return varValue;
}

unsigned long Variable::hashCode() const {
	unsigned long hash = 5381;
	int c;

	for (const char& c : varName) {
		hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
	}
	return hash;
}

bool Variable::operator<(const Variable & variable) const
{
	if (strcmp(varName.data(), variable.name().data()) < 0)
		return true;
	return false;
}

std::ostream & operator<<(std::ostream & out, const Variable& variable)
{
	return out << variable.name() << " = " << std::to_string(variable.value());
}

bool operator==(const Variable & var1, const Variable & var2) 
{
	if (std::strcmp(var1.name().data(), var2.name().data()))
		return false;
	return true;
}


