let user = {};

$(document).ready(async () => {

    user = JSON.parse(localStorage.getItem("user"));

    $("#greetings").html("Olá, " + user.nome);

    $("#inputName").val(user.nome);
    $("#inputCPF").val(user.cpf);
    // $("#inputCPF").removeAttr("readonly");
    $("#inputPhone").val(user.telefone);
    //$("#inputPhone").removeAttr("readonly");
    $("#inputEmail").val(user.email);
    //$("#inputEmail").removeAttr("readonly");
    $("#inputAddress").val(user.cidade + " - " + user.estado);
    // $("#inputAddress").removeAttr("readonly");
    //$("#inputAddress").val(user.estado);
    // $("#inputAddress").removeAttr("readonly");
    $("#inputBio").val(user.descricao);
    // $("#inputBio").removeAttr("readonly");
    $("#inputDefParcial").val(user.role);
    //$("#inputDefParcial").removeAttr("disabled");
})

function logout(){
    localStorage.removeItem("user");
    location.href = "../..";
}