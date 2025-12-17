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
<title>Backlog Tracker - Forgot Password</title>
<link rel="icon" href="" type="image/x-icon" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
<link rel="stylesheet" href="resources/css/roboto.css" />
<link rel="stylesheet" href="resources/css/mdb.min.css" />
<link rel="stylesheet" href="resources/css/backlogtracker.css" />
</head>
<body>
	<!-- Start your project here-->
	<div class="container-fluid blogtracker">
		<div class="d-flex justify-content-center align-items-center"
			style="height: 100vh;">
			<div class="card">
				<div class="card-header">
					<ul class="nav nav-tabs mb-3" id="header" role="tablist">
						<li class="nav-item" role="presentation"><a
							class="nav-link active" id="login" data-mdb-toggle="tab"
							href="#login-toggle" role="tab" aria-controls="login-toggle"
							aria-selected="true"><i class="fas fa-user-lock fa-fw me-2"></i>Forgot Password</a></li>
					</ul>
				</div>
				<div class="tab-content" id="ex-with-icons-content">
					<div class="tab-pane fade show active" id="login-toggle"
						role="tabpanel" aria-labelledby="login">
						<div class="card-body">
							<div class="tab-pane fade show active" id="login" role="tabpanel"
								aria-labelledby="login">
								<c:if test="${not empty message}">
								    <div class="error">${message}</div>
								</c:if>
								<form action="<c:url value='sendForgotPasswordMail'/>" method="get">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="form-outline mb-4">
										<input type="email" id="email" name="email" class="form-control form-control-lg" autocomplete="off"/> 
										<label class="form-label" for="email">Email Address</label>
									</div>
									<button type="submit" class="btn btn-primary btn-block">Submit</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End your project here-->
	<script type="text/javascript" src="resources/js/mdb.min.js"></script>
	<!-- Custom scripts -->
	<script type="text/javascript"></script>
</body>
</html>