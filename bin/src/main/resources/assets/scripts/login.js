
// // -----------------------------------------------------------------------------------------------------
// //-----------------------------------------------------------------------------------------------------
// function leDados () {
//     let strDados = localStorage.getItem('db');
//     let objDados = {};  

//     if (strDados) {
//         objDados = JSON.parse (strDados);

//     }

//     else {
//         objDados = { Cadastros: [   {Nome: "João", Sobrenome: "da Silva", CPF: "14858996256", Email: "matheueis@yahoo.com.br",Senha: "1234", Telefone: "(32) 991313865", Endereço: "Rua Maria do Carmo", Deficiência: "Tenho 30% da visão", Descrição: "Tenho somente 30% da visão, e por isso tenho bastante dificuldade de conseguir mexer em um site web por enxergar muito pouco."},
//                                     {Nome: "Joana", Sobrenome: "Camargo", CPF: "14858974596", Email: "joanacamargo@yahoo.com.br",Senha: "12345", Telefone: "(32) 991374598", Endereço: "Rua Maria do Santos", Deficiência: "Não tenho o olho direito", Descrição: "Tenho somente o olho esquedo, porém isso nao me prejudica muito pois o outro olho enxerga 100%"}]                               
//                     }
//     }

//     return objDados;
// }

// var attempt = 10; // Variable to count number of attempts.
// // Below function Executes on click of login button.
// function validate(){
// var username = document.getElementById("username").value;
// var password = document.getElementById("password").value;

// let objDados = leDados ();
// if ( username == "admin" && password == "admin"){
//     alert ("Login successfully");
//     window.location.href = '../../modules/crudadmin/index.html';

//     return false;
// }

// for (i=0; i<objDados.Cadastros.length; i++){
//     if(username == objDados.Cadastros[i].Email && password == objDados.Cadastros[i].Senha ){
//         alert ("Login successfully");
//         window.location.href = '../../modules/home-page/index.html';
//         return false;
        
//     }
    
// }

// if(username != objDados.Cadastros[i].Email || password != objDados.Cadastros[i].Senha ){
//     attempt --;// Decrementing by one.
//     alert("You have left "+attempt+" attempt;");
// // Disabling fields after 3 attempts.
// if( attempt == 0){
// document.getElementById("username").disabled = true;
// document.getElementById("password").disabled = true;
// document.getElementById("submit").disabled = true;
// return false;
// }
// }
// }
// -----------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------

const email = document.getElementById('campoEmail');
const passwd = document.getElementById('campoSenha');

onload = () => {
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('tmp');
}

document.getElementById('login').addEventListener('submit', e => {e.preventDefault()});

document.getElementById('btn_submit').onclick = () => {
    if(email.checkValidity() && passwd.checkValidity()){
        postUser(email.value, passwd.value);
    }
}

const postUser = (e, p) => {

    if ( e == "admin" && p == "admin"){
        alert ("Login successfully");
        window.location.href = '../../modules/crudadmin/index.html';
        return false;
    }
    else{

    let xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:4568/login/`, true);

    xhr.withCredentials = true;
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.setRequestHeader("Authorization", "XX");

    xhr.onload = () => {
        const xhrResponse = JSON.parse(xhr.responseText).Usuario[0];
        
        if(xhrResponse.id === -1){
            alert('Email e/ou senha inválidos');
        }else{
            const user = {
                id: xhrResponse.id,
                email: e
            }

            sessionStorage.setItem('user', JSON.stringify(user));
            location.href = '/../product_search/index.html';
        }
    }

    xhr.onerror = () => {
        alert('erro ao efetuar login ;-;');
    }

    xhr.send(p);
    }
}