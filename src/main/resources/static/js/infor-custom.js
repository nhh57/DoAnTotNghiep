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