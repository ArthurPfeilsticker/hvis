$(document).ready(async () => {
  if(localStorage.getItem("user") != null){
    $("#btn_Entrar").html("Logout");
    $("#btn_Entrar").on( "click", function() {
      logout()
    } );
    $("#btn_Perfil").html("Meu Perfil");
    $("#btn_Perfil").on( "click", function() {
      location.href = "../profile_view/index.html"
    } );
  }else{
    $("#btn_Entrar").on( "click", function() {
      location.href = "../login/index.html"
    } );
    $("#btn_Perfil").on( "click", function() {
      location.href = "../signup-user"
    } );
  }
})

async function send_information(product) {
  let productFormated = { tipo: "produto", data: product };

  localStorage.setItem("productToSend", JSON.stringify(productFormated));
}

async function loadDatabase(role) {
  let database = [];
  let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:4568/usuario`, true);
    xhr.withCredentials = true;
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.setRequestHeader("Authorization", "XX");

    xhr.onload = () => {
        let response = JSON.parse(xhr.responseText);
        if(response['users'].length >= 1){
          database = response['users'];
        }
        $('.products-results').empty();

  console.log(database.filter(e => e.role == role));


  database = database.filter(e => e.role == role);

  for (let i = 0; i < database.length; i++) {
    let product = database[i];
    let productCard = `
      <div class="col-12 col-sm-12 col-md-4 col-lg-4 text-center">
        <div class="card_product d-flex">
          <div class="imgBx">
          <img src="../../assets/images/profile.png">
          </div>
          <div class="content d-flex">
          <div class="details">
          <h2>${product.nome} - ${product.idade} anos</h2>
          <h2> <span>Região: </span>${product.cidade} - ${product.estado}<br></h2>
          <h2> <span>Telefone: </span>${product.telefone}</h2>
          <h2> <span>e-mail: </span>${product.email}</h2>
          <h2> <span>Descrição: </span>${product.descricao}</h2>
        </div>
          </div>
        </div>
      </div>`;
    $(".products-results").append(productCard);
    $(`#prodButton${i}`).on("click", (e) => {
      let id = $(e.target).attr("prodId");
      send_information(products[id]);
    });
  }
    }

    xhr.onerror = () => {
        alert('Sem usuários cadastrados no banco');
    }

    xhr.send();

}

function logout(){
  localStorage.removeItem("user");
  location.href = "../..";
}