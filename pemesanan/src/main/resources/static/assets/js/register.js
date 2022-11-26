$(document).ready(function(){
    $("form").submit(function (event) {
        event.preventDefault();

        const body = {
            "username" : $("input[name = username]").val(),
//            "password" : $("input[name = password]").val()
        }

            $.post({
                url: "/user/register",
                contentType:"application/json",
                data : JSON.stringify(body),
//                success: function (response) {
//                    window.location.replace("/");
                    // console.log("berhasil",response);
                    // let notifSucces = $("#notif-succes");
                    // let notifFail = $("#notif-fail");
                    // notifSucces.html(response.message);
                    // notifFail.hide();
                    // notifSucces.show();
//                },
                error : function (response) {
                   let responseJSON = response.responseJSON;
//                    let notifSucces = $("#notif-succes");
                    let notifFail = $("#notif-fail");
                    notifFail.html(responseJSON.message);
                    notifFail.show();
                    notifSucces.hide();
                }
            });
    });

});

alert("terbacakah??");