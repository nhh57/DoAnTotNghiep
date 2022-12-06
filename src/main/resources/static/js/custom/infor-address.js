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
const loadTinh = () => {
	req.url = tinh;
	getAPI(req.url, (result) => {
		const selectTinh = document.querySelector("#chonTinh");
		let options = "";
		if (result.code == 200 && result.data) {
			options += `<option value="" selected="selected">--Chọn tỉnh--</option>`;
			for (let tinh of result.data) {
				options += `<option value="${tinh.ProvinceID}">${tinh.ProvinceName}</option>`;
			}
		}
		selectTinh.innerHTML = options;
	})
}

const handleChonTinh = (provinceSelect) => {
	const provinceId = provinceSelect.currentTarget.value;
	req.url = huyen + "?province_id=" + provinceId;
	getAPI(req.url, (result) => {
		const selectHuyen = document.querySelector("#chonHuyen");
		let options = "";
		if (result.code == 200 && result.data) {
			options += `<option value="" selected="selected">--Chọn huyện--</option>`;
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
	getAPI(req.url, (result) => {
		const selectXa = document.querySelector("#chonXa");
		let options = "";
		if (result.code == 200 && result.data) {
			options += `<option value="" selected="selected">--Chọn xã--</option>`;
			for (let xa of result.data) {
				options += `<option value="${xa.WardId}">${xa.WardName}</option>`;
			}
		}
		selectXa.innerHTML = options;
	})
}
const getAPI = (url, callback) => {
	fetch(url, {
		method: 'GET', // or 'PUT'
		headers: {
			token,
		}
	}).then((response) => response.json())
		.then((result) => { callback(result); })
		.catch((error) => { console.error('Error:', error); });
}

loadTinh();

$('#soNha').on('change', function(e) {
	const thisInput=e.currentTarget.value;
     if (this.value.length > 1) {
		const tenTinh=$("#chonTinh option:selected").text();
		const tenHuyen=$("#chonHuyen option:selected").text();
		const tenXa=$("#chonXa option:selected").text();
       	const diaChi=thisInput+', '+tenXa+", "+tenHuyen+", "+ tenTinh;
       	$('#address').text(diaChi)
     }
});
