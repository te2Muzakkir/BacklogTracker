<div class="container-fluid py-3" id="dashboard">
	<br>
	<div class="row g-3">
	    <div class="col-md-3">
	      <div class="card shadow-0 border">
	        <div class="card-body">
	          <h6 class="text-muted mb-1">Items in Stock</h6>
	          <h3 class="mb-0">${totalInventoryCount}</h3>
	          <small class="text-muted">Inventory Movement</small>
	        </div>
	      </div>
	    </div>
	    <div class="col-md-3">
	      <div class="card shadow-0 border">
	        <div class="card-body">
	          <h6 class="text-muted mb-1">Low Stock Items</h6>
	          <h3 class="mb-0 text-warning">${lowInventoryCount}</h3>
	          <small class="text-muted">Inventory</small>
	        </div>
	      </div>
	    </div>
	    <div class="col-md-3">
	      <div class="card shadow-0 border">
	        <div class="card-body">
	          <h6 class="text-muted mb-1">Pending Customer Orders</h6>
	          <h3 class="mb-0 text-danger">${pendingOrderCount}</h3>
	          <small class="text-muted">Orders</small>
	        </div>
	      </div>
	    </div>
	    <div class="col-md-3">
	      <div class="card shadow-0 border">
	        <div class="card-body">
	          <h6 class="text-muted mb-1">Today POs</h6>
	          <h3 class="mb-0 text-primary">${poCreatedTodayCount}</h3>
	          <small class="text-muted">Purchase Orders</small>
	        </div>
	      </div>
	    </div>
	</div>
	<br>
	<div class="card shadow-0 border">
	    <div class="card-header bg-white">
	      <h6 class="mb-0 fw-bold">Recent Inventory Movements</h6>
	    </div>
	
	    <div class="card-body p-0">
	      <br>
	      <table class="table table-hover align-middle mb-0" id="inventoryMovementTbl">
	        <thead class="table-light">
	          <tr>
	            <th>Order Date</th>
	            <th>Item</th>
	            <th>Quantity</th>
	            <th>Movement Type</th>
	            <th>Reference Type</th>
	            <th>Ordered By</th>
	          </tr>
	        </thead>
	        <tbody>
	          
	        </tbody>
	      </table>
	    </div>
	</div>
</div>
<script type="text/javascript">

$(document).ready(function () {
	  $('#inventoryMovementTbl').DataTable({
	    ajax: {
	      url: '/backlogtracker/dashboard/inventory-movement', 
	      type: 'GET',
	      dataSrc: '' // IMPORTANT if backend returns List<DTO>
	    },
	    columns: [
	      { data: 'createdDate' },
	      { data: 'itemName' },
	      {
	        data: 'quantity',
	        render: function (data, type, row) {
	          return row.movementType === 'IN'
	            ? '<span class="text-success fw-semibold">+'+data+'</span>'
	            : '<span class="text-danger fw-semibold">'+data+'</span>';
	        }
	      },
	      {
	        data: 'movementType',
	        render: function (data) {
	          return data === 'IN'
	            ? '<span class="badge bg-success">IN</span>'
	            : '<span class="badge bg-danger">OUT</span>';
	        }
	      },
	      { data: 'refrence' },
	      { data: 'createdBy' }
	    ],
	    paging: true,
	    searching: true,
	    ordering: true,
	    lengthChange: false,
	    pageLength: 10
	  });
});


</script>