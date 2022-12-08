const token="fd90ee79-74f9-11ed-9dc6-f64f768dbc22";
const tinh="https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
const huyen="https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
const xa="https://online-gateway.ghn.vn/shiip/public-api/master-data/ward";
let req = {
 method: 'GET',
 url: '',
 headers: {
   'token': token
 }
}

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

const loadHuyen = ()=>{
    $('.chonHuyen').each(function(){
        const provinceId=$(this).data('provinceid');
        req.url = huyen + "?province_id=" + provinceId;
        getAPI(req.url, (result) => {
            //update
            let options = "";
            options += `<option value="" >--Chọn huyện--</option>`;
            const districtName=$('.chonHuyen').data('district');
            if (result.code == 200 && result.data) {
                for (let huyen of result.data) {
                    if(huyen.DistrictName==districtName){
                        options += `<option `+`selected="selected"`+` value="${huyen.DistrictID}">${huyen.DistrictName}</option>`;
                    }else{
                        options += `<option value="${huyen.DistrictID}">${huyen.DistrictName}</option>`;
                    }
                }
            }
            $('.chonHuyen').html(options);
        });
    });
}

const loadXa = ()=>{
    $('.chonXa').each(function(){
        const districtId=$(this).data('districtid');
        req.url=xa+"?district_id="+districtId;
        getAPI(req.url, (result) => {
            let options = "";
            options += `<option value="" >--Chọn xã--</option>`;
            const wardName=$('.chonXa').data('ward');
            if (result.code == 200 && result.data) {
                for (let xa of result.data) {
                    if(xa.WardName==wardName){
                        options += `<option `+`selected="selected"`+` value="${xa.WardCode}">${xa.WardName}</option>`;
                    }else{
                        options += `<option value="${xa.WardCode}">${xa.WardName}</option>`;
                    }
                }
            }
            $('.chonXa').html(options);
        })
    })
}

const handleChonTinh2 = (provinceSelect) => {
    const chonTinh=provinceSelect.currentTarget;
    const id=chonTinh.getAttribute("data-shipid");
	const provinceId = chonTinh.value;
	const provinceName=chonTinh.options[chonTinh.selectedIndex].text;
	req.url = huyen + "?province_id=" + provinceId;
    $('.hiddenProvinceId'+id).val(provinceId);
    $('.hiddenProvince'+id).val(provinceName);
	getAPI(req.url, (result) => {
		const selectHuyen = document.querySelector(".huyenCustom"+id);
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

const handleChonHuyen2 = (districtSelect) => {
	const chonHuyen=districtSelect.currentTarget;
    const id=chonHuyen.getAttribute("data-shipid");
    const districtId=chonHuyen.value;
    const districtName=chonHuyen.options[chonHuyen.selectedIndex].text;
    $('.hiddenDistrictId'+id).val(districtId);
    $('.hiddenDistrict'+id).val(districtName);
    req.url=xa+"?district_id="+districtId;
	getAPI(req.url, (result) => {
		const selectXa = document.querySelector(".xaCustom"+id);
		let options = "";
		options += `<option value="">--Chọn xã--</option>`;
		if (result.code == 200 && result.data) {
			for (let xa of result.data) {
				options += `<option value="${xa.WardCode}">${xa.WardName}</option>`;
			}
		}
		selectXa.innerHTML = options;
	})
}

$('.chonXa').on('change', function() {
    const id=$(this).data('shipid');
	const maXa=$(".chonXa option:selected").val();
	const tenXa=$(".chonXa option:selected").text();
    $('.hiddenWardId'+id).val(maXa);
	$('.hiddenWard'+id).val(tenXa);
});

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
loadHuyen();
loadXa();
