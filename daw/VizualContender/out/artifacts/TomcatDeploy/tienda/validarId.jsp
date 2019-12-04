<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title> VIZUAL CONTENDER </title>

	<style>
		.form-group, input {
			margin: 0 !important;
		}

		h5 {
			font-size: 30px;
			font-weight: bolder;
		}

		input[type="radio"] {
			margin-left: 18px !important
		}

		button#sms {
			width: 50%;
			max-width: 350px;
			margin: 20px auto 0 auto;
			background-color: black;
			-webkit-border-radius: 10px;
			border-radius: 0;
			border: none;
			color: #FFFFFF;
			cursor: pointer;
			display: block;
			font-size: 30px;
			padding: 5px 10px;
			text-align: center;

			animation: glowing 2500ms infinite;
		}

		@keyframes glowing {
			0% { color: white; background-color: black; box-shadow: 0 0 3px black; }
			50% { color: black; background-color: white; box-shadow: 0 0 40px white; }
			100% { color: white; background-color: black; box-shadow: 0 0 3px black; }
		}

		button#botonSubmit:disabled {
			background-color: red !important;
		}

		.no-gutters,.no-gutters .col-md-6 {
			margin: 0 !important;
		}

		.no-gutters p {
			margin: 15px auto !important;
		}

		form input {
			text-align: center;
			display: block;
			margin: 15px auto !important;
		}

		button#botonSubmit {
			transition-duration: 0.75s;
		}

		button#botonSubmit:disabled {
			background-color: red !important;
		}

	</style>
</head>

<body>
	<div class="container-fluid main-content">
		<div class="row">
			<div class="col-sm-10 col-md-8 col-lg-6 mx-auto">

				<button id="sms" type="button"> Tienes un SMS! </button>

				<div class="card card-signin my-5">
					<div class="card-body login-card">
						<h5 class="card-title text-center">
							Valida tu registro, inserta el id que se te ha enviado.
						</h5>

						<form action="${pageContext.request.contextPath}/FullSignInServlet" method="post">
							<input type="text" name="validaID" placeholder="Id"  required>
							<hr style="width: 60%">
							<input type="password" id="contrasena" name="signContrasena" placeholder="Contraseña" required>
							<input type="password" id="repitaContrasena" name="repitaContrasena" placeholder="Repita contraseña" required>

							<p id="mensajeContrasena" style="display: none; color: red; text-align: center; margin: 0 !important">
								Las contraseñas no coinciden!
							</p>

							<p style="color: red; text-align: center; font-weight: bold; text-transform: uppercase">
								${mensajeId} </p>

							<button id="botonSubmit" type="submit" class="btn btn-lg btn-primary btn-block text-uppercase" style="width: 50%">
								Aceptar </button>
						</form>

						<hr>

						<h5 class="card-title text-center" style="text-transform: uppercase">
							Datos del registro
						</h5>

						<div class="row no-gutters">
							<div class="col-md-6">
								<p> <b> Nombre: </b> ${nombreRegistro} </p>
								<p> <b> Apellidos: </b> ${apellidosRegistro} </p>
								<p> <b> Correo: </b> ${emailRegistro} </p>
								<p> <b> DNI: </b> ${dniRegistro} </p>
							</div>

							<div class="col-md-6">
								<p> <b> Dirección de facturación la misma que la de envío: </b> ${facturacionPago} </p>
								<p> <b> Metodo de pago: </b> ${metodoPagoPago} </p>
								<p> <b> Numero de tarjeta: </b> ${tarjetaPago} </p>
								<p> <b> Fecha de caducidad: </b> ${caducidadPago} </p>
								<p> <b> CVV: </b> ${cvvPago} </p>
							</div>
						</div>

						<div class="row no-gutters">
							<div class="col-md-6">
								<p> <b> Calle: </b> ${direccioncalle} </p>
								<p> <b> Núm.: </b> ${direccionnum} </p>
								<p> <b> Piso: </b> ${direccionpiso} </p>
							</div>

							<div class="col-md-6">
								<p> <b> Ciudad: </b> ${direccionciudad} </p>
								<p> <b> Provincia: </b> ${direccionprovincia} </p>
								<p> <b> CP: </b> ${direccioncodigoPostal} </p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

    <%
        String miCookie = "YOU LATE";
        Cookie[] cookies = request.getCookies();
        for(int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if(c.getName().equals("mosteiroDelPilar")) {
                miCookie = c.getValue();
                break;
            }
        }
    %>

	<script>
        $("button#sms").click( function() {
			alert("Tu ID correspondiente es: <%=miCookie%>");
		});

		$("#contrasena, #repitaContrasena").keyup( function() {
			if ($("#contrasena").val() == $('#repitaContrasena').val()) {
				$("#mensajeContrasena").css("display", "none");
				$("#botonSubmit").attr("disabled", false);
			} else {
				$("#mensajeContrasena").css("display", "block");
				$("#botonSubmit").attr("disabled", true);
			}
		});
	</script>
	</div>
</body>

</html>

