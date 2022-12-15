$(document).ready(() => {
    $(".feat-btn").click(function () {
        $("div ul .feat-show").toggleClass("show");
        $("div ul .first").toggleClass("rotate");
        $(".feat-show .feat-show-btn.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("show").siblings().removeClass("show");
    });
    $(".feat-btn-test").click(function (e) {
        $("div ul .feat-show").removeClass("show");
     });
    $("div ul li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
    });
    $(".sidebar-left a").click(function (e) {
        e.preventDefault();
    });
    $(".feat-show .feat-show-btn").click(function (e) {
        $(this).addClass("active").siblings().removeClass("active");
    });

    var check=$("#clickElement").val();
    if(check=="diaChi"){
        $("div ul .feat-show").toggleClass("show");
        $("div ul .first").toggleClass("rotate");
        $(".feat-show .feat-show-btn.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("show").siblings().removeClass("show");

        $("#diaChi").addClass("active").siblings().removeClass("active");
        $("#user-info").removeClass("show first active");
        $("#user-address").addClass("show first active");
        $("#user-pass").removeClass("show first active");
    }else if(check=="doiMatKhau"){
        $("div ul .feat-show").toggleClass("show");
        $("div ul .first").toggleClass("rotate");
        $(".feat-show .feat-show-btn.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("show").siblings().removeClass("show");

        $("#doiMatKhau").addClass("active").siblings().removeClass("active");
        $("#user-info").removeClass("show first active");
        $("#user-address").removeClass("show first active");
        $("#user-pass").addClass("show first active");
    }else if(check=="donMua"){
    $("#donMua").addClass("active").siblings().removeClass("active");
             $("#user-cart").addClass("active").siblings().removeClass("active");
             $("#user-info").removeClass("show first active");
             $("#user-address").removeClass("show first active");
             $("#user-pass").removeClass("show first active");
         }else{
        $("div ul .feat-show").toggleClass("show");
        $("div ul .first").toggleClass("rotate");
        $(".feat-show .feat-show-btn.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("active").siblings().removeClass("active");
        $(".tab-pane.first").addClass("show").siblings().removeClass("show");

        $("#hoSo").addClass("active").siblings().removeClass("active");
        $("#user-info").addClass("show first active");
        $("#user-address").removeClass("show first active");
        $("#user-pass").removeClass("show first active");
    }
    var changePassSuccess=$("#changePassSuccess").val()
    if(changePassSuccess=="true"){
        localStorage.clear();
    }
    $("#modalNotify").modal('show');
    $("#modalNotify2").modal('show');

    $(".btnAddressCustom").click(function () {
        $(".btnAddressCustom").removeClass("btnDisable").addClass("btnActive");
        $(this).removeClass("btnActive").addClass("btnDisable");
        const shipDetailId=$(this).data('shipid');
        const accountId=$(this).data('accountid');
        $('.status-defaule').attr("hidden",true);
        $('#macDinh'+shipDetailId).removeAttr('hidden');
        console.log(shipDetailId);
        $.ajax({
            url: "/mvc/information/ship-detail/setDefault",
            method: "POST",
            data: {
              	shipDetailId: shipDetailId,
              	accountId: accountId
            },
            success: function(response) {
              	const obj = JSON.parse(response);
                console.log(obj);
            }
        });
    });
    $(".btnHuyDonHang").click(function () {
        const orderId=$(this).data('orderid');
        console.log(orderId);
        $.ajax({
            url: "/mvc/information/order/setOrderStatus",
            method: "POST",
            data: {
              	orderStatus: 'Đã hủy',
              	orderId: orderId
            },
            success: function(response) {
              	const obj = JSON.parse(response);
                console.log(obj);
                $('#modalHuyDonHang'+orderId).modal('toggle');
                $('#modalKetQuaHuyDonHang').modal('show');
            }
        });
      });
    $(".btnHuyDonHang1").click(function () {
        const orderId=$(this).data('orderid');
        console.log(orderId);
        $.ajax({
            url: "/mvc/information/order/setOrderStatus",
            method: "POST",
            data: {
                orderStatus: 'Đã hủy',
                orderId: orderId
            },
            success: function(response) {
                const obj = JSON.parse(response);
                console.log(obj);
                $('#modalHuyDonHang1'+orderId).modal('toggle');
                $('#modalKetQuaHuyDonHang').modal('show');
            }
        });
      });

    $('.customBtnMuaLai').on('click', (element) => {
        const thisBtn = $(element.currentTarget);
        const orderId = thisBtn.data("maorder");
        $.ajax({
            url: "/mvc/information/order/repurchase",
            method: "POST",
            // type: "application/json",
            data: {
                orderId: orderId
            },
            success: function(response) {
                console.log(response);
                toastr.options.timeOut = 1000;
                toastr.options = {
                    "positionClass": "toast-bottom-right"
                }
                response.listCartDetail.forEach(function(item){
                    console.log("aaa");
                    toastr.success('+'+item.amount+' '+item.productName+' vào giỏ hàng');
                });
                $('#checkout_items').html(response.numberOfCart);
            }
        });
    });

    let selectedRating=0;
    let selectedRating1=0;
    $('.fa-star').hover(function(){
        const thisRating=$(this).data('rating');
        const productId=$(this).data('idsp');
        const orderId=$(this).data('orderid');
        $(this).addClass('checked').siblings().removeClass('checked');
        for(let i=1;i<=thisRating;i++){
            $('.star'+i+''+orderId+''+productId).addClass('checked');
            $('.star1'+i+''+orderId+''+productId).addClass('checked');
        }
    });
    $('.fa-star').on('mouseleave',function(){
        const productId=$(this).data('idsp');
        const orderId=$(this).data('orderid');
        $(this).siblings().removeClass('checked');
        $(this).removeClass('checked');
        for(let i=1;i<=selectedRating;i++){
            $('.star'+i+''+orderId+''+productId).addClass('checked');
        }
        for(let i=1;i<=selectedRating1;i++){
            $('.star1'+i+''+orderId+''+productId).addClass('checked');
        }
    });
    $('.fa-star').on('click', function(){
        selectedRating=$(this).data('rating');
        selectedRating1=$(this).data('rating');
        const productId=$(this).data('idsp');
        const orderId=$(this).data('orderid');
        $('#rate'+orderId+''+productId).val(selectedRating);
        $('#rate1'+orderId+''+productId).val(selectedRating1);
        console.log("selectedRating",selectedRating)
        $(this).addClass('checked').siblings().removeClass('checked');
        for(let i=1;i<=selectedRating;i++){
            $('.star'+i+''+orderId+''+productId).addClass('checked');
        }
        for(let i=1;i<=selectedRating1;i++){
            $('.star1'+i+''+orderId+''+productId).addClass('checked');
        }
    });

    $('.btnHoanThanhCustom').on('click',function(){
        const orderId=$(this).data('orderid');
        console.log('orderId',orderId);
        console.log('reviewCustom',$('.reviewCustom'));
        $('.reviewCustom').each(function(){
            const comment=$(this).find('textarea[name="comment"]').val();
            const rate=$(this).find('input[name="rate"]').val();
            const productId=$(this).find('input[name="productId"]').val();
            console.log('comment',comment);
            console.log('rate',rate);
            console.log('productId',productId);
            if(comment && rate && productId){
                $.ajax({
                    url: "/mvc/information/review/save",
                    method: "POST",
                    // type: "application/json",
                    data: {
                        orderId:orderId,
                        productId:productId,
                        comment: comment,
                        rate:rate
                    },
                    success: function(response) {
                        const obj = JSON.parse(response);
                        console.log(obj);
                        if(obj.status=='Thành công'){
                            $("#modalDanhGia1"+orderId).modal('toggle');
                            $('#modalNotify3').modal('show');
                        }
                    }
                });
            }
        });
    });

    //custom
    $('.btnHoanThanhCustom1').on('click',function(){
        const orderId=$(this).data('orderid');
        console.log('orderId',orderId);
        console.log('reviewCustom1',$('.reviewCustom1'));
        $('.reviewCustom1').each(function(){
            const comment=$(this).find('textarea[name="comment"]').val();
            const rate=$(this).find('input[name="rate"]').val();
            const productId=$(this).find('input[name="productId"]').val();
            console.log('comment',comment);
            console.log('rate',rate);
            console.log('productId',productId);
            if(comment && rate && productId){
                $.ajax({
                    url: "/mvc/information/review/save",
                    method: "POST",
                    // type: "application/json",
                    data: {
                        orderId:orderId,
                        productId:productId,
                        comment: comment,
                        rate:rate
                    },
                    success: function(response) {
                        const obj = JSON.parse(response);
                        console.log(obj);
                        if(obj.status=='Thành công'){
                            $("#modalDanhGia"+orderId).modal('toggle');
                            $('#modalNotify3').modal('show');
                        }
                    }
                });
            }
        });
    });
});
