<div class="container-fluid py-3" id="itemList">

    <!-- Title -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0"><i class="fas fa-list me-1"></i> Items</h4>
        <button type="button" class="btn btn-primary btn-sm" data-mdb-toggle="modal"
  			data-mdb-target="#addItemModal">Add Item</button>
    </div>

    <!-- Card -->
    <div class="card shadow-0 border" style="height: 100vh;">
        <div class="card-body p-0">

            <!-- Table -->
            <table class="table align-middle mb-0" id="itemTbl">
                <thead class="table-light">
                    <tr>
                        <th>Name</th>
                        <th>SKU</th>
                        <th>Category</th>
                        <th>Unit Price</th>
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

<div class="modal fade" id="addItemModal" tabindex="-1" aria-labelledby="addItemModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Add Item</h5>
      </div>
      <div class="modal-body">
			<div class="mb-3">
                <label class="form-label">Item Name</label>
                <input type="text" id="itemName" class="form-control">
            </div>
			<div class="mb-3">
                <label class="form-label">SKU</label>
                <input type="text" id="sku" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Category</label>
                <input type="text" id="category" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Unit Price</label>
                <input type="text" id="unitPrice" class="form-control">
            </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-mdb-ripple-init
          data-mdb-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="saveItemBtn" data-mdb-ripple-init>Save changes</button>
      </div>
    </div>
  </div>
</div>
  
<script type="text/javascript">

	function loadItemsTable() {

		if ($('#addItemModal').length) {
		    new mdb.Modal(document.getElementById('addItemModal'));
		}
		
		$.ajax({
			  url: "/backlogtracker/item/findAll", 
			  type: "GET",
			  success: function(response) {
			    console.log("Success:", response);
			    var tableBody = $('#itemTbl tbody');
		        tableBody.empty();
		        $.each(response, function(index, resp) {
		            var row = '<tr>' +
		            		  '<td>' + resp.name + '</td>' +
		                      '<td>' + resp.sku + '</td>' +
		                      '<td>' + resp.category + '</td>' +
		                      '<td>' + resp.unitPrice + '</td>' +
		                      '<td><button class="btn btn-sm btn-outline-danger archivebtn" data-itemid="'+resp.id+'">Archive</button>&nbsp;&nbsp;'+
		                      '<button class="btn btn-sm btn-outline-primary editbtn disabled" data-itemid="'+resp.id+'">Edit</button></td>'+
		                      '</tr>';
		            tableBody.append(row);
		        });
			  },
			  error: function(xhr, status, error) {
			    console.error("Error:", status, error);
			  }
			});
	}

	loadItemsTable();

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

	$(document).on("click", "#saveItemBtn", function () {
	    const itemName = $("#itemName").val().trim();
	    const sku = $("#sku").val().trim();
	    const category = $("#category").val().trim();
	    const unitPrice = $("#unitPrice").val().trim();
	    // Basic validation
	    if (!itemName || !unitPrice) {
	        alert("Item name and quantity are required.");
	        return;
	    }

	    $.ajax({
	        url: "/backlogtracker/item/add",
	        type: "POST",
	        data: {
	        	itemName: itemName,
	        	sku: sku,
	        	category: category,
	        	unitPrice: unitPrice
	        },
	        beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
	        success: function (data) {
	            // Close modal
	            const modal = mdb.Modal.getInstance($('#addItemModal'));
	            modal.hide();

	            // Reload table
	            loadItemsTable();
	        },
	        error: function () {
	            alert("Failed to add item.");
	        }
	    });
	});

	$(document).on("click", ".archivebtn", function (event) {
		$.ajax({
	        url: "/backlogtracker/item/changeState",
	        type: "POST",
	        data: {
	        	id: $(this).data('itemid'),
	        	state: false 
	        },
	        beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
	        success: function (data) {
	            loadItemsTable();
	        },
	        error: function () {
	            alert("Failed to archive item.");
	        }
	    });
		event.stopPropagation();
	});

	$(document).on("click", ".editbtn", function () {
	});

</script>