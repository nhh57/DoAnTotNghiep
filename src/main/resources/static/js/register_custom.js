$(document).ready(() => {
    $('#repassword').blur(function() {
        var pass = $('#password').val();
        var repass = $('#repassword').val();
        if (pass != repass) {
            $('#errorRepassword').removeAttr('hidden');
        }else {
            $('#errorRepassword').attr("hidden",true);
        }
    });
    $("#modalNotify").modal('show');
});