<div class="container-fluid py-3" id="itemList">

    <!-- Title -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0"><i class="fas fa-list me-1"></i> Purchase Orders</h4>
        <button type="button" class="btn btn-primary btn-sm" data-mdb-toggle="modal"
  			data-mdb-target="#addPurchaseOrderModal">Create Purchase Order</button>
    </div>

    <!-- Card -->
    <div class="card shadow-0 border" style="height: 100vh;">
        <div class="card-body p-0">

            <!-- Table -->
            <table class="table align-middle mb-0" id="purchaseOrdertbl">
                <thead class="table-light">
                    <tr>
                    	<th>Purchase Order Number</th>
                        <th>Supplier</th>
                        <th>Purchase Order Date</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Total Item</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

        </div>
    </div>
</div>


<!-- Add Item Modal -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<div class="modal fade bd-example-modal-lg" id="addPurchaseOrderModal" tabindex="-1" aria-labelledby="addPurchaseOrderModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Purchase Order</h5>
      </div>
      <div class="modal-body">
           <table class="table table-borderless align-middle">
               <tbody>
                   <tr class="row">
                       <td class="fw-semibold col-2">Supplier Name: </td>
                       <td class=" col-4">
                           <select id="supplierName" name="supplierName" class="form-select" required>
                           </select>
                       </td>
                       <td class="fw-semibold col-2">PO Date: </td>
                       <td  class=" col-4">
                           <input type="date" class="form-input" placeholder="Enter PO Date" id="poDate">
                       </td>
                   </tr>
                   <tr class="row">
                       
                   </tr>

               </tbody>
           </table>
           <hr>
	        <h6 class="fw-bold mb-3">Add Item to Order</h6>
	        <table class="table table-borderless align-middle">
	          <tr class="row">
	            <td class="fw-semibold col-2">Item: </td>
	            <td class="col-4">
	              <select id="item" name="item" class="form-select" required></select>
	            </td>
	
	            <td class="fw-semibold col-2">Quantity: </td>
	            <td class="col-4">
	              <input type="number" id="quantity" name="quantity" class="form-input" min="1">
	            </td>
	          </tr>
	
	          <tr class="row">
	            <td class="fw-semibold col-2">Unit Price: </td>
	            <td  class="col-4">
	              <input type="text" id="unitPrice" name="unitPrice"  class="form-input" readonly="readonly">
	            </td>
	            <td class="fw-bold col-4"></td>
	            <td  class="col-8">
	            </td>
	          </tr>
	          <tr class="row">
	          	<td class="col-12">
	              <button class="btn btn-primary w-100" id="addItemBtn">Add Item</button>
	            </td>
	          </tr>
	        </table>
	
	        <hr>
	        <h6 class="fw-bold mb-3">Selected Items</h6>
        	<table class="table table-bordered" id="orderTable">
	          <thead class="table-light">
	            <tr>
	           	  <th style="display: none;">id</th>
	              <th>Item</th>
	              <th>Quantity</th>
	              <th>Unit Price</th>
	              <th>Total</th>
	              <th>Remove</th>
	            </tr>
	          </thead>
	          <tbody></tbody>
        	</table>

	        <div class="text-end fw-bold fs-5">
	          Order Total: <span id="orderTotal">0</span>
	        </div>

           <!-- Hidden CSRF -->
           <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-mdb-ripple-init
          data-mdb-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="placeOrder" data-mdb-ripple-init>Place Order</button>
      </div>
    </div>
  </div>
</div>


<div class="modal fade bd-example-modal" id="viewOrderItemsModal" tabindex="-1" aria-labelledby="viewOrderItemsModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Purchase Order : <span id="poHeading"></span></h5>
      </div>
      <div class="modal-body">
      	   <h6 class="fw-bold mb-3">Purchase Order Items</h6>
           <table class="table table-bordered align-middle" id="itemTbl">
               <thead>
                 <th>Item</th>
                 <th>Quantity</th>
                 <th>Unit Price</th>
                 <th>Total Price</th>
               </thead>
               <tbody>
               </tbody>
           </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-mdb-ripple-init data-mdb-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
  
