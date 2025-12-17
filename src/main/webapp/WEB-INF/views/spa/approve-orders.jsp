<div class="container-fluid py-3" id="itemList">
	<meta name="_csrf" content="${_csrf.token}" />
	<meta name="_csrf_header" content="${_csrf.headerName}" />

    <!-- Title -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0"><i class="fas fa-list me-1"></i> Customer Orders</h4>
    </div>

    <!-- Card -->
    <div class="card shadow-0 border" style="height: 50vh;">
        <div class="card-body p-0">

            <!-- Table -->
            <table class="table align-middle mb-0" id="customerOrdertbl">
                <thead class="table-light">
                    <tr>
                    	<th>Customer Order Number</th>
                        <th>Customer</th>
                        <th>Order Date</th>
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
    
    <br>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0"><i class="fas fa-list me-1"></i> Purchase Orders</h4>
    </div>

    <!-- Card -->
    <div class="card shadow-0 border" style="height: 50vh;">
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
</div>

<div class="modal fade bd-example-modal-lg" id="viewOrderItemsModal" tabindex="-1" aria-labelledby="viewOrderItemsModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Order : <span id="oHeading"></span></h5>
      </div>
      <div class="modal-body">
      		<table class="table table-borderless align-middle">
	          <tr class="row">
	            <td class="fw-semibold col-2">Name : </td>
	            <td class="col-4">
	              <input type="text" id="nametxt" name="nametxt"  class="form-input" readonly="readonly">
	            </td>
	
	            <td class="fw-semibold col-2">Order Date : </td>
	            <td class="col-4">
	              <input type="text" id="odatetxt" name="odatetxt"  class="form-input" readonly="readonly">
	            </td>
	          </tr>
	
	          <tr class="row">
	            <td class="fw-semibold col-2">Order Total: </td>
	            <td  class="col-4">
	              <input type="text" id="totalAmounttxt" name="totalAmounttxt"  class="form-input" readonly="readonly">
	            </td>
	            <td class="fw-bold col-4"></td>
	            <td  class="col-8">
	            	<input type="hidden" id="oId" name="oId"  class="form-input" readonly="readonly">
	            </td>
	          </tr>
	        </table>
      	   <h6 class="fw-bold mb-3">Customer Order Items</h6>
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
        <button type="button" class="btn btn-primary" id="approvebtn" data-mdb-ripple-init>Approve</button>
        <button type="button" class="btn btn-danger" id="rejectbtn" data-mdb-ripple-init>Reject</button>
        <button type="button" class="btn btn-secondary" data-mdb-ripple-init data-mdb-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Toast container -->
<div aria-live="polite" aria-atomic="true" class="position-relative">
  <div id="successToast" class="toast toast-primary position-fixed top-0 end-0 m-3" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="toast-header  toast-primary">
      <strong class="me-auto">Success</strong>
      <button type="button" class="btn-close" data-mdb-dismiss="toast" aria-label="Close"></button>
    </div>
    <div class="toast-body  toast-primary">
    Success!!
    </div>
  </div>
</div>

<script type="text/javascript">

