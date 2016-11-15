#ifndef VARIABLE_H					
#define VARIABLE_H 

#include <string>
#include <cstring>
#include <sstream>
/**********************************************************************************************//**
 * @class	Variable
 *
 * @brief	A variable.
 **************************************************************************************************/

class Variable {
private:
	std::string varName;
	unsigned int varValue;

public:

	/**********************************************************************************************//**
	 * @fn	Variable::Variable(std::string varName, unsigned int varValue);
	 *
	 * @brief	Constructor.
	 * 			
	 * @param	varName 	Name of the variable.
	 * @param	varValue	The variable value.
	 **************************************************************************************************/

	Variable(std::string varName, unsigned int varValue);

	/**********************************************************************************************//**
	 * @fn	void Variable::store(unsigned int varValue);
	 *
	 * @brief	Stores the value of a variable
	 *
	 * @param	varValue	The Variable value to store.
	 **************************************************************************************************/

	void store(unsigned int varValue);

	/**********************************************************************************************//**
	 * @fn	const std::string Variable::name() const;
	 *
	 * @brief	Gets the name.
	 *
	 * @return	A const std::string.
	 **************************************************************************************************/

	const std::string name() const;

	/**********************************************************************************************//**
	 * @fn	const unsigned int Variable::value() const;
	 *
	 * @brief	Gets the value.
	 *
	 * @return	A const unsigned int.
	 **************************************************************************************************/

	const unsigned int value() const;

	/**********************************************************************************************//**
	 * @fn	friend std::ostream & Variable::operator<<(std::ostream & out, const Variable& variable);
	 *
	 * @brief	override << operator to return stream of the string
	 *
	 * @param [in,out]	out			The out.
	 * @param 		  	variable	The variable.
	 *
	 * @return	string stream of the format 'name = value'
	 **************************************************************************************************/

	friend std::ostream & operator<<(std::ostream & out, const Variable& variable);

	/**********************************************************************************************//**
	 * @fn	unsigned long Variable::hashCode() const;
	 *
	 * @brief	Hash code.
	 *
	 * @return	A long. Hash code
	 **************************************************************************************************/

	unsigned long hashCode() const;

	/**********************************************************************************************//**
	 * @fn	friend bool Variable::operator==(const Variable& var1, const Variable& var2);
	 *
	 * @brief	Equality operator.
	 *
	 * @param	var1	The first instance to compare.
	 * @param	var2	The second instance to compare.
	 *
	 * @return	True if the parameters are considered equivalent.
	 **************************************************************************************************/

	friend bool operator==(const Variable& var1, const Variable& var2);

	/**********************************************************************************************//**
	 * @fn	bool Variable::operator<(const Variable &variable) const;
	 *
	 * @brief	Less-than comparison operator.
	 *
	 * @param	variable	The variable.
	 *
	 * @return	True if the first parameter is less than the second.
	 **************************************************************************************************/

	bool operator<(const Variable &variable) const;
};

#endif
