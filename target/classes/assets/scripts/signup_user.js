const back_box = document.querySelector('.back-box'),
      user_firstname = document.querySelector('#campoNome'),
      user_cpf = document.querySelector('#campoCPF'),
      user_nick = document.querySelector('#campoUsername'),
      user_email = document.querySelector('#campoEmail'),
      user_passwd = document.querySelector('#campoSenha'),
      user_confirmpasswd = document.querySelector('#campoConfirmaS'),
      user_city = document.querySelector('#campoCidade'),
      user_estate = document.querySelector('#campoEstado'),
      user_tel = document.querySelector('#campoTelefone'),
      user_desc = document.querySelector('#campoDescricao'),
      form_element = document.querySelector('#formCadastros'),
      user_role = document.querySelector('#campoRole');
      user_sexo = document.querySelector('#campoSexo');
      user_idade = document.querySelector('#campoIdade');

let btn_submit = document.querySelector('.btn-submit');
let valid_passwd = true;

onload = () => {
    sessionStorage.removeItem('user');
}

back_box.addEventListener('click', () => {
    location.href = '../product_search/index.html';
});

user_confirmpasswd.addEventListener('blur', () => {
    if (user_confirmpasswd.value != user_passwd.value) {
        user_confirmpasswd.style.backgroundColor = '#f1343499';
        valid_passwd = false;
        btn_submit.setAttribute('disabled', 'true');
    } else {
        user_confirmpasswd.style.backgroundColor = '#fff';
        valid_passwd = true;
        if (valid_passwd) {
            btn_submit.removeAttribute('disabled');
        }
    }
});

form_element.addEventListener('submit', e => {
    e.preventDefault();
});

btn_submit.onclick = () => {
    if(user_passwd.value === 'senha123'){
        alert('ERRO: CRIA UMA SENHA DECENTE SEU JUMENTO');
    }else{
        let user = {
            nome: user_firstname.value.trim(),
            username: user_nick.value.trim(),
            password: user_passwd.value.trim(),
            cpf: parseInt (user_cpf.value.trim()),
            telefone:  parseInt (user_tel.value.trim()),
            email: user_email.value.trim(),
            cidade: user_city.value.trim(),
            estado: user_estate.value.trim(),
            descricao: user_desc.value.trim(),
            role: user_role.value.trim(),
            sexo: user_sexo.value.trim(),
            idade: user_idade.value.trim(),
        }
        postUser(user);
    }
}

const postUser = user => {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:4568/usuario`, true);

    xhr.withCredentials = true;
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.setRequestHeader("Authorization", "XX");

    xhr.onload = () => {
        console.log(xhr.responseText);
        let response = JSON.parse(xhr.responseText);
        if(response['users'].length >= 1){
            alert('Nova conta criada!\nProceda com o login');
            location.href = '../product_search/index.html';
        }else{
            console.log('else');
        }
    }

    xhr.onerror = () => {
        alert('erro ao criar conta ;-;');
    }

    xhr.send(JSON.stringify(user));

}