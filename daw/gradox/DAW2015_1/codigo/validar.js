function validar(elem, msg){
	var reExp= /^[\.\-]{2}DAW[^a-z1-9\s]{2,4}$/;
	if(elem.match(reExp))
		_____
	else{
		alert(msg);
		_____ false;
		
	}
}

function validarAlta() {
	var nombre=document.getElementById('user').value;
	return validar(nombre, "Introduzca un nombre de usuario correcto");
}