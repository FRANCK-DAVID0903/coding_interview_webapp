$(document).ready(function () {

    $('#contact').validate({
        rules:{
            name:{
                required:true,
                minlength:3,
                maxlength:50
            },
            email:{
                required:true,
                email:true
            },
            subject:{
                required:true,
                minlength:3,
                maxlength:50
            },
            message:{
                required:true,
                minlength:3
            }
        },
        messages:{
            name:{
                required:'Remplissez le champ nom',
                minlength:'Le nom doit contenir au moins 3 caractères',
                maxlength:'Le nom doit contenir au plus 50 caractères'
            },
            email:{
                required:'Remplissez le champ email',
                email:'Votre email n\'est pas valide  exemple:luis@domain.com'
            },
            subject:{
                required:'Remplissez le champ subject',
                minlength:'Le sujet doit contenir au moins 3 caractères',
                maxlength:'Le sujet doit contenir au plus 50 caractères'
            },
            message:{
                required:'Remplissez le champ message',
                minlength:'Le message doit contenir au moins 3 caractères',
            },
        }
    })

});