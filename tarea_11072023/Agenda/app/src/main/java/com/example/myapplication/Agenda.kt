package com.example.myapplication

import java.io.IOException

private const val USER = "admin"
private const val PASSWORD = "12345678"
private const val ATTEMPTS = 3

data class Contact (val name: String = "", val phoneNumber: String, val email: String? = "")

class Helper {
    companion object {
        fun validateTextLength(value: String?): Boolean {
            return value?.trim()!! > 0.toString()
        }

        fun validateNumberGraterThanZero(value: Int): Boolean {
            return value > 0
        }
    }
}

class Security {
    companion object {
        fun verifyUserIdentity(_username: String?, _passwords: String?): Boolean {
            return _username === USER && _passwords === PASSWORD
        }
    }
}

class MainMenu {

    companion object {
        public fun renderMenu() {
            println("Welcome to the Agenda App built using Kotlin!")
            var countAttempts = 0
            var authenticated = false
            while (countAttempts <= ATTEMPTS && !authenticated){
                println("Please enter your username: ")
                val username = readLine()
                println("Please enter your password: ")
                val password = readLine()

                authenticated = Security.verifyUserIdentity(username, password)

                if(authenticated)
                {
                    println("Please select and option")
                    println("1. Add a new contact")
                    println("2. Remove a contact from the list")
                    println("0. Exit")
                    println("--------------------------------------------------")
                    var option = readLine()

                    var agenda = Agenda()

                    when(option)
                    {
                        "0" -> {
                            println("Bye!")
                            authenticated = false

                        }
                        "1" -> {
                            println("Please enter a contact name")
                            val _name = readLine()

                            println("Please enter a contact phone number")
                            val _phoneNumber = readLine()

                            println("Please enter a contact phone email")
                            val _email = readLine()

                            if (_name != null && _phoneNumber != null) {
                                val result = agenda.addNewContact(_name, _phoneNumber, _email)
                                println(result)
                            }
                        }
                        "2" -> {
                            println("Please enter a contact id to remove")
                            val contacId = readLine()?.toInt()
                            if (contacId != null) {
                                agenda.removeContact(contacId)
                            }
                        }
                        else -> {
                            println("Please select and option")
                            println("1. Add a new contact")
                            println("2. Remove a contact from the list")
                            println("0. Exit")
                            println("--------------------------------------------------")
                            option = readLine()
                        }
                    }
                }else{
                    countAttempts++
                }
            }

            println("Uoops! you have already rebase the maximum amount of attempts ")

        }
    }
}

class Agenda {
    private val Contacts = mutableListOf<Contact>(
        Contact("Contact 1", "1234-1234"),
        Contact("Contact 2", "1234-1234"),
        Contact("Contact 3", "1234-1234"),
        Contact("Contact 4", "1234-1234"),
        Contact("Contact 5", "1234-1234"),
        Contact("Contact 6", "1234-1234"),
        Contact("Contact 7", "1234-1234"),
        Contact("Contact 8", "1234-1234")
    )
    public fun addNewContact(name: String, phoneNumber: String, email: String?): String {
        try {
            if(!Helper.validateTextLength(name) && !Helper.validateTextLength(phoneNumber))
                throw IOException("Invalid user or phone number");

            Contacts.add(Contact(name, phoneNumber))
        } catch (ex: IOException)
        {
            println("An error has occurred - message: ${ex.message}")
        }

        return "New contact was added to the list $name phone number: $phoneNumber"
    }

    public fun removeContact(index: Int) {
        try{
            if(!Helper.validateNumberGraterThanZero(index))
                throw IOException("Invalid user to be removed");

        } catch (ex: IOException)
        {
            println("An error has occured - message: ${ex.message}")
        }
    }

    fun main(args: Array<String>){
        print("Please enter your name: ")
        val name = readLine()
        print("Hello $name!")

        MainMenu.renderMenu()
    }
}