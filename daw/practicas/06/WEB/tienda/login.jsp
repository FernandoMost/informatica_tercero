<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title> VIZUAL CONTENDER </title>
</head>

<body>
	<div class="container-fluid main-content">
		<div class="row">
			<div class="col-sm-8 col-md-6 col-lg-4 mx-auto">
				<div class="card card-signin my-5">
					<div class="card-body login-card">
						<h5 class="card-title text-center" style="font-size: 30px; text-transform: uppercase; font-weight: bolder">
							Identifícate
						</h5>

						<form class="form-signin" id="LogIn" name="LogIn" action="http://localhost:8080/VizualContender/LogInServlet" method="post">
							<label for="LogInEmail"></label>
							<input type="email" id="LogInEmail" name="LogInEmail" class="form-control" placeholder="E-mail" required autofocus>

							<label for="LogInPassword"></label>
							<input type="password" id="LogInPassword" name="LogInPassword" class="form-control" placeholder="Contraseña" required>

							<button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit"> Log in </button>
						</form>

						<hr>

						<h1> ${respuesta} </h1>

						<button class="btn btn-lg btn-primary btn-block text-uppercase" onclick="openModal()"> Sign in </button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id="modalSignUp">
		<span class="close" onclick="closeModal()" >&times;</span>

		<div class="row" style="position: relative; top: 50%; transform: translateY(-50%);">
			<div class="col-sm-12 col-md-10 col-lg-8 mx-auto">
				<div class="card card-signin my-5">
					<div class="card-body">
						<h5 class="card-title text-center" style="font-size: 30px; text-transform: uppercase; font-weight: bolder">
							Registrate
						</h5>

						<form class="form-signin" name="SignIn">
							<div class="form-row">
								<div class="form-group col-md-6">
									<input type="text" class="form-control" id="inputNombre" placeholder="Campo 1" required autofocus>
								</div>

								<div class="form-group col-md-6">
									<input type="email" class="form-control" id="inputEmail" placeholder="Campo 2" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-6">
									<input type="text" class="form-control" id="inputNombre" placeholder="Campo 3" required>
								</div>

								<div class="form-group col-md-6">
									<input type="email" class="form-control" id="inputEmail" placeholder="Campo 4" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-12">
									<input type="email" id="inputEmail" class="form-control" placeholder="Campo 5" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-12">
									<input type="password" id="inputPassword" class="form-control" placeholder="Campo 6" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-12">
									<input type="password" id="inputPassword" class="form-control" placeholder="Campo 7" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-12">
									<input type="password" id="inputPassword" class="form-control" placeholder="Campo 8" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-12">
									<input type="password" id="inputPassword" class="form-control" placeholder="Campo 9" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-12">
									<input type="password" id="inputPassword" class="form-control" placeholder="Campo 10" required>
								</div>
							</div>

							<button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit"> Continuar </button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		function openModal() {
			document.getElementById("modalSignUp").style.display = "block";
		}

		function closeModal() {
			document.getElementById("modalSignUp").style.display = "none";
		}
	</script>

</body>

</html>