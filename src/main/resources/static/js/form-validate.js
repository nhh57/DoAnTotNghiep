function valid_datas( f ){
	
	if( f.name.value == '' ){
		jQuery('#form_status').html('<span class="wrong">Họ và tên không được để trống!</span>');
		notice( f.fullName );
	}else if( f.email.value == '' ){
		jQuery('#form_status').html('<span class="wrong">Email không được để trống!</span>');
		notice( f.email );
	}else if( f.phone.value == '' ){
        jQuery('#form_status').html('<span class="wrong">Số điện thoại không được để trống!</span>');
        notice( f.phone );
    }else if( f.subject.value == '' ){
        jQuery('#form_status').html('<span class="wrong">Tiêu đề không được để trống!</span>');
        notice( f.subject );
	}else if( f.message.value == '' ){
		jQuery('#form_status').html('<span class="wrong">Nội dung không được để trống!</span>');
		notice( f.message );
	}else{
        $.ajax({
            url: "/mvc/contact",
            method: "POST",
            // type: "application/json",
            data: {
                fullName: f.fullName.value,
                email: f.email.value,
                phone: f.phone.value,
                subject: f.subject.value,
                message: f.message.value
            },
            success: function(response) {
                const obj = JSON.parse(response);
                console.log(obj);
                unNotice(f.fullName);
                unNotice(f.email);
                unNotice(f.phone);
                unNotice(f.subject);
                unNotice(f.message);
                $("#modalNotify").modal('show');
            }
        });
	}
	
	return false;
}

function notice( f ){
	jQuery('#fruitkha-contact').find('input,textarea').css('border','none');
	f.style.border = '1px solid red';
	f.focus();
}
function unNotice( f ){
    jQuery('#form_status').html('');
	jQuery('#fruitkha-contact').find('input,textarea').css('border');
	f.style.border = '1px solid #ddd';
	f.blur();
}
