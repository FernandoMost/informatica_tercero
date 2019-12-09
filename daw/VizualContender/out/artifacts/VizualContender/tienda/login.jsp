<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title> VIZUAL CONTENDER </title>

	<style>
		h5 {
			font-size: 25px;
			font-weight: bolder;
		}

		input[type="radio"] {
			margin-left: 18px !important
		}

		button#botonSubmit {
			transition-duration: 2s;
		}

		button#botonSubmit:disabled {
			background-color: red !important;
		}
	</style>

    <script>
        $(function() {
            if (getCookie("mosteiroDelPilar") === "mosteiroDelPilar") {
                window.location.href = "#!/tienda";
                // /VizualContender/#!tienda
            }
        });
    </script>
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

						<form class="form-signin" id="LogIn" name="LogIn" action="${pageContext.request.contextPath}/LogInServlet" method="post">
							<label for="LogInEmail"></label>
							<input type="email" id="LogInEmail" name="LogInEmail" class="form-control" placeholder="E-mail" required autofocus>

							<label for="LogInPassword"></label>
							<input type="password" id="LogInPassword" name="LogInPassword" class="form-control" placeholder="Contraseña" required>

							<button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit"> Log in </button>
						</form>

						<p style="color: red; text-transform: uppercase; text-align: center; font-weight: bold; font-size: 14px">
							${mensajeLogin}
						</p>

						<hr>

						<p style="color: red; text-transform: uppercase; text-align: center; font-weight: bold; font-size: 14px">
							${mensajeSignin}
						</p>

						<button class="btn btn-lg btn-primary btn-block text-uppercase" onclick="openModal()"> Sign in </button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id="modalSignUp" style="z-index: 98">
		<span class="close" onclick="closeModal()" >&times;</span>

		<div class="row" style="position: relative; top: 50%; transform: translateY(-50%);">
			<div class="col-sm-10 col-md-8 col-lg-6 mx-auto">
				<div class="card card-signin my-5">
					<div class="card-body">
						<h5 class="card-title text-center" style="font-size: 30px; text-transform: uppercase; font-weight: bolder">
							Registrate
						</h5>

						<form class="form-signin" name="SignIn" action="${pageContext.request.contextPath}/SignInServlet" method="post">
							<div class="form-row">
								<div class="form-group col-md-4">
									<input type="text" class="form-control" id="SignInNombre" name="signinNombre" placeholder="Nombre" required autofocus>
								</div>

								<div class="form-group col-md-8">
									<input type="text" class="form-control" id="SignInApellidos" name="signinApellidos" placeholder="Apellidos" required>
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-7">
								<input type="email" class="form-control" id="SignInEmail" name="signinEmail" placeholder="E-mail" required>
								</div>

								<div class="form-group col-md-5">
								<input type="text" class="form-control" id="SignInNombre" name="signinDNI" placeholder="DNI" pattern="[0-9]{8}[A-Za-z]{1}" required>
								</div>
							</div>

							<hr>

							<h5 class="card-title text-center" style="text-transform: uppercase">
								Dirección de envío
							</h5>

							<div class="form-row">
								<div class="form-group col-md-12">
								<input type="text" class="form-control" id="SignInNombre" name="signinCalle" placeholder="Calle">
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-4">
								<input type="text" class="form-control" id="SignInNombre" name="signinNumero" placeholder="Número" pattern="\d*">
								</div>

								<div class="form-group col-md-4">
								<input type="text" class="form-control" id="SignInNombre" name="signinPiso" placeholder="Piso">
								</div>

								<div class="form-group col-md-4">
								<input type="text" class="form-control" id="SignInNombre" name="signinCP" placeholder="Código postal" pattern="\d{5}">
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-6">
								<input type="text" class="form-control" id="SignInNombre" name="signinCiudad" placeholder="Ciudad">
								</div>

								<div class="form-group col-md-6">
								<input type="text" class="form-control" id="SignInNombre" name="signinProvincia" placeholder="Provincia">
								</div>
							</div>

							<hr>

							<h5 class="card-title text-center" style="text-transform: uppercase">
								Método de pago
							</h5>

							<div class="form-row">
								<div class="form-group col-md-12">
								<input type="checkbox" name="facturacion" value="boolFacturacion" checked> Deseo que la dirección de facturación sea la misma que la de envío
								</div>
							</div>

							<div class="form-row">
								<div class="form-group col-md-12" style="text-align: center">
								<input type="radio" name="pago" id="pagoT" value="tarjeta" checked> Tarjeta
								<input type="radio" name="pago" id="pagoP" value="paypal"> Paypal
								<input type="radio" name="pago" id="pagoC" value="contrarrembolso"> Contrarrembolso
								</div>
							</div>

							<div class="form-row" id="infoTarjeta">
								<div class="form-group col-md-6">
								<input type="text" class="form-control" id="numTarjeta" name="numTarjeta" placeholder="Número de tarjeta" pattern="[0-9]{16}">
								</div>

								<div class="form-group col-md-3">
								<input type="text" class="form-control" id="caducidadTarjeta" name="caducidadTarjeta" placeholder="Caducidad" pattern="\d{2}/\d{2}">
								</div>

								<div class="form-group col-md-3">
								<input type="text" class="form-control" id="cvvTarjeta" name="cvvTarjeta" placeholder="CVV" pattern="\d{3}">
								</div>
							</div>

							<button id="botonSubmit" class="btn btn-lg btn-primary btn-block text-uppercase" type="submit"> Continuar </button>
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

		$("input[type='radio']").change( function() {
			$("#infoTarjeta input[type='text']").val('');

			if(document.getElementById('pagoT').checked) {
				document.getElementById("numTarjeta").disabled = false;
				document.getElementById("caducidadTarjeta").disabled = false;
				document.getElementById("cvvTarjeta").disabled = false;
			} else if(document.getElementById('pagoP').checked) {
				document.getElementById("numTarjeta").disabled = true;
				document.getElementById("caducidadTarjeta").disabled = true;
				document.getElementById("cvvTarjeta").disabled = true;
			} else if(document.getElementById('pagoC').checked) {
				document.getElementById("numTarjeta").disabled = true;
				document.getElementById("caducidadTarjeta").disabled = true;
				document.getElementById("cvvTarjeta").disabled = true;
			}
		});

	</script>

</body>

</html>