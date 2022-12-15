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

    $("#imagesMore").on('change', function () {
        $(".showImages").empty();
        for (let i = 0; i < this.files.length; ++i) {
            let filereader = new FileReader();
            let $img = jQuery.parseHTML("<img style='margin:5px;' src='' width='60' height='60'>");
            filereader.onload = function () {
                $img[0].src = this.result;
            };
            filereader.readAsDataURL(this.files[i]);
            $(".showImages").append($img);
        }
    });
    $(".inputImageMore").on('change', function () {
        const thisInput=$(this);
        const productId=thisInput.data('pid');
        $(".showMoreImage"+productId).empty();
        for (let i = 0; i < this.files.length; ++i) {
            let filereader = new FileReader();
            let $img = jQuery.parseHTML("<img style='margin:5px;' src='' width='60' height='60'>");
            filereader.onload = function () {
                $img[0].src = this.result;
            };
            filereader.readAsDataURL(this.files[i]);
            $(".showMoreImage"+productId).append($img);
        }
    });
    $('.imageMore').dblclick(function(){
        const thisImg=$(this);
        const imgId=thisImg.data('imgid');
        $.ajax({
            url: "/mvc/admin/product/product-image/delete",
            method: "POST",
            // type: "application/json",
            data: {
                imageId: imgId
            },
            success: function(response) {
                const obj = JSON.parse(response);
                console.log(obj);
                thisImg.remove();
            }
        });

    });
});