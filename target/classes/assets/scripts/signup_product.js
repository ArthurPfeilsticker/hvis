const product_name = document.querySelector('#campoNProduto'),
      product_desc = document.querySelector('#campoDescricao'),
      product_price = document.querySelector('#campoPreco'),
      productId_num = document.querySelector('#campoQuantidade'),
      productId_imagem = document.querySelector('#campoImagem');

let btn_submit = document.querySelector('.btn-submit');


btn_submit.onclick = () => {
    if(product_name.value === 'testeProduct'){
        alert('ERRO: este produto  já foi criado');
    }else{
        let product = {
            nome: product_name.value.trim(),
            descricao: product_desc.value.trim(),
            preco: product_price.value.trim(),
            quantidade: productId_num.value.trim(),
            imagem: productId_imagem.trim(),
        }
        postProduct(product);
    }
}

const postProduct = product => {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:4568/produto`, true);


    xhr.onload = () => {
        let response = JSON.parse(xhr.responseText).Produto[0];
        if(response.sucesso){
            alert('Novo produto criado!');
            location.href = '../../index.html';
        }
    }

    xhr.onerror = () => {
        alert('erro ao criar produto ;-;');
    }

    xhr.send(JSON.stringify(product));

}