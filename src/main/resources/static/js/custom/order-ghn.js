const token="fd90ee79-74f9-11ed-9dc6-f64f768dbc22";
const phuongThucGiaoHang="https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";
const phiVanChuyen="https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
const tinh="https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
const huyen="https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
const xa="https://online-gateway.ghn.vn/shiip/public-api/master-data/ward";
const shopId=3532123; // my shop
const fromDistrict=1454; // quận 12

let req = {
 method: 'GET',
 url: '',
 headers: {
   'token': token
 }
};

$(".btnAddressCustom").click(function () {
    $('#tableTongTien').hide();
    $(".btnAddressCustom").removeClass("btnActive").addClass("btnDisable");
    $(this).removeClass("btnDisable").addClass("btnActive");
    const shipDetailId=$(this).data('shipid');
    const shipFullName=$(this).data('shipname');
    const shipPhone=$(this).data('shipphone');
    const shipAddress=$(this).data('shipaddress');
    const districtId=$(this).data('districtid');
    const wardId=$(this).data('wardid');
    $("#shipDetailId").val(shipDetailId);
    $("#districtId").val(districtId);
    $("#wardId").val(wardId);
    $("#fullName").val(shipFullName);
    $("#phone").val(shipPhone);
    $("#address").val(shipAddress);
    loadPhuongThucGiaoHang();
});
$("#modalNotify").modal('show');

const loadTinh =() => {
	req.url = tinh;
	getAPI(req.url, (result) => {
		const selectTinh = document.querySelector("#chonTinh");
		let options = "";
		options += `<option value="" selected="selected">--Chọn tỉnh--</option>`;
		if (result.code == 200 && result.data) {
			for (let tinh of result.data) {
				options += `<option value="${tinh.ProvinceID}">${tinh.ProvinceName}</option>`;
			}
		}
		selectTinh.innerHTML = options;
		//update
		let options2 = "";
		options2 += `<option value="" >--Chọn tỉnh--</option>`;
		$(".chonTinh").each(function(){
		    const province=$(this).data('province');
            if (result.code == 200 && result.data) {
                for (let tinh2 of result.data) {
                    if(tinh2.ProvinceName==province){
                        options2 += `<option `+`selected="selected"`+` value="${tinh2.ProvinceID}">${tinh2.ProvinceName}</option>`;
                    }else{
                        options2 += `<option value="${tinh2.ProvinceID}">${tinh2.ProvinceName}</option>`;
                    }
                }
            }
            $(this).html(options2);
		});
	})
}

const handleChonTinh = (provinceSelect) => {
	const provinceId = provinceSelect.currentTarget.value;
	req.url = huyen + "?province_id=" + provinceId;
	const maTinh=$("#chonTinh option:selected").val();
	const tenTinh=$("#chonTinh option:selected").text();
    $('#hiddenProvinceId').val(maTinh);
    $('#hiddenProvince').val(tenTinh);
	getAPI(req.url, (result) => {
		const selectHuyen = document.querySelector("#chonHuyen");
		let options = "";
		options += `<option value="" selected="selected">--Chọn huyện--</option>`;
		if (result.code == 200 && result.data) {
			for (let huyen of result.data) {
				options += `<option value="${huyen.DistrictID}">${huyen.DistrictName}</option>`;
			}
		}
		selectHuyen.innerHTML = options;
	})
}

const handleChonHuyen = (districtSelect) => {
	const districtId = districtSelect.currentTarget.value;
	req.url=xa+"?district_id="+districtId;
	const maHuyen=$("#chonHuyen option:selected").val();
	const tenHuyen=$("#chonHuyen option:selected").text();
    $('#hiddenDistrictId').val(maHuyen);
    $('#hiddenDistrict').val(tenHuyen);
	getAPI(req.url, (result) => {
		const selectXa = document.querySelector("#chonXa");
		let options = "";
		options += `<option value="" selected="selected">--Chọn xã--</option>`;
		if (result.code == 200 && result.data) {
			for (let xa of result.data) {
				options += `<option value="${xa.WardCode}">${xa.WardName}</option>`;
			}
		}
		selectXa.innerHTML = options;
	})
}
$('#chonXa').on('change', function(e) {
	const maXa=$("#chonXa option:selected").val();
	const tenXa=$("#chonXa option:selected").text();
    $('#hiddenWardId').val(maXa);
	$('#hiddenWard').val(tenXa);
});

//call back
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

loadTinh();

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
    const districtId=$("#districtId").val();
	url = phuongThucGiaoHang+"?shop_id="+shopId+"&from_district="+fromDistrict+"&to_district="+districtId;
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
    const districtId=$("#districtId").val();
    const wardId=$("#wardId").val();
	url = phiVanChuyen+"?service_id="+serviceId+"&insurance_value="+insuranceValue+"&coupon="
	+"&from_district_id="+1454+"&to_district_id="+districtId+"&to_ward_code="+wardId+"&height="+15+"&length="+20+"&weight="+200+"&with="+20;
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
loadPhuongThucGiaoHang();
checkThanhToan();
$('#tableTongTien').hide();