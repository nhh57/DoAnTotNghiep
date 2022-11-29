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
});