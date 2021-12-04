$(document).ready(function () {




  $("#email").on('blur',function (e) {
        e.preventDefault()
        let email=$("#email").val()
        verifyEmail(email)
    })


    $("#username").on('blur',function (e) {
        e.preventDefault()
        let username=$("#username").val()
        verifyUsername(username)
    })

    $("#code").on('blur',function (e) {
        e.preventDefault()
        let code=$("#code").val()
        let association=$("#association").val()
        verifyCode(code,association)
    })



    function verifyCode(code,association) {
        $.ajax({
            url:'/api/v1/verifyCodeValidation',
            method:'GET',
            data:{
                code:code,
                association:association
            },
            success:function (data, textStatus, jqXHR) {

                if (data.error ){
                    console.log(data.message)
                    $("#res_code").empty().append("<span style='color: red;margin-left: 8px'>Code ou association invalide</span>")
                }else{
                    $("#res_code").empty()
                    console.log(data.message)
                }

                }
            ,
            error:function (jqXHR, exception) {
                console.log(jqXHR)
            }
        })
    }

    function verifyUsername(username) {
        $.ajax({
            url:'/api/v1/existUsername',
            method:'GET',
            data:{
                username:username
            },
            success:function (data, textStatus, jqXHR) {

                if (data.error){
                    //notifyWarning('Error',"Le username a déjà été utilisé")
                    console.log(data.message)
                     $("#res_username").empty().append("<span style='color: red;margin-left: 8px'>Le username a déjà été utilisé</span>")
                }else{
                     $("#res_username").empty()
                    console.log(data.message)
                }
            },
            error:function (jqXHR, exception) {
                console.log(jqXHR)
            }
        })
    }

    function verifyEmail(email) {
        $.ajax({
            url:'/api/v1/existEmail',
            method:'GET',
            data:{
                email:email
            },
            success:function (data, textStatus, jqXHR) {
                if (data.error){
                    $("#res_email")
                        .empty()
                        .append("<span style='color: red;margin-left: 8px'>L'email a déjà été utilisé</span>")
                }else{
                    $("#res_email").empty()
                    console.log(data.message)
                }
            },
            error:function (jqXHR, exception) {
                console.log(jqXHR)
            }
        })
    }


    function notifySuccess(title, body) {
        swal.fire({
            title: title,
            text: body,
            textColor:'blue',
            type: 'success',
            icon:'success',
            // buttonsStyling: false,
            // confirmButtonColor:'#3085d6',
            confirmButtonClass: 'btn btn-sm bg-primary',
            //background: 'rgba(0, 0, 0, 0.96)'
        })

    }

    function notifyWarning(title, body) {
        Swal.fire({
            title: title,
            text: body,
            //type: 'warning',
            //buttonsStyling: true,
           // confirmButtonClass: 'btn btn-sm btn-light',
            //  background: 'rgba(0, 0, 0, 0.96)'
        })
    }

})