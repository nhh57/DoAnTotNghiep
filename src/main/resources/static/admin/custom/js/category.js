$(document).ready(function () {
    $("#filterCustom").change((e)=> {
    var filterCustom = $(e.currentTarget);
        var value = filterCustom.val().toLowerCase();
        $("#tableFilter tr").filter(function () {
            if(value=='tất cả'){
                filterCustom.toggle(filterCustom.text().toLowerCase().indexOf('') > -1)
            }else{
                filterCustom.toggle(filterCustom.text().toLowerCase().indexOf(value) > -1)
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