<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no" />
		<meta http-equiv="x-ua-compatible" content="ie=edge" />
		<link rel="icon" type="image/png" href="resources/images/title.png">
		<title>Backlog Tracker</title>
		<link rel="icon" href="" type="image/x-icon" />
		<link rel="stylesheet" href="resources/css/mdb.min.css" />
		<link rel="stylesheet"
			href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
		<link rel="stylesheet" href="resources/css/roboto.css" />
		<link rel="stylesheet" href="resources/css/backlogtracker.css" />
		<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/dataTables.bootstrap5.min.css">
		<style type="text/css">
			.form-input {
			  display: block;
			  width: 100%;
			  padding: 8px 10px;
			  border: 1px solid #ced4da;
			  border-radius: 4px;
			  box-sizing: border-box;
			}
			
			.form-input:focus {
			  outline: none;
			  border-color: #3b71ca;
			}
			
			.flatpickr-calendar {
			    font-family: "Roboto", sans-serif;
			    border-radius: .375rem;
			}
		</style>
		<script type="text/javascript" src="resources/js/jquery-3.7.1.min.js"></script>
		<script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>
	</head>
	<body>
		<header>
		    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm px-3">
			    <!-- Logo + App Name -->
			    <a class="navbar-brand d-flex align-items-center" href="<c:url value='/home'/>">
			        <img src="<c:url value='/resources/images/title.png'/>" alt="Logo" height="32" class="me-2">
			        <span class="fw-bold">Backlog Tracker</span>
			    </a>
			
			    <!-- Toggler -->
			    <button class="navbar-toggler" type="button" data-mdb-toggle="collapse"
			            data-mdb-target="#navbarMain" aria-controls="navbarMain"
			            aria-expanded="false" aria-label="Toggle navigation">
			        <i class="fas fa-bars"></i>
			    </button>
			
			    <!-- Collapsible Content -->
			    <div class="collapse navbar-collapse" id="navbarMain">
			
			        <!-- Left menu -->
			        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
			
			            <!-- Items Dropdown -->
			            <li class="nav-item dropdown">
			                <a class="nav-link dropdown-toggle" href="#" id="itemsDropdown"
			                   role="button" data-mdb-toggle="dropdown" aria-expanded="false">
			                    Items
			                </a>
			                <ul class="dropdown-menu" aria-labelledby="itemsDropdown">
			                    <li><a class="dropdown-item" href="#" data-view="item-list">All Items</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li><a class="dropdown-item" href="#" data-view="items-archived">Archived Items</a></li>
			                </ul>
			            </li>
			            <!-- Suppliers Dropdown -->
			            <li class="nav-item dropdown">
			                <a class="nav-link dropdown-toggle" href="#" id="itemsDropdown"
			                   role="button" data-mdb-toggle="dropdown" aria-expanded="false">
			                    Suppliers
			                </a>
			                <ul class="dropdown-menu" aria-labelledby="itemsDropdown">
			                    <li><a class="dropdown-item" href="#" data-view="suppliers-list">All Suppliers</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li><a class="dropdown-item" href="#" data-view="suppliers-archived" >Archived Suppliers</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li><a class="dropdown-item" href="#" data-view="suppliers-order-list">View Order</a></li>
			                </ul>
			            </li>
			            <!-- Customers Dropdown -->
			            <li class="nav-item dropdown">
			                <a class="nav-link dropdown-toggle" href="#" id="itemsDropdown"
			                   role="button" data-mdb-toggle="dropdown" aria-expanded="false">
			                    Customers
			                </a>
			                <ul class="dropdown-menu" aria-labelledby="itemsDropdown">
			                    <li><a class="dropdown-item" href="#" data-view="customers-list">All Customers</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li><a class="dropdown-item" href="#" data-view="customers-archived">Archived Customers</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li><a class="dropdown-item" href="#" data-view="customers-order-list">View Order</a></li>
			                </ul>
			            </li>
			            <li class="nav-item dropdown">
			                <a class="nav-link dropdown-toggle" href="#" id="itemsDropdown"
			                   role="button" data-mdb-toggle="dropdown" aria-expanded="false">
			                    Reports
			                </a>
			                <ul class="dropdown-menu" aria-labelledby="itemsDropdown">
			                    <li><a class="dropdown-item disabled" href="#" data-view="customers-list">Customers Order Report</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li><a class="dropdown-item disabled" href="#" data-view="customers-archived">Suppliers Order Report</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li><a class="dropdown-item disabled" href="#" data-view="customers-place-order">Inventory Report</a></li>
			                </ul>
			            </li>
			            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
							<li class="nav-item dropdown">
				                <a class="nav-link dropdown-toggle" href="#" id="itemsDropdown"
				                   role="button" data-mdb-toggle="dropdown" aria-expanded="false">
				                    Admin
				                </a>
				                <ul class="dropdown-menu" aria-labelledby="itemsDropdown">
				                	<li><a class="dropdown-item" href="#" data-view="approve-orders">Approve Orders</a></li>
				                    <li><hr class="dropdown-divider"></li>
				                    <li><a class="dropdown-item disabled" href="#" data-view="role-List">Role</a></li>
				                    <li><hr class="dropdown-divider"></li>
				                    <li><a class="dropdown-item disabled" href="#" data-view="admin-panel">Admin Panel</a></li>
				                </ul>
				            </li>
					    </c:if>
			        </ul>
			
			        <!-- Right section -->
			        <ul class="navbar-nav ms-auto">
			
			            <!-- User dropdown -->
			            <li class="nav-item dropdown">
			                <a class="nav-link dropdown-toggle fw-semibold" href="#" id="userDropdown"
			                   role="button" data-mdb-toggle="dropdown" aria-expanded="false">
			                    ${username}
			                </a>
			                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
			                    <li><a class="dropdown-item disabled" href="<c:url value='/profile'/>">Profile</a></li>
			                    <li><hr class="dropdown-divider"></li>
			                    <li>
			                    	<form action="<c:url value='/logout'/>" method="post" id="logoutForm">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									</form>
			                    	<a href="#" class="dropdown-item text-danger" id="logoutLink">Logout</a>
			                    </li>
			                </ul>
			            </li>
			
			        </ul>
			    </div>
			</nav>
		</header>
		<!--Main Navigation-->
		<main id="maincontent">
			<jsp:include page="./spa/dashboard.jsp"></jsp:include>
		</main>
		<footer>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.11.6/umd/popper.min.js"></script>
			<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
			<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
			<script src="https://cdn.datatables.net/1.13.8/js/dataTables.bootstrap5.min.js"></script>
			<script type="text/javascript" src="resources/js/mdb.min.js"></script>
			<script type="text/javascript">
		
				$("#logoutLink").on("click", function(e) {
				    e.preventDefault();
				    $("#logoutForm").submit();
				});

				setInterval(function() {
				    fetch('/backlogtracker/ping', { method: 'GET' })
				        .then(response => {
				            if (response.status === 401) {
				                window.location = '/backlogtracker/login?timeout';
				            }
				        });
				}, 660000);

				$(document).on("click", ".dropdown-item", function (e) {
				    e.preventDefault();
				    const view = $(this).data('view');
				    $("#maincontent").html('');
				    $("#maincontent").load("/backlogtracker/view?viewName=" + view, function (response, status) {
				        if (status === "error") {
				            $("#maincontent").html("<p>Error loading page</p>");
				            return;
				        }
				        // session timeout detection
				        if (response.includes("login-toggle")) {
				            window.location = "/backlogtracker/login";
				        }
				    });
				});

				$(document).off("click", ".downloadInvoice").on('click', '.downloadInvoice', function () {
					
				});
			</script>
		</footer>
	</body>
</html>