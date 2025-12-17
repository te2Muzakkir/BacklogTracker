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
					<c:if test="${param.error != null}"><p class="errMsg">Invalid username or password</p></c:if>
  					<c:if test="${param.logout != null}"><p class="loutMsg">You are successfully logged out</p></c:if>
  					<c:if test="${param.timeout != null}"><p class="loutMsg">Your session has expired.</p></c:if>
					<ul class="nav nav-tabs mb-3" id="header" role="tablist">
						<li class="nav-item" role="presentation"><a
							class="nav-link active" id="login" data-mdb-toggle="tab"
							href="#login-toggle" role="tab" aria-controls="login-toggle"
							aria-selected="true"><i class="fas fa-user-lock fa-fw me-2"></i>Login</a></li>
						<li class="nav-item" role="presentation"><a class="nav-link"
							id="registration" data-mdb-toggle="tab"
							href="#registration-toggle" role="tab"
							aria-controls="registration-toggle" aria-selected="false"><i
								class="fas fa-user-pen fa-fw me-2"></i>Register</a></li>
					</ul>
				</div>
				<div class="tab-content" id="ex-with-icons-content">
					<div class="tab-pane fade show active" id="login-toggle"
						role="tabpanel" aria-labelledby="login">
						<div class="card-body">
							<div class="tab-pane fade show active" id="login" role="tabpanel"
								aria-labelledby="login">
								<form action="<c:url value='/login'/>" method="post">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="form-outline mb-4">
										<input type="email" id="username" name="username" class="form-control form-control-lg" autocomplete="off" autofocus="autofocus"/> 
										<label class="form-label" for="email">Email Address</label>
									</div>
									<div class="form-outline mb-4">
										<input type="password" id="password" name="password" class="form-control form-control-lg" 
										autocomplete="new-password" data-lpignore="true"/>
										<label class="form-label" for="password">Password</label>
									</div>
									<button type="submit" class="btn btn-primary btn-block">Log
										in</button>
								</form>
							</div>
						</div>
						<div class="card-footer text-muted">
							<a href="<c:url value='/forgotPassword' />">Forgot Password?</a>
						</div>
					</div>
					<div class="tab-pane fade" id="registration-toggle" role="tabpanel"
						aria-labelledby="registration">
						<div class="card-body">
							<div class="tab-pane fade show active" id="login" role="tabpanel"
								aria-labelledby="login">
								<form action="<c:url value='/register'/>" method="post">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="form-outline mb-4">
										<input type="text" id="username" name="username" class="form-control form-control-lg" autocomplete="off" autofocus="autofocus"/> 
										<label class="form-label" for="username">User Name</label>
									</div>
									<div class="form-outline mb-4">
										<input type="email" id="email" name="email" class="form-control form-control-lg" autocomplete="off"/> 
										<label class="form-label" for="email">Email Address</label>
									</div>
									<div class="form-outline mb-4">
										<input type="password" id="password" name="password" class="form-control form-control-lg" 
										autocomplete="new-password" data-lpignore="true"/>
										<label class="form-label" for="password">Password</label>
									</div>
									<button type="submit" class="btn btn-primary btn-block">Register</button>
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