function loadOrders() {

	$.ajax({
		  url: "/backlogtracker/customer/getOrdersPendingApproval", 
		  type: "GET",
		  success: function(response) {
		    var tableBody = $('#customerOrdertbl tbody');
	        tableBody.empty();
	        $.each(response, function(index, resp) {
	        	var row = '<tr>' +
	      		    '<td> CO - ' + resp.id + ' </td>' +
	      		    '<td class="name">' + resp.customerName + '</td>' +
	                '<td class="odate">' + resp.coDate + '</td>' +
	                '<td class="totalAmount">' + resp.totalAmount + '</td>' +
	                '<td>' + resp.status + '</td>' +
	                '<td>' + resp.totalItems + '</td>' +
	                '<td><button class="btn btn-sm btn-outline-primary viewItem" data-oid="'+resp.id+'" data-oname="CO - '+resp.id+'">View</button>&nbsp;&nbsp;'+
	                '<button class="btn btn-sm btn-outline-success downloadInvoice" data-oid="'+resp.id+'" data-oname="CO - '+resp.id+'">Download Invoice</button></td>'+
	                '</tr>';
	            tableBody.append(row);
	        });
	        loadPurchaseOrders();
		  },
		  error: function(xhr, status, error) {
		    console.error("Error:", status, error);
		  }
		});
	}
	
	loadOrders();

	function loadPurchaseOrders() {
		$.ajax({
		  url: "/backlogtracker/supplier/getOrdersPendingApproval", 
		  type: "GET",
		  success: function(response) {
			  var tableBody = $('#purchaseOrdertbl tbody');
		        tableBody.empty();
		        $.each(response, function(index, resp) {
		            var row = '<tr>' +
		            		  '<td> PO - ' + resp.id + ' </td>' +
		            		  '<td class="name">' + resp.supplierName + '</td>' +
		                      '<td  class="odate">' + resp.poDate + '</td>' +
		                      '<td  class="totalAmount">' + resp.totalAmount + '</td>' +
		                      '<td>' + resp.status + '</td>' +
		                      '<td>' + resp.totalItems + '</td>' +
		                      '<td><button class="btn btn-sm btn-outline-primary viewItem" data-oid="'+resp.id+'" data-oname="PO - '+resp.id+'">View</button>&nbsp;&nbsp;'+
		                      '<button class="btn btn-sm btn-outline-success downloadInvoice" data-oid="'+resp.id+'" data-oname="PO - '+resp.id+'">Download Invoice</button></td>'+
		                      '</tr>';
		            tableBody.append(row);
		        });
		  },
		  error: function(xhr, status, error) {
		    console.error("Error:", status, error);
		  }
		});
	}
	$(document).off("click", ".viewItem");

	$(document).on('click', '.viewItem', function () {
		var oId = $(this).data('oid');
		var oName = $(this).data('oname');
		var name = $(this).closest('td').siblings('.name').text();
		var odate = $(this).closest('td').siblings('.odate').text();
		var totalAmount = $(this).closest('td').siblings('.totalAmount').text();
		console.log(oName);
		var url = '/backlogtracker/supplier/listOrderItems';
		if(oName.startsWith("CO"))
			url = '/backlogtracker/customer/listOrderItems';
		$.ajax({
	        url: url,
	        type: "get",
	        data: {
	        	id: oId
	        },
	        success: function (response) {
	        	var tableBody = $('#itemTbl tbody');
		        tableBody.empty();
		        $.each(response, function(index, resp) {
		            var row = '<tr>' +
		            		  '<td>' + resp.itemName + '</td>' +
		                      '<td>' + resp.unitPrice + '</td>' +
		                      '<td>' + resp.quantity + '</td>' +
		                      '<td>' + resp.totalPrice + '</td>' +
		                      '</tr>';
		            tableBody.append(row);
		        });
		        $('#oHeading').html(oName);
		        $('#nametxt').val(name);
		        $('#odatetxt').val(odate);
		        $('#totalAmounttxt').val(totalAmount);
		        $('#oId').val(oId);
		        new mdb.Modal($('#viewOrderItemsModal')).show();
	        },
	        error: function () {
	            alert("Failed to add item.");
	        }
	    });
	});

	$(document).off("click", "#approvebtn").on('click', '#approvebtn', function () {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var oName = $('#oHeading').text();
		var url = '/backlogtracker/supplier/changeOrderStatus';
		if(oName.startsWith("CO"))
			url = '/backlogtracker/customer/changeOrderStatus';
		var id = $('#oId').val();
		console.log(oName +" : "+ url);
		$.ajax({
	        url: url,
	        type: "POST",
	        data: {
	        	id: id,
	        	status: "APPROVED"
	        },
	        beforeSend: function (xhr) {
	            xhr.setRequestHeader(header, token);
	        },
	        success: function (response) {
	        	mdb.Modal.getInstance($('#viewOrderItemsModal')).hide();
	            loadOrders();
		        $('#successToast .toast-body').text(response);
			    new mdb.Toast(document.getElementById('successToast')).show();
	        },
	        error: function () {
	            alert("Failed to add item.");
	        }
	    });
	});

	$(document).off("click", "#rejectbtn").on('click', '#rejectbtn', function () {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var oName = $('#oHeading').text();
		var url = '/backlogtracker/supplier/changeOrderStatus';
		if(oName.startsWith("CO"))
			url = '/backlogtracker/customer/changeOrderStatus';
		var id = $('#oId').val();
		$.ajax({
	        url: url,
	        type: "POST",
	        data: {
	        	id: id,
	        	status: "REJECTED"
	        },
	        beforeSend: function (xhr) {
	            xhr.setRequestHeader(header, token);
	        },
	        success: function (response) {
	        	mdb.Modal.getInstance($('#viewOrderItemsModal')).hide();
	            loadOrders();
		        $('#successToast .toast-body').text(response);
			    new mdb.Toast(document.getElementById('successToast')).show();
	        },
	        error: function () {
	            alert("Failed to add item.");
	        }
	    });
	});
	
</script>