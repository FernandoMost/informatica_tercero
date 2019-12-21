<html>
	<%@include file="cabecera.jsp"%>
	<body>
		<div id="contenido">
			<a id="logo" href="http://www.usc.es"></a>
			<div id="submenu2">
				<p class="titulo">Registro de Usuarios de DAW</p>
				<div class="textogrande">
					<%= request.getAttribute("salida")%>
					<h2>Hacer click para consultar DB</h2>
					<form action="_____" method="_____"/>
						<input type="submit" value="Consultar"/></p>
					</form>
				</div>
				<p class="pie"> <%= request.getAttribute("salidaUno")%> </p>
			</div>
		</div>
	</body>
</html>