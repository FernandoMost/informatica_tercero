<%_____ uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="_____"%>
<html>
	<%_____ file="cabecera.jsp"%>
	<body>
		<div id="contenido">
		<a id="logo" href="http:www.usc.es"></a>
		<div id="submenu2">
			<p class="titulo">Registro de Usuarios de DAW</p>
			<div class="textogrande">
				<table>
					<caption>_____</caption>
					<tr>
						<th>_____</th>
						<th>_____</th>
					</tr>
					
					<c:_____ var="lista" items="${_____}">
						<tr>
							<td>${_____}</td>
							<td>${_____}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<p id="pie">webmaster email: ${_____}</p>
		</div>
		</div>
		