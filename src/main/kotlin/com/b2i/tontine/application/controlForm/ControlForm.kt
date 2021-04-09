package com.b2i.social.application.controlForm

import com.b2i.tontine.application.controlForm.Color
import org.springframework.ui.Model
import org.springframework.web.servlet.mvc.support.RedirectAttributes

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

    fun VerifyHashMap(model: Model, errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isEmpty() )
        {
            ControlForm.model( model, "Operation réussie", Color.green )
        }
        else
        {
            val entry: Map.Entry<String, String> = errors.entries.iterator().next()
            val key = entry.key
            val value = entry.value

            success=false

            ControlForm.model(model, value, Color.red)
        }

        return success
    }


    object message{
        const val SUCCESS="Operation effectuée avec succès"
        const val FAIL="Malheureusement nous avons pas pu traité votre demande réessayer plus tard"
    }

    fun emptyField(msg:String):String{
        return "Remplissez le champ ${msg} SVP"
    }

    fun objectNotFound(ob:String):String{
        return "${ob} introuvable"
    }

}