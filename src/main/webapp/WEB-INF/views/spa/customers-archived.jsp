<div class="container-fluid py-3" id="itemList">

    <!-- Title -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0"><i class="fas fa-list me-1"></i>Archived Customers</h4>
    </div>

    <!-- Card -->
    <div class="card shadow-0 border" style="height: 100vh;">
        <div class="card-body p-0">
            <!-- Table -->
            <table class="table align-middle mb-0" id="customertbl">
                <thead class="table-light">
                    <tr>
                        <th>Name</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Address</th>
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
  
<script type="text/javascript">

	function loadArchivedCustomersTable() {

		$.ajax({
			  url: "/backlogtracker/customer/findAllInActive", 
			  type: "GET",
			  success: function(response) {
			    console.log("Success:", response);
			    var tableBody = $('#customertbl tbody');
		        tableBody.empty();
		        $.each(response, function(index, resp) {
		            var row = '<tr>' +
				              '<td>' + resp.name + '</td>' +
		                      '<td>' + resp.phone + '</td>' +
		                      '<td>' + resp.email + '</td>' +
		                      '<td>' + resp.address + '</td>' +
		                      '<td><button class="btn btn-sm btn-outline-primary unArchive" data-customerid="'+resp.id+'">Un Archive</button></td>'+
		                      '</tr>';
		            tableBody.append(row);
		        });
			  },
			  error: function(xhr, status, error) {
			    console.error("Error:", status, error);
			  }
			});
	}

	loadArchivedCustomersTable();

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

	$(document).on("click", ".unArchive", function () {
		$.ajax({
	        url: "/backlogtracker/customer/changeState",
	        type: "POST",
	        data: {
	        	id: $(this).data('customerid'),
	        	state: true 
	        },
	        beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
	        success: function (data) {
	        	loadArchivedCustomersTable();
	        },
	        error: function () {
	            alert("Failed to archive item.");
	        }
	    });
		event.stopPropagation();
	});

</script>