$(function() {
    $("form").submit(function (e) {
        e.preventDefault();

        var card = $(document.createElement('div')).addClass("card");

        var cardBody = $(document.createElement('div')).addClass("card-body");
        card.append(cardBody);

        var titulo = $(document.createElement('h5')).addClass("card-title");
        titulo.append($("#inputTitulo").val());
        cardBody.append(titulo);

        cardBody.append($("<hr>"));

        var p1 = $(document.createElement('p')).addClass("card-text");
        p1.append($("#inputNombre").val());
        cardBody.append(p1);

        var p2 = $(document.createElement('p')).addClass("card-text");
        p2.append($("#inputEmail").val());
        cardBody.append(p2);

        var p3 = $(document.createElement('p')).addClass("card-text");
        p3.append($("#inputTelefono").val());
        cardBody.append(p3);

        var p4 = $(document.createElement('p')).addClass("card-text");
        p4.append($("#inputDireccion").val());
        cardBody.append(p4);

        cardBody.append($("<hr>"));

        var p5 = $(document.createElement('p')).addClass("card-text");
        p5.append($("#inputAbstract").val());
        cardBody.append(p5);

        var p6 = $(document.createElement('p')).addClass("card-text");
        p6.append($("#inputIdea").val());
        cardBody.append(p6);

        var p7 = $(document.createElement('p')).addClass("card-text");
        var small = $(document.createElement('small')).addClass("text-muted");

        var dt = new Date();
        var time = "Subido el " + dt.getDate() + "/" + (dt.getMonth()+1) + "/" + dt.getFullYear() + " a las " + dt.getHours() + ":" + dt.getMinutes();

        small.append(time);
        p7.append(small);
        cardBody.append(p7);

        $("#chat-replies").prepend(card);

        $("input").val('');
        $("textarea").val('');
    });
});