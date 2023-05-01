package com.chow.code_base_sdk.utils

/**
 * WE ARE USING THIS ENUM CLASS TO CREATE STATIC OBJECTS
 * SO THE CLIENT CAN CHOOSE WHICH CHARACTER TO CALL
 *
 * THIS IS ONE OF THE PARTS OF THE ENTRY POINT OF THE SDK
 * IN ORDER TO INITIALIZE A CHARACTER TYPE MUST BE PASSED BY THE CLIENT
 */
enum class CharactersType(val realType: String) {
    SIMPSONS("simpsons characters"),
    THE_WIRE("the wire characters")
}