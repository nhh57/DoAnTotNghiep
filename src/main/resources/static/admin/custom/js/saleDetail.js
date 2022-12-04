$(document).ready(function () {
    $("#filterCustom").change((e)=> {
    var filterCustom = $(e.currentTarget);
        var value = filterCustom.val().toLowerCase();
        $("#tableFilter tr").filter(function () {
            if(value=='tất cả'){
                filterCustom.toggle(filterCustom.text().toLowerCase().indexOf('') > -1)
            }else{
                filterCustom.toggle(filterCustom.text().toLowerCase().indexOf(value) > -1)
            }
        });
    });
    $("#myInput").on("keyup", function () {
         var value = $(this).val().toLowerCase();
         $("#tableFilter tr").filter(function () {
             $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
         });
    });

    $("#checkAll").click(function(){
        $('input:checkbox').not(this).prop('checked', this.checked);
    });

    $('.inputNumberCustom').dblclick((e)=>{
        const discountElement=$(e.currentTarget);
        const value=discountElement.val();
        discountElement.attr("readonly", false);
        discountElement.css({"border":"2px solid gray",
                              "border-radius":"5px"})

    });
    $('.inputNumberCustom').blur((e)=>{
        const discountElement=$(e.currentTarget);
        const discount=discountElement.val();
        const id = discountElement.data("id");
        const max = parseInt(discountElement.attr('max'));
        const min = parseInt(discountElement.attr('min'));
        if(discount > max){
            discount=max;
        }else if(discount < min){
            discount=min;
        }
        discountElement.attr("readonly", true);
        discountElement.css({"border":"none"})
        $.ajax({
            url: "/mvc/admin/sale/sale-detail/change-discount",
            method: "POST",
            // type: "application/json",
            data: {
                id:id,
                discountSale: discount
            },
            success: function(response) {
                const obj = JSON.parse(response);
                console.log(obj)
                console.log('result',obj);
            }
        });
    });

    $('.clearOne').click((e)=>{
        const thisBtnClear=$(e.currentTarget);
        const id=thisBtnClear.data("id");
        $.ajax({
            url: "/mvc/admin/sale/sale-detail/remove",
            method: "POST",
            // type: "application/json",
            data: {
                id:id
            },
            success: function(response) {
                const obj = JSON.parse(response);
                console.log(obj)
                console.log('result',obj);
            }
        });
    });

     $('#clearAll').click((e)=>{
            const thisBtnClear=$(e.currentTarget);
            const saleId=thisBtnClear.data("saleid");
            $.ajax({
                url: "/mvc/admin/sale/sale-detail/remove-all",
                method: "POST",
                // type: "application/json",
                data: {
                    saleId:saleId
                },
                success: function(response) {
                    const obj = JSON.parse(response);
                    console.log(obj)
                    console.log('result',obj);
                }
            });
        });

    //Auto min max
    $( ".inputNumberCustom" ).change(function() {
      const max = parseInt($(this).attr('max'));
      const min = parseInt($(this).attr('min'));
      if ($(this).val() > max)
      {
          $(this).val(max);
      }
      else if ($(this).val() < min)
      {
          $(this).val(min);
      }
    });
    $('.btnAddSaleDetail').click(()=>{
        $('input[name="checkBoxCustom"]:checked').each(function(){
           const saleId=$(this).data('saleid')
           const productId=$(this).val();
           $.ajax({
              url: "/mvc/admin/sale/sale-detail/save",
              method: "POST",
              // type: "application/json",
              data: {
                  saleId:saleId,
                  productId:productId
              },
              success: function(response) {
                  const obj = JSON.parse(response);
                  console.log(obj)
                  console.log('result',obj);
                  location.reload();
              }
          });
        });
    });


});