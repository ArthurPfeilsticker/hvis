const username = document.getElementById('campoUsername');
const passwd = document.getElementById('campoSenha');

onload = () => {
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('tmp');
}

document.getElementById('login').addEventListener('submit', e => {e.preventDefault()});

document.getElementById('btn_submit').onclick = () => {
    if(username.checkValidity() && passwd.checkValidity()){
        postUser(username.value, passwd.value);
    }
}

const postUser = (username, password) => {

    if ( username == "admin" && password == "admin"){
        alert ("Login successfully");
        window.location.href = '../../modules/crudadmin/index.html';
        return false;
    }
    else{

        let payLoad = {"username": username, "password": password}

        let xhr = new XMLHttpRequest();
        xhr.open('POST', `http://localhost:4568/login`, true);

        xhr.withCredentials = true;
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
        xhr.setRequestHeader("Authorization", "XX");

        xhr.onload = () => {
            const xhrResponse = JSON.parse(xhr.responseText).users[0];
            
            if(xhrResponse.id === -1){
                alert('username e/ou senha inválidos');
            }else{
                const user = {
                    id: xhrResponse.id,
                    username: xhrResponse.username,
                    email: xhrResponse.email,
                    cidade: xhrResponse.cidade,
                    estado: xhrResponse.estado,
                    nome: xhrResponse.nome,
                    cpf: xhrResponse.cpf,
                    telefone: xhrResponse.telefone,
                    descricao: xhrResponse.descricao,
                    role: xhrResponse.role,
                    sexo: xhrResponse.sexo,
                    idade: parseInt(xhrResponse.idade),

                }

                localStorage.setItem('user', JSON.stringify(user));
                location.href = '../profile_view';
            }
        }

        xhr.onerror = () => {
            alert('erro ao efetuar login ;-;');
        }

        xhr.send(JSON.stringify(payLoad));
    }
}

function logout(){
    localStorage.removeItem("user");
    location.href = "/src/main/resources";
}