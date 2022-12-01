$(document).ready(function () {
    $("#filterCustom").change(function () {
        var value = $(this).val().toLowerCase();
        $("#tableFilter tr").filter(function () {
            if(value=='tất cả'){
                $(this).toggle($(this).text().toLowerCase().indexOf('') > -1)
            }else{
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            }
        });
    });
    $("#myInput").on("keyup", function () {
         var value = $(this).val().toLowerCase();
         $("#tableFilter tr").filter(function () {
             $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
         });
    });

    $('.checkRole').on('click', (element) => {
        const thisCheckBox = $(element.currentTarget);
        const accountId = thisCheckBox.data("accountid");
        const roleId = thisCheckBox.data("roleid");
        $.ajax({
            url: "/mvc/admin/account/set-role",
            method: "POST",
            // type: "application/json",
            data: {
                accountId: accountId,
                roleId:roleId
            },
            success: function(response) {
                const obj = JSON.parse(response);
                console.log(obj)
            }
        });
    });
});