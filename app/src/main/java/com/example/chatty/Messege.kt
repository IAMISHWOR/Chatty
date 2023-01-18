package com.example.chatty

import android.inputmethodservice.AbstractInputMethodService

class Messege {
    var messege: String? = null
    var senderId: String? = null

    constructor(){}

    constructor(messege: String?,senderId: String?){
        this.messege = messege
        this.senderId = senderId
    }
}