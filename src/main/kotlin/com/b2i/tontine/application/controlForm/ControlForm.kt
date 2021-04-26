package com.b2i.tontine.application.controlForm

import org.springframework.ui.Model
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*

object ControlForm {

    fun model(model: Model, message: String, color: Color) {
        model.addAttribute("operationMessage", message)
        model.addAttribute("colorMessage", color)
    }

    fun modelPhoto(model: Model, clef: String, message: String, color: Color) {
        model.addAttribute(clef, message)
        model.addAttribute("colorMessage$clef", color)
    }


    fun redirectAttribute(redirectAttributes: RedirectAttributes, message: String, color: Color){
        ///Something is wrong
        redirectAttributes.addFlashAttribute("operationMessage", message)
        redirectAttributes.addFlashAttribute("colorMessage", color)
    }

    fun redirectPhoto(redirectAttributes: RedirectAttributes, clef: String, message: String, color: Color) {
        redirectAttributes.addFlashAttribute(clef, message)
        redirectAttributes.addFlashAttribute("colorMessage$clef", color)
    }

    fun verifyHashMap(model: Model, errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isEmpty() )
        {
            model( model, "Operation réussie", Color.green )
        }
        else
        {
            val entry: Map.Entry<String, String> = errors.entries.iterator().next()
            val key = entry.key
            val value = entry.value

            success=false

            model(model, value, Color.red)
        }

        return success
    }


    fun verifyHashMapRedirect( redirectAttributes: RedirectAttributes, errors : Map<String, String>, message: String):Boolean
    {
        var success=true

        if ( errors.isEmpty() )
        {
            redirectAttribute( redirectAttributes, message, Color.green )
        }
        else
        {
            val entry: Map.Entry<String, String> = errors.entries.iterator().next()
            val key = entry.key
            val value = entry.value

            success=false

            redirectAttribute( redirectAttributes , value, Color.red)
        }

        return success
    }

    fun verifyApiHashMap( errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isNotEmpty() )
        {
            success=false
        }

        return success
    }

    fun extractFirstMessage( errors: Map<String, String> ) : String {

        val entry: Map.Entry<String, String> = errors.entries.iterator().next()
        return entry.value
    }


//    fun saveImage (model: Model, image : MultipartFile, typeImage : String, prefixImage : String, user : User) : String {
//
//
//        if ( !image.isEmpty )
//        {
//            val namePhoto = Photo.finalName( prefixImage, user.username )
//
//            if ( !InteractionServer.saveImage( typeImage, image, namePhoto) ) {
//                modelPhoto( model, prefixImage, "Echec de la sauvegarde de: $prefixImage", Color.red )
//            }
//            else{
//                modelPhoto( model, prefixImage, "Sucess de la sauvegarde de: $prefixImage", Color.green )
//                return namePhoto
//            }
//        }
//
//        return ""
//    }

//    fun saveImageRedirect ( redirectAttributes: RedirectAttributes, image : MultipartFile, typeImage : String, prefixImage : String, user : User) : String {
//
//
//        if ( !image.isEmpty )
//        {
//            val namePhoto = Photo.finalName( prefixImage, user.username )
//
//            if ( !InteractionServer.saveImage( typeImage, image, namePhoto) ) {
//                redirectPhoto( redirectAttributes, prefixImage, "Echec de la sauvegarde de: $prefixImage", Color.red )
//            }
//            else{
//                redirectPhoto( redirectAttributes, prefixImage, "Sucess de la sauvegarde de: $prefixImage", Color.green )
//                return namePhoto
//            }
//        }
//
//        return ""
//    }

    fun emptyField(msg:String):String{
        return "Remplissez le champ ${msg} SVP"
    }

    fun objectNotFound(ob:String):String{
        return "${ob} introuvable"
    }

    fun formatDate( date : String ) : Date {
        val format = SimpleDateFormat( "yyyy-MM-dd" )

        return format.parse( date )
    }

}