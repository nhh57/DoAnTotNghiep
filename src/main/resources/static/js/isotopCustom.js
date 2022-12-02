var checkboxes = $('.checkboxCustom');
    checkboxes.change(function () {
    // khởi tạo mảng chứa giá trị checkbox
    var arrayFilter = [];
    // Chạy vòng lặp
    checkboxes.each(function (i, element) {
        // Nếu được check thì thêm giá trị vào mảng
        if (element.checked) {
            arrayFilter.push(element.value);
        }
    });
    // combine inclusive filters
    var filterValue = arrayFilter.length ? arrayFilter.join(', ') : '*';
    console.log('filterValue', filterValue)
    $(".product-lists").isotope({
        filter: filterValue,
    });
});

var sortTypes = $('.type_sorting_btn');
var sortNums = $('.num_sorting_btn');
// Short based on the value from the sorting_type dropdown
sortTypes.each(function () {
    $(this).on('click', function () {
        $('.type_sorting_text').text($(this).text());
    });
});

// Show only a selected number of items
sortNums.each(function () {
    $(this).on('click', function () {
        $('.num_sorting_text').text($(this).text());
    });
});