<script type="text/javascript">


	function loadPurchaseOrderTable() {

		$.ajax({
			  url: "/backlogtracker/supplier/listOrders", 
			  type: "GET",
			  success: function(response) {
			    var tableBody = $('#purchaseOrdertbl tbody');
		        tableBody.empty();
		        $.each(response, function(index, resp) {
		            var row = '<tr>' +
		            		  '<td> PO - ' + resp.id + ' </td>' +
		            		  '<td>' + resp.supplierName + '</td>' +
		                      '<td>' + resp.poDate + '</td>' +
		                      '<td>' + resp.totalAmount + '</td>' +
		                      '<td>' + resp.status + '</td>' +
		                      '<td>' + resp.totalItems + '</td>' +
		                      '<td><button class="btn btn-sm btn-outline-primary viewItem" data-poid="'+resp.id+'" data-poname="PO - '+resp.id+'">View Items</button>&nbsp;&nbsp;'+
		                      '<button class="btn btn-sm btn-outline-success downloadInvoice" data-poid="'+resp.id+'" data-poname="PO - '+resp.id+'">Download Invoice</button></td>'+
		                      '</tr>';
		            tableBody.append(row);
		        });
		        loadSuppliers();
			  },
			  error: function(xhr, status, error) {
			    console.error("Error:", status, error);
			  }
			});
	}

	loadPurchaseOrderTable();

	function loadSuppliers() {
		$.ajax({
		  url: "/backlogtracker/supplier/findAllActive", 
		  type: "GET",
		  success: function(response) {
		    var supplierSelect = $('#supplierName');
		    supplierSelect.empty();
		    supplierSelect.append('<option value="-1">Select Supplier</option>');
	        $.each(response, function(index, resp) {
	        	supplierSelect.append('<option value="' + resp.id + '">' + resp.name + '</option>');
	        	
	        });
		  },
		  error: function(xhr, status, error) {
		    console.error("Error:", status, error);
		  }
		});
	}

	 var addPurchaseOrderModal = new mdb.Modal($('#addPurchaseOrderModal'));

	//$(document).on('shown.mdb.modal', '#addPurchaseOrderModal', function () {
	$('#addPurchaseOrderModal').on('shown.mdb.modal', function() {
		flatpickr("#poDate", {
		    dateFormat: "d/m/Y",
		    allowInput: true,
		    disableMobile: "true",
		    maxDate: "today"
		});

	  $('#supplierName').select2({
          dropdownParent: $('#addPurchaseOrderModal'), // Important for MDB modals
          placeholder: "Select Supplier",
          allowClear: true,
          width: '100%'
      });    

	  $('#item').select2({
          dropdownParent: $('#addPurchaseOrderModal'), // Important for MDB modals
          placeholder: "Select Item",
          allowClear: true,
          width: '100%'
      });  
	});

	//$(document).on('hidden.mdb.modal', '#addPurchaseOrderModal', function () {
	$('#addPurchaseOrderModal').on('hidden.mdb.modal', function() {
	    $('#quantity').val('');
	    $('#unitPrice').val('');
	    // Reset select
	    $('#item').val(null).trigger('change');
	    $('#supplierName').val(null).trigger('change');

	    var fpInstance = $('#poDate')[0]._flatpickr;
	    if (fpInstance) {
	        fpInstance.clear();  // resets the date field
	    }
	    $('#orderTotal').text('0');
	    $('#orderTable tbody').html('');
	});

	$(document).off('change', '#supplierName').on('change', '#supplierName', function () {
		$.ajax({
		  url: "/backlogtracker/item/findAll", 
		  type: "GET",
		  success: function(response) {
		    var itemSelect = $('#item');
		    itemSelect.empty();
		    itemSelect.append('<option value="-1">Select Item</option>');
	        $.each(response, function(index, resp) {
	        	itemSelect.append('<option value="' + resp.id + '" data-unitPrice="' + resp.unitPrice + '">' + resp.name + '</option>');
	        	
	        });
		  },
		  error: function(xhr, status, error) {
		    console.error("Error:", status, error);
		  }
		});
	});


	$(document).off('change', '#item').on('change', '#item', function () {
		var unitPrice = $(this).find(':selected').data('unitprice');
		$('#unitPrice').val(unitPrice);
	});

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $('#addItemBtn').on('click', function () {
    	  const itemVal = $('#item option:selected').val();
    	  const itemText = $('#item option:selected').text();
    	  const quantity = parseInt($('#quantity').val());
    	  const unitPrice = parseFloat($('#unitPrice').val());
		  var orderTotal = parseFloat($('#orderTotal').text());
    	  const total = quantity * unitPrice;
    	  orderTotal += total;
		  var trRow = "<tr>" +
		  			  "<td class='d-none item-id'>"+itemVal+"</td>" +
		  			"<td class='itemName'>"+itemText+"</td>" +
		  			"<td class='quantity'>"+quantity+"</td>" +
		  			"<td class='unitPrice'>"+unitPrice+"</td>" +
		  			"<td class='totalItemCost'>"+total+"</td>" +
		  			"<td><button class='btn btn-sm btn-danger removeRow'>X</button></td>" +
		  			"</tr>"; 
    	  
    	  $('#orderTable tbody').append(trRow);
    	  $('#orderTotal').text(orderTotal);
    	  $('#item option').first().prop('selected', true);
    	  $('#quantity').val('');
    	  $('#unitPrice').val('');
    	});

    	$(document).on('click', '.removeRow', function () {
    	  const rowTotal = parseFloat($(this).closest('tr').find('td.totalItemCost').text());
    	  var orderTotal = parseFloat($('#orderTotal').text());
    	  orderTotal -= rowTotal;
    	  $('#orderTotal').text(orderTotal);
    	  $(this).closest('tr').remove();
    	});

    $(document).off("click", "#placeOrder");

	$(document).on("click", "#placeOrder", function (event) {
	    const supplier = $("#supplierName").val().trim();
	    const poDate = $("#poDate").val().trim();
	    var itemDetails = [];
	    $("#orderTable tbody tr").each(function() {
	    	itemDetails.push({
	            itemId: $(this).find('td.item-id').text().trim(),
	            itemName: $(this).find('td.itemName').text().trim(),
	            quantity: $(this).find('td.quantity').text().trim(),
	            unitPrice: $(this).find('td.unitPrice').text().trim()
	        });
	    });
	    // Basic validation
	    if (!supplier || !poDate) {
	        alert("Supplier name and PO Date are required.");
	        return;
	    }
		console.log(JSON.stringify({
        	supplier: supplier,
        	poDate: poDate,
        	itemDetails: itemDetails
        }));
	    $.ajax({
	        url: "/backlogtracker/supplier/placeOrder",
	        type: "POST",
	        contentType: "application/json", 
	        data: JSON.stringify({
	        	supplier: supplier,
	        	poDate: poDate,
	        	itemDetails: itemDetails
	        }),
	        beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
	        success: function (data) {
	            // Close modal
	            const modal = mdb.Modal.getInstance($('#addPurchaseOrderModal'));
	            modal.hide();

	            // Reload table
	            loadPurchaseOrderTable();
	            event.stopPropagation();
	        },
	        error: function () {
	            alert("Failed to add item.");
	        }
	    });
	    event.stopPropagation();
	});

	$(document).off("click", ".viewItem");

	$(document).on('click', '.viewItem', function () {
		var poId = $(this).data('poid');
		var poName = $(this).data('poname');
		console.log(poName);
		$.ajax({
	        url: "/backlogtracker/supplier/listOrderItems",
	        type: "get",
	        data: {
	        	id: poId
	        },
	        beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
	        success: function (response) {
	        	var tableBody = $('#itemTbl tbody');
		        tableBody.empty();
		        $.each(response, function(index, resp) {
		            var row = '<tr>' +
		            		  '<td>' + resp.itemName + '</td>' +
		                      '<td>' + resp.quantity + '</td>' +
		                      '<td>' + resp.unitPrice + '</td>' +
		                      '<td>' + resp.totalPrice + '</td>' +
		                      '</tr>';
		            tableBody.append(row);
		        });
		        $('#poHeading').html(poName);
		        new mdb.Modal($('#viewOrderItemsModal')).show();
	        },
	        error: function () {
	            alert("Failed to add item.");
	        }
	    });
	});

	$('#viewOrderItemsModal').on('hidden.mdb.modal', function() {
	    $('.modal-backdrop').val('');
	    
	});
	

</script>