async function checkNames(db, sobrenome) {
  let users = db.users;

  let user = users.find((obj) => {
    return obj.sobrenome == sobrenome;
  });

  return user;
}

async function updateUser(userName) {
  let db = await JSON.parse(localStorage.getItem("databaseUsers"));

  let names = $("#inputName").val().split(" ");
  let primeiroNome = names.shift();
  let sobrenome = names.join(" ");
  let user = await checkNames(db, sobrenome);
  if (!user) {
    window.alert("Não é possível salvar um usuário em branco/inexistente");
    return;
  }
  $("#inputName").prop("readonly", true);
  let address = $("#inputAddress").val();
  $("#inputAddress").prop("readonly", true);
  let cpf = $("#inputCPF").val();
  $("#inputCPF").prop("readonly", true);
  let bio = $("#inputBio").val();
  $("#inputBio").prop("readonly", true);
  let phone = $("#inputPhone").val();
  $("#inputPhone").prop("readonly", true);
  let defType = $("#inputDefParcial").val();
  $("#inputDefParcial").prop("disabled", true);

  

  user.primeiroNome = primeiroNome;
  user.sobrenome = sobrenome;
  user.endereco = address;
  user.cpf = cpf;
  user.biografia = bio;
  user.telefone = phone;
  user.tipoDeficiencia = defType;

  let indexToRemove = db["users"].findIndex((obj) => {
    return obj.sobrenome == sobrenome;
  });

  db["users"].splice(indexToRemove, 1);

  db["users"].push(user);

  localStorage.setItem("databaseUsers", JSON.stringify(db));

  window.alert(`O seu contato foi atualizado ! - ${primeiroNome}`);
}

function clearFields(){
    $('#inputName').val(' ');
    $('#inputName').removeAttr("readonly");
    $('#inputAddress').val(' ');
    $('#inputAddress').prop("readonly", true);
    $('#inputCPF').val(' ');
    $('#inputCPF').prop("readonly", true);
    $('#inputBio').val(' ');
    $('#inputBio').prop("readonly", true);
    $('#inputPhone').val(' ');
    $('#inputPhone').prop("readonly", true);
    $('#inputDefParcial').val(' ');
    $('#inputDefParcial').prop("disabled", true);
  }

async function allowEdit() {
  let names = $("#inputName").val().split(" ");
  let primeiroNome = names.shift();
  let sobrenome = names.join(" ");

  let db = await JSON.parse(localStorage.getItem("databaseUsers"));

  let user = await checkNames(db, sobrenome);

  console.log(user);

  if (!user) {
    window.alert("Usuário não encontrado. Verifique o nome corretamente");
    return;
  }

  $("#greetings").html("Olá, " + user.primeiroNome);

  $("#inputName").val(user.primeiroNome + " " + user.sobrenome);
  $("#inputAddress").val(user.endereco);
  $("#inputAddress").removeAttr("readonly");
  $("#inputCPF").val(user.cpf);
  $("#inputCPF").removeAttr("readonly");
  $("#inputBio").val(user.biografia);
  $("#inputBio").removeAttr("readonly");
  $("#inputPhone").val(user.telefone);
  $("#inputPhone").removeAttr("readonly");
  $("#inputDefParcial").val(user.tipoDeficiencia);
  $("#inputDefParcial").removeAttr("disabled");
}

$(document).ready(async () => {
    await readJSON();
})

async function readJSON(){
    let db = {
    users: [
      {
        primeiroNome: "Roberto",
        sobrenome: "da Cruz Souza",
        endereco: "Rua Rio Pomba 1568 - Padre Eustáquio Belo Horizonte MG",
        cpf: "12345678900",
        biografia:
          "Lorem Ipsum libero in convallis. Duis hendrerit nibh libero, id fermentum erat lobortis a. Curabitur sapien justo, vehicula ut varius sit amet, vulputate et tellus. Sed ut libero eu ex dignissim dapibus. Integer non congue nulla, at bibendum dui. Donec id commodo augue. Duis faucibus leo risus, ornare rutrum dui ultrices vel.",
        telefone: "31998765432",
        tipoDeficiencia: "Parcial",
      },{
        primeiroNome: "Bernardo",
        sobrenome: "Fontes Vieira",
        endereco: "Alameda do Jequitinhonha - Liberdade Rio Branco AC",
        cpf: "78956489798",
        biografia:
          "Lorem Ipsum libero in convallis. Duis hendrerit nibh libero, id fermentum erat lobortis a. Curabitur sapien justo, vehicula ut varius sit amet, vulputate et tellus. Sed ut libero eu ex dignissim dapibus. Integer non congue nulla, at bibendum dui. Donec id commodo augue. Duis faucibus leo risus, ornare rutrum dui ultrices vel.",
        telefone: "8285675234",
        tipoDeficiencia: "Completa",
      }
    ],
  };
 
     await localStorage.setItem('databaseUsers', JSON.stringify(db));
     return db;
 }