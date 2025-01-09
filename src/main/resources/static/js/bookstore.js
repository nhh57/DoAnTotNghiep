function addCart(bookId) {
	var url = "/addCart?id=" + bookId;

	// Lấy số lượng sản phẩm hiện tại trong giỏ hàng
	var totalCartItems = parseInt($("#totalCartItems").text()) || 0;

	$.ajax({
		url: url,
		type: "GET",
		dataType: "json",
		success: function(result) {
			if (result.unsuccessful) {
				console.log("Error Notification: ", result.unsuccessful);
				showNotification(result.unsuccessful, "error");
			} else {
				console.log("Success Notification: ", result.success);
				$("#totalCartItems").text(parseInt(totalCartItems) + 1);
				showNotification(result.success, "success");
			}
		},
		error: function(err) {
			console.error("Lỗi AJAX:", err);
			showNotification("Đã xảy ra lỗi, vui lòng thử lại.", "error");
		}
	});
}
function showNotification(message, type) {
	// Tạo một thông báo mới
	var notification = document.createElement("div");
	notification.className = "notification " + (type === "success" ? "success" : "error");
	notification.innerText = message;

	// Thêm hiệu ứng và hiển thị thông báo
	var container = document.getElementById("notification-container");
	container.appendChild(notification);

	// Ẩn thông báo sau 3 giây
	setTimeout(function() {
		notification.style.opacity = 0;
		setTimeout(function() {
			notification.remove();
		}, 500); // Đợi hiệu ứng mờ dần hoàn tất
	}, 3000);
}



function doFavorite(index) {

	var url = null;
	var totalFavorite = $("#totalFavorite").text();

	if ($('.tg-btnaddtowishlist' + '.' + index + '.hiden-flagFavorite').hasClass("bookmark")) {
		url = "/doUnFavorite?id=" + index;
		$('.tg-btnaddtowishlist' + '.' + index).removeClass('st-hiden');
		$('.tg-btnaddtowishlist' + '.' + index + '.hiden-flagFavorite').addClass('st-hiden');
		$('.tg-btnaddtowishlist' + '.' + index + '.hiden-flagFavorite').removeClass('bookmark');
		$('.tg-btnaddtowishlist' + '.' + index + '.do-flagFavorite').removeClass('st-hiden');
		$("#totalFavorite").text("");
		totalFavorite = parseInt(totalFavorite) - 1;
		$("#totalFavorite").text(totalFavorite);

	} else {
		url = "/doFavorite?id=" + index;
		$('.tg-btnaddtowishlist' + '.' + index).addClass('st-hiden');
		$('.tg-btnaddtowishlist' + '.' + index + '.hiden-flagFavorite').addClass('bookmark');
		$('.tg-btnaddtowishlist' + '.' + index + '.hiden-flagFavorite').removeClass('st-hiden');
		$('.tg-btnaddtowishlist' + '.' + index + '.do-flagFavorite').addClass('st-hiden');
		$("#totalFavorite").text("");
		totalFavorite = parseInt(totalFavorite) + 1;
		$("#totalFavorite").text(totalFavorite);

	}

	$.ajax({
		url: url,
		type: "GET",
		dataType: "json",
		data: 'json',
		success: function(result) {



		},
		error: function(err) {
		}
	}); // ajax call

}


function doUnFavorite(bookId) {

	var url = "/doUnFavorite?id=" + bookId;

	var totalFavorite = $("#totalFavorite").text();

	$.ajax({
		url: url,
		type: "GET",
		dataType: "json",
		data: 'json',
		success: function(result) {
			$("#totalFavorite").text("");
			totalFavorite = parseInt(totalFavorite) - 1;
			$("#totalFavorite").text(totalFavorite);
			$('.tg-btnaddtowishlist' + '.doUnFavorite' + '.init' + "." + bookId).remove();
			$('.tg-btnaddtowishlist' + '.doFavorite ' + '.init' + "." + bookId).remove();
			$('.tg-btnaddtowishlist' + '.doFavorite ' + "." + bookId).addClass('st-hiden');
			$('.tg-btnaddtowishlist' + '.doUnFavorite ' + "." + bookId + '.st-hiden').removeClass('st-hiden');




		},
		error: function(err) {
			// check the err for error details
		}
	}); // ajax call

}