<div class="container-fluid py-3" id="itemList">

    <!-- Title -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="mb-0"><i class="fas fa-list me-1"></i>Archived Items</h4>
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
  
<script type="text/javascript">

	function loadItemsTable() {

		$.ajax({
			  url: "/backlogtracker/item/findAllInActive", 
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
		                      '<td><button class="btn btn-sm btn-outline-primary unArchive" data-itemid="'+resp.id+'">Un Archive</button></td>'+
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

	$(document).on("click", ".unArchive", function () {
		$.ajax({
	        url: "/backlogtracker/item/changeState",
	        type: "POST",
	        data: {
	        	id: $(this).data('itemid'),
	        	state: true 
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

</script>