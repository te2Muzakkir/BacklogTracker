<div class="container-fluid py-3" id="itemList">

    <!-- Title -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0"><i class="fas fa-list me-1"></i> Suppliers</h4>
        <button type="button" class="btn btn-primary btn-sm" data-mdb-toggle="modal"
  			data-mdb-target="#addSupplierModal">Add Supplier</button>
    </div>

    <!-- Card -->
    <div class="card shadow-0 border" style="height: 100vh;">
        <div class="card-body p-0">

            <!-- Table -->
            <table class="table align-middle mb-0" id="suppliertbl">
                <thead class="table-light">
                    <tr>
                        <th>Name</th>
                        <th>Phone Number</th>
                        <th>Email</th>
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

<div class="modal fade" id="addSupplierModal" tabindex="-1" aria-labelledby="addSupplierModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Add Supplier</h5>
      </div>
      <div class="modal-body">
			<div class="mb-3">
                <label class="form-label">Name</label>
                <input type="text" id="supplierName" class="form-control">
            </div>
			<div class="mb-3">
                <label class="form-label">Phone Number</label>
                <input type="text" id="phone" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="text" id="email" class="form-control">
            </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-mdb-ripple-init
          data-mdb-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="saveBtn" data-mdb-ripple-init>Save changes</button>
      </div>
    </div>
  </div>
</div>
  
<script type="text/javascript">

	function loadSuppliersTable() {

		if ($('#addSupplierModal').length) {
		    new mdb.Modal(document.getElementById('addSupplierModal'));
		}
		
		$.ajax({
			  url: "/backlogtracker/supplier/findAllActive", 
			  type: "GET",
			  success: function(response) {
			    console.log("Success:", response);
			    var tableBody = $('#suppliertbl tbody');
		        tableBody.empty();
		        $.each(response, function(index, resp) {
		            var row = '<tr>' +
		            		  '<td>' + resp.name + '</td>' +
		                      '<td>' + resp.phone + '</td>' +
		                      '<td>' + resp.email + '</td>' +
		                      '<td><button class="btn btn-sm btn-outline-danger archivebtn" data-supplierid="'+resp.id+'">Archive</button>&nbsp;&nbsp;'+
		                      '<button class="btn btn-sm btn-outline-primary editbtn disabled" data-supplierid="'+resp.id+'">Edit</button></td>'+
		                      '</tr>';
		            tableBody.append(row);
		        });
			  },
			  error: function(xhr, status, error) {
			    console.error("Error:", status, error);
			  }
			});
	}

	loadSuppliersTable();

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).off("click", "#saveBtn");

	$(document).on("click", "#saveBtn", function (event) {
	    const supplierName = $("#supplierName").val().trim();
	    const phone = $("#phone").val().trim();
	    const email = $("#email").val().trim();
	    // Basic validation
	    if (!supplierName || !phone) {
	        alert("Supplier name and phone are required.");
	        return;
	    }

	    $.ajax({
	        url: "/backlogtracker/supplier/add",
	        type: "POST",
	        data: {
	        	name: supplierName,
	        	contact: phone,
	        	phone: phone,
	        	email: email
	        },
	        beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
	        success: function (data) {
	            // Close modal
	            const modal = mdb.Modal.getInstance($('#addSupplierModal'));
	            modal.hide();

	            // Reload table
	            loadSuppliersTable();
	            event.stopPropagation();
	        },
	        error: function () {
	            alert("Failed to add item.");
	        }
	    });
	    event.stopPropagation();
	});

	$(document).on("click", ".archivebtn", function (event) {
		$.ajax({
	        url: "/backlogtracker/supplier/changeState",
	        type: "POST",
	        data: {
	        	id: $(this).data('supplierid'),
	        	state: false 
	        },
	        beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
	        success: function (data) {
	        	loadSuppliersTable();
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