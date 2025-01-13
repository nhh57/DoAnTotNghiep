// Mảng để lưu các bookId đã có trong giỏ hàng
var cartItems = [];

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

				// Nếu sản phẩm là mới (ID khác), tăng số hiển thị icon và thêm vào danh sách `cartItems`
				if (!cartItems.includes(bookId)) {
					cartItems.push(bookId); // Thêm bookId vào danh sách giỏ hàng
					$("#totalCartItems").text(totalCartItems + 1); // Tăng hiển thị icon
				}

				// Hiển thị thông báo thành công
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

	// Thêm nội dung và biểu tượng
	notification.innerHTML = `<span>${type === "success" ? "✔️" : "❌"} ${message}</span>`;

	// Áp dụng CSS trực tiếp
	notification.style.padding = "10px 20px";
	notification.style.marginBottom = "10px";
	notification.style.borderRadius = "5px";
	notification.style.color = "#fff";
	notification.style.fontSize = "14px";
	notification.style.boxShadow = "0 2px 5px rgba(0, 0, 0, 0.2)";
	notification.style.transition = "opacity 0.5s ease-in-out";
	notification.style.opacity = "1";
	notification.style.position = "relative";
	notification.style.backgroundColor = type === "success" ? "#4CAF50" : "#f44336"; // Màu sắc dựa trên loại thông báo

	// Thêm hiệu ứng trượt
	notification.style.transform = "translateX(100%)";
	notification.style.animation = "slide-in 0.5s forwards";

	// Đặt vị trí của thông báo
	var container = document.getElementById("notification-container");
	if (!container) {
		container = document.createElement("div");
		container.id = "notification-container";
		container.style.position = "fixed";
		container.style.top = "20px";
		container.style.right = "20px";
		container.style.zIndex = "9999";
		document.body.appendChild(container);
	}

	container.appendChild(notification);

	// Ẩn thông báo sau 3 giây
	setTimeout(function () {
		notification.style.opacity = "0";
		setTimeout(function () {
			notification.remove();
		}, 500); // Đợi hiệu ứng mờ dần hoàn tất
	}, 3000);
}

// Thêm animation keyframes bằng JavaScript
const styleSheet = document.createElement("style");
styleSheet.type = "text/css";
styleSheet.innerHTML = `
    @keyframes slide-in {
        from {
            transform: translateX(100%);
        }
        to {
            transform: translateX(0);
        }
    }
`;
document.head.appendChild(styleSheet);





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
