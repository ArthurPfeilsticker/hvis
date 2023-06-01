window.addEventListener("load", (event) => {
  loadDatabase();
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
});

async function send_information(product) {
  let productFormated = { tipo: "produto", data: product };

  localStorage.setItem("productToSend", JSON.stringify(productFormated));
}

async function loadDatabase(category) {
  let database = [];
  let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:4568/produto`, true);
    xhr.withCredentials = true;
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.setRequestHeader("Authorization", "XX");

    xhr.onload = () => {
        console.log(xhr.responseText);
        let response = JSON.parse(xhr.responseText);
        if(response['products'].length >= 1){
          database = response['products'];
        }
        $('.products-results').empty();

  console.log(database.length);

  database = database.filter(e => e.category == category);

  for (let i = 0; i < database.length; i++) {
    let product = database[i];
    let productCard = `
    <div class="col-12 col-sm-12 col-md-4 col-lg-3 text-center">
      <div class="card_product d-flex">
        <div class="imgBx">
          <img src="${product.imagem}">
        </div>
        <div class="content d-flex">
          <div class="details">
            <h2>${product.nome}<br></h2>
            <div class="data d-flex">
              <h3>${product.preco}<br><span>Preço</span></h3>
            </div>
            <div class="actionBtn d-flex">
              <a type="button" class="btn btn-primary" target="_blank" href="${product.descricao}" onclick="send_information()" prodId="${i}" id="prodButton${i}">Comprar!</a>
            </div>
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
        alert('Sem produtos cadastrados dessa categoria');
    }

    xhr.send();

  
}

function logout(){
  localStorage.removeItem("user");
  location.href = "../..";
}