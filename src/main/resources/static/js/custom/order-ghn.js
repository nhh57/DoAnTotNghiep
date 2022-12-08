const token="fd90ee79-74f9-11ed-9dc6-f64f768dbc22";
const phuongThucGiaoHang="https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";
const phiVanChuyen="https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
const shopId=3532123; // my shop
const fromDistrict=1454; // quận 12


const checkThanhToan =()=>{
    let check1=false;
    let check2=false;
    $('.check1').each(function(){
        if($(this).prop("checked")){
            check1=true;
        }
    });
    $('.check1').each(function(){
        if($(this).prop("checked")){
            check2=true;
        }
    });
    if(check1 && check2){
        $('#btnThanhToan').prop('disabled', false);
    }else{
        $('#btnThanhToan').prop('disabled', true);
    }
}

$('.check2').click(function(){
   checkThanhToan();
});

const loadPhuongThucGiaoHang =() => {
    //1700 là định quán
	url = phuongThucGiaoHang+"?shop_id="+shopId+"&from_district="+fromDistrict+"&to_district="+1700;
	getAPI(url, (result) => {
		let html="";
		if (result.code == 200 && result.data) {
		    const renderPhuongThucGiaoHang = document.querySelector("#renderPhuongThucGiaoHang");
		    let i=0;
			for(let shipMethod of result.data){
			    html+='<div class="form__radio">';
			    html+='<label for="phuongThucGiaoHang'+i+'">'+shipMethod.short_name+'</label>';
			    html+='<input data-shipmethod="'+shipMethod.short_name+'" class="check1" id="phuongThucGiaoHang'+i+'" value='+shipMethod.service_id+' name="ship-method" type="radio"/></div>';
			    i++;
			}
            renderPhuongThucGiaoHang.innerHTML=html;
            $('.check1').click(function(){
               const serviceId=$(this).val();
               const shipMethodName=$(this).data('shipmethod');
               const insuranceValue=$('#tongTienDonHang').val();
               $("#shipMethod").val(shipMethodName);
               $("#shipMethodId").val(serviceId);
               getPhiVanChuyen(serviceId,insuranceValue);
               $('#tableTongTien').show();
               checkThanhToan();
            });
		}
	})
}

const getPhiVanChuyen =(serviceId,insuranceValue) => {
    //service_id là id của phương thức giao hàng
    //insurance_value là giá của đơn hàng
    //from_district_id là mã huyện của nơi giao
    //to_district_id là mã huyện của người nhận
    //to_ward_code là mã xã của người nhận
    // height, length, weight, with lần lượt là chiều cao, chiều dài, cân nặng, chiều rộng
	url = phiVanChuyen+"?service_id="+serviceId+"&insurance_value="+insuranceValue+"&coupon="
	+"&from_district_id="+1454+"&to_district_id="+1700+"&to_ward_code="+480401+"&height="+15+"&length="+20+"&weight="+200+"&with="+20;
	getAPI(url, (result) => {
		if (result.code == 200 && result.data) {
			console.log("phiVanChuyen",result.data);
			let phiShip=result.data.total;
			$('#phiShipInput').val(phiShip);
			let phaiTra=+insuranceValue + +phiShip;
			$('#tongTienInput').val(phaiTra);
			phiShip = phiShip.toLocaleString('it-IT', {style : 'currency', currency : 'VND'}).replaceAll(".",",").replace("VND","đ");
			$('#phiShip').html(phiShip);
			phaiTra = phaiTra.toLocaleString('it-IT', {style : 'currency', currency : 'VND'}).replaceAll(".",",").replace("VND","đ");
			$('#phaiTra').html(phaiTra);
		}
	})

}

//callback
const getAPI = (url, callback) => {
	fetch(url, {
		method: 'GET',
		headers: {
			token,
		}
	}).then((response) => response.json())
		.then((result) => { callback(result); })
		.catch((error) => { console.error('Error:', error); });
}

loadPhuongThucGiaoHang();
checkThanhToan();
$('#tableTongTien').hide();