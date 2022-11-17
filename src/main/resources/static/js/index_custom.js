
$(document).ready(() => {
	$('.btnAddToCart').on('click', (element) => {
		const thisBtn = $(element.currentTarget);
		const masp = thisBtn.data("masanpham");
		console.log("clickBtnAddToCart")
		$.ajax({
			url: "/mvc/shopping-cart/addToCart",
			method: "POST",
			// type: "application/json",
			data: {
				maSanPham: masp,
				soLuong:1
			},
			success: function(response) {
				const obj = JSON.parse(response);
				console.log(obj)
				$('#checkout_items').html(obj.soLuong);
			}
		});
	});

	//Fly to cart
	$('.items').flyto({
    		item: '.product_img', // item cần bay đến giỏ hàng
    		target: '.checkout', // giỏ hàng
    		button: '.btnAddToCart' // button add vào giỏ hàng
    	});

    	if ($('.bbb_viewed_slider').length) {
        				var viewedSlider = $('.bbb_viewed_slider');

        				viewedSlider.owlCarousel(
        					{
        						loop: true,
        						margin: 30,
        						autoplay: true,
        						autoplayTimeout: 6000,
        						nav: false,
        						dots: false,
        						responsive:
        						{
        							0: { items: 1 },
        							575: { items: 2 },
        							768: { items: 3 },
        							991: { items: 4 },
        							1199: { items: 6 }
        						}
        					});

        				if ($('.bbb_viewed_prev').length) {
        					var prev = $('.bbb_viewed_prev');
        					prev.on('click', function () {
        						viewedSlider.trigger('prev.owl.carousel');
        					});
        				}

        				if ($('.bbb_viewed_next').length) {
        					var next = $('.bbb_viewed_next');
        					next.on('click', function () {
        						viewedSlider.trigger('next.owl.carousel');
        					});
        				}
        			}
});