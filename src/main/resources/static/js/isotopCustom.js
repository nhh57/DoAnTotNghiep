var $container;
var filters = {};

var brandCustom=$('.brandCustom');
var categoryCustom=$('.categoryCustom');
var priceCustom=$('.priceCustom');

$(function(){

  $container = $('#product-lists');
  // do stuff when checkbox change
  $('#options').on( 'change', function( jQEvent ) {
    var $checkbox = $( jQEvent.target );
    manageCheckbox( $checkbox );

    var comboFilter = getComboFilter( filters );

    $container.isotope({ filter: comboFilter });
  });

});


var category=[];
categoryCustom.each(function (i, element) {
  category.push(element.value);
});

var brand=[];
brandCustom.each(function (i, element) {
  brand.push(element.value);
});

var price=[];
priceCustom.each(function (i, element) {
   price.push(element.value);
});

var data={
    category,
    brand,
    price,
};

function getComboFilter( filters ) {
  var i = 0;
  var comboFilters = [];
  var message = [];

  for ( var prop in filters ) {
    message.push( filters[ prop ].join(' ') );
    var filterGroup = filters[ prop ];
    // skip to next filter group if it doesn't have any values
    if ( !filterGroup.length ) {
      continue;
    }
    if ( i === 0 ) {
      // copy to new array
      comboFilters = filterGroup.slice(0);
    } else {
      var filterSelectors = [];
      // copy to fresh array
      var groupCombo = comboFilters.slice(0); // [ A, B ]
      // merge filter Groups
      for (var k=0, len3 = filterGroup.length; k < len3; k++) {
        for (var j=0, len2 = groupCombo.length; j < len2; j++) {
          filterSelectors.push( groupCombo[j] + filterGroup[k] ); // [ 1, 2 ]
        }

      }
      // apply filter selectors to combo filters for next group
      comboFilters = filterSelectors;
    }
    i++;
  }

  var comboFilter = comboFilters.join(', ');
  return comboFilter;
}

function manageCheckbox( $checkbox ) {
  var checkbox = $checkbox[0];

  var group = $checkbox.parents('.option-set').attr('data-group');
  // create array for filter group, if not there yet
  var filterGroup = filters[ group ];
  if ( !filterGroup ) {
    filterGroup = filters[ group ] = [];
  }

  var isAll = $checkbox.hasClass('all');
  // reset filter group if the all box was checked
  if ( isAll ) {
    delete filters[ group ];
    if ( !checkbox.checked ) {
      checkbox.checked = 'checked';
    }
  }
  // index of
  var index = $.inArray( checkbox.value, filterGroup );

  if ( checkbox.checked ) {
    var selector = isAll ? 'input' : 'input.all';
    $checkbox.siblings( selector ).removeAttr('checked');
  if ( !isAll && index === -1 ) {
        // add filter to group
        filters[ group ].push( checkbox.value );
      }

    } else if ( !isAll ) {
      // remove filter from group
      filters[ group ].splice( index, 1 );
      // if unchecked the last box, check the all
      if ( !$checkbox.siblings('[checked]').length ) {
        $checkbox.siblings('input.all').attr('checked', 'checked');
      }
    }

}




// Sort theo số sản phẩm
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