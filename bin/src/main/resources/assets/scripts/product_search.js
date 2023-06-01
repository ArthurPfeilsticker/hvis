async function searchProducts(query) {
  $(".products-results").empty();
  console.log("fetching data... - " + query);
  // let queryURI = encodeURIComponent(query);
  // const settings = {
  //   async: true,
  //   crossDomain: true,
  //   url: `https://serpapi.com/search.json?q=${queryURI}&tbm=shop&location=Belo%20Horizonte&hl=pt&gl=BR&api_key=d9396826823cbc643dbf729ca52cc331430f951fe9c643cee8c251f113cce2fc`,
  //   method: "GET",
  //   headers: {
  //     Accept: "*/*",
  //   },
  // };
  // let products = $.ajax(settings);
  let products;
  if (query.charAt(0) == " ") {
    query = query.substring(1);
  }
  console.log(query);

  if (query == "olhos de vidro" || query == "olho de vidro") {
    products = await $.getJSON(
      "../../assets/scripts/database.json",
      function (json) {
        return json;
      }
    );
  } else if (query == "colírio" || query == "colirio") {
    products = await $.getJSON(
      "../../assets/scripts/database2.json",
      function (json) {
        return json;
      }
    );
  } else if (query == "tapa-olho" || query == "tapa olho") {
    products = await $.getJSON(
      "../../assets/scripts/database3.json",
      function (json) {
        return json;
      }
    );
  } else {
    window.alert(
      'Por favor, por motivos de limite de requisições da API, escolha "tapa olho", "olhos de vidro" ou "colírio" como produto!'
    );
  }

  return products;
}

window.addEventListener("load", (event) => {
  loadDatabase();
});

/*async function searchProducts_fields() {
  let locality = $("#locality").val();
  let productQuery = $("#product-field").val();

  console.log(locality == "");

  console.log(locality + " " + productQuery);

  let apiResponse = await searchProducts(productQuery);
  console.log(apiResponse);
  let products = apiResponse["shopping_results"];
  for (let i = 0; i < products.length; i++) {
    let product = products[i];
    let productCard = `
    <div class="col-12 col-sm-12 col-md-4 col-lg-3 text-center">
      <div class="card_product d-flex">
        <div class="imgBx">
          <img src="${product.thumbnail}">
        </div>
        <div class="content d-flex">
          <div class="details">
            <h2>${product.title}<br><span>${product.source}</span></h2>
            <div class="data d-flex">
              <h3>${product.price}<br><span>Preço</span></h3>
            </div>
            <div class="actionBtn d-flex">
              <a type="button" class="btn btn-primary" target="_blank" href="${product.link}" onclick="send_information()" prodId="${i}" id="prodButton${i}">Comprar!</a>
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
}*/

async function send_information(product) {
  let productFormated = { tipo: "produto", data: product };

  localStorage.setItem("productToSend", JSON.stringify(productFormated));
}

async function loadDatabase() {
  let database = [];
  let xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:4568/produto`, true);
    xhr.withCredentials = true;
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.setRequestHeader("Authorization", "XX");

    xhr.onload = () => {
        let response = JSON.parse(xhr.responseText);
        if(response['products'].length >= 1){
          database = response['products'];
        }
        $('.products-results').empty();

  console.log(database.length);

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
