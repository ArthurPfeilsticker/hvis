

async function loadDatabase(type) {
    $('.products-results').empty();
    let database = await $.getJSON(`../../assets/database/${type}.json`, function (json) {
      return json;
    });
  
    let products = database['results'];
    for (let i = 0; i < products.length; i++) {
      let product = products[i];
      let productCard = `
      <div class="col-12 col-sm-12 col-md-4 col-lg-4 text-center">
        <div class="card_product d-flex">
          <div class="imgBx">
          <img src="${product.foto}">
          </div>
          <div class="content d-flex">
          <div class="details">
          <h2>${product.nome}<br><span>${product.tipo}</span></h2>
          <h2> <span>Cidade: </span>${product.cidade}<br></h2>
          <h2> <span>Telefone: </span>${product.telefone}</h2>
          <h2> <span>Estado: </span>${product.estado}</h2>
          <h2> <span>Descrição: </span>${product.especialidade}</h2>
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



