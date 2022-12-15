$("#myTab li a").on("shown.bs.tab", function (e) {
    const id = $(e.target).attr("href").substr(1);
    $('#idTab').val(id);
    $('.tab-panel-custom').each(function(){
        let href = $(this).attr('href');
        const tab = $('#idTab').val();
        if (href.indexOf('tabPanelIndex') > -1) {
            href=href.substring(0,href.indexOf('&tabPanelIndex'));
            href+='&tabPanelIndex='+tab;
        }else{
            href+='&tabPanelIndex='+tab;
        }
        $(this).attr('href', href);
    });
});
let tab=$('#idTab').val();
$('#myTab a[href="#' + tab + '"]').tab('show');

$('#loaiThoiGian').on('change', function () {
    const value = $(this).val();
    if (value === 'year') {
        $('#filterYear').attr('hidden', false);
        $('#filterMonth').attr('hidden', true);
        $('#filterStage').attr('hidden', true);
    } else if (value === 'quarter') {
        $('#filterYear').attr('hidden', true);
        $('#filterMonth').attr('hidden', true);
        $('#filterStage').attr('hidden', true);
    } else if (value === 'month') {
        $('#filterYear').attr('hidden', true);
        $('#filterMonth').attr('hidden', false);
        $('#filterStage').attr('hidden', true);
    } else if (value === 'stage') {
        $('#filterYear').attr('hidden', true);
        $('#filterMonth').attr('hidden', true);
        $('#filterStage').attr('hidden', false);
    }
});
$('#yFilter').on('click',function(){
    const column=$('#filterYear #yColumn').val();
    const year=$('#filterYear #yYear').val();
    const idTab=$('#idTab').val();
    location ='/mvc/admin/report?tag=3&year=' + year+'&column='+column+'&tabPanelIndex='+idTab;
});

$('#mFilter').on('click',function(){
    const year=$('#filterMonth #mYear').val();
    const month=$('#filterMonth #mMonth').val();
    const idTab=$('#idTab').val();
    location ='/mvc/admin/report?tag=3&mYear=' + year+'&mMonth='+month+'&tabPanelIndex='+idTab;
});
$('#sFilter').on('click',function(){
    const startDate=$('#sStartDate').val();
    const endDate=$('#sEndDate').val();
    const idTab=$('#idTab').val();
    location ='/mvc/admin/report?tag=3&sStartDate=' + startDate+'&sEndDate='+endDate+'&tabPanelIndex='+idTab;
});

$('#sStartDate').on('change',function(){
    const startDate= new Date($('#sStartDate').val());
    const maxDate=new Date(startDate.setDate(startDate.getDate() + 31));
    const maxDateString=maxDate.toISOString().split('T')[0];
    $('#sEndDate').attr('min',$('#sStartDate').val());
    $('#sEndDate').attr('max',maxDateString);
});

