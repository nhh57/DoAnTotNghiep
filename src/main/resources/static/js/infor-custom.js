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
});
