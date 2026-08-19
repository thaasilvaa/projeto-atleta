const API = "http://localhost:8080/api/atletas";

const form = document.getElementById("formAtleta");
const lista = document.getElementById("lista");

let atletas = [];


// ==========================================
// CARREGAR ATLETAS
// ==========================================

async function listar() {

    try {

        const resposta = await fetch(API);

        if (!resposta.ok) {
            throw new Error("Erro ao buscar atletas.");
        }

        atletas = await resposta.json();

        atualizarDashboard(atletas);
        mostrarModalidades(atletas);
        mostrarTabela(atletas);

    } catch (erro) {

        console.error(erro);

        lista.innerHTML = `
            <tr>
                <td colspan="6" style="text-align:center;">
                    Não foi possível conectar ao servidor.
                </td>
            </tr>
        `;

    }
}


// ==========================================
// DASHBOARD
// ==========================================

function atualizarDashboard(atletas) {

    document.getElementById("totalAtletas").textContent =
        atletas.length;

    document.getElementById("totalFutebol").textContent =
        contar("Futebol");

    document.getElementById("totalBasquete").textContent =
        contar("Basquete");

    document.getElementById("totalNatacao").textContent =
        contar("Natação");

    document.getElementById("totalAtletismo").textContent =
        contar("Atletismo");

}


function contar(modalidade) {

    return atletas.filter(
        atleta => atleta.modalidade === modalidade
    ).length;

}


// ==========================================
// MODALIDADES
// ==========================================

function mostrarModalidades(atletas) {

    const modalidades = {

        Futebol: "listaFutebol",

        Basquete: "listaBasquete",

        Natação: "listaNatacao",

        Atletismo: "listaAtletismo"

    };


    Object.entries(modalidades).forEach(
        ([modalidade, elementoId]) => {

            const listaModalidade =
                document.getElementById(elementoId);

            const atletasModalidade =
                atletas.filter(
                    atleta =>
                        atleta.modalidade === modalidade
                );


            const contadorId =
                "contador" +
                modalidade
                    .normalize("NFD")
                    .replace(/[\u0300-\u036f]/g, "")
                    .replace("ã", "a");

            let contador;

            if (modalidade === "Futebol") {
                contador =
                    document.getElementById("contadorFutebol");
            }

            if (modalidade === "Basquete") {
                contador =
                    document.getElementById("contadorBasquete");
            }

            if (modalidade === "Natação") {
                contador =
                    document.getElementById("contadorNatacao");
            }

            if (modalidade === "Atletismo") {
                contador =
                    document.getElementById("contadorAtletismo");
            }


            contador.textContent =
                `${atletasModalidade.length} ${
                    atletasModalidade.length === 1
                        ? "atleta"
                        : "atletas"
                }`;


            if (atletasModalidade.length === 0) {

                listaModalidade.innerHTML = `
                    <div class="sem-atletas">
                        Nenhum atleta cadastrado nesta modalidade.
                    </div>
                `;

                return;
            }


            listaModalidade.innerHTML =
                atletasModalidade.map(
                    atleta => criarCard(atleta)
                ).join("");

        }
    );

}


// ==========================================
// CARD DO ATLETA
// ==========================================

function criarCard(atleta) {

    const inicial =
        atleta.nome
            ? atleta.nome.charAt(0).toUpperCase()
            : "?";


    return `

        <div class="atleta-card">

            <div class="avatar">
                ${inicial}
            </div>

            <div class="atleta-dados">

                <strong>
                    ${atleta.nome}
                </strong>

                <span>
                    ${atleta.idade} anos
                </span>

            </div>

            <div class="acoes-card">

                <button
                    class="btn-mini"
                    title="Editar"
                    onclick="editar(${atleta.id})"
                >
                    ✏️
                </button>

                <button
                    class="btn-mini excluir-mini"
                    title="Excluir"
                    onclick="excluir(${atleta.id})"
                >
                    🗑️
                </button>

            </div>

        </div>

    `;

}


// ==========================================
// TABELA
// ==========================================

function mostrarTabela(atletasParaMostrar) {

    lista.innerHTML = "";


    if (atletasParaMostrar.length === 0) {

        lista.innerHTML = `
            <tr>

                <td
                    colspan="6"
                    style="text-align:center; padding:35px;"
                >
                    Nenhum atleta encontrado.
                </td>

            </tr>
        `;

        return;
    }


    atletasParaMostrar.forEach(atleta => {

        lista.innerHTML += `

            <tr>

                <td>
                    #${atleta.id}
                </td>

                <td>

                    <span class="nome-tabela">
                        ${atleta.nome}
                    </span>

                </td>

                <td>

                    <span class="badge">
                        ${atleta.modalidade}
                    </span>

                </td>

                <td>
                    ${atleta.idade} anos
                </td>

                <td>
                    ${formatarSalario(atleta.salarioMensal)}
                </td>

                <td>

                    <div class="acoes">

                        <button
                            class="editar"
                            onclick="editar(${atleta.id})"
                        >
                            ✏️ Editar
                        </button>

                        <button
                            class="excluir"
                            onclick="excluir(${atleta.id})"
                        >
                            🗑️ Excluir
                        </button>

                    </div>

                </td>

            </tr>

        `;

    });

}


// ==========================================
// FORMATAR SALÁRIO
// ==========================================

function formatarSalario(valor) {

    return Number(valor).toLocaleString(
        "pt-BR",
        {
            style: "currency",
            currency: "BRL"
        }
    );

}


// ==========================================
// CADASTRAR / ATUALIZAR
// ==========================================

form.addEventListener(
    "submit",
    async function(event) {

        event.preventDefault();


        const id =
            document.getElementById("id").value;


        const atleta = {

            nome:
                document
                    .getElementById("nome")
                    .value
                    .trim(),

            modalidade:
                document
                    .getElementById("modalidade")
                    .value,

            idade:
                Number(
                    document
                        .getElementById("idade")
                        .value
                ),

            salarioMensal:
                Number(
                    document
                        .getElementById("salarioMensal")
                        .value
                )

        };


        try {

            const resposta = await fetch(

                id
                    ? `${API}/${id}`
                    : API,

                {

                    method:
                        id
                            ? "PUT"
                            : "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(atleta)

                }

            );


            if (!resposta.ok) {

                let mensagem =
                    "Erro ao salvar atleta.";

                try {

                    const erro =
                        await resposta.json();

                    mensagem =
                        erro.erro ||
                        erro.message ||
                        mensagem;

                } catch {}


                alert(mensagem);

                return;

            }


            alert(
                id
                    ? "Atleta atualizado com sucesso!"
                    : "Atleta cadastrado com sucesso!"
            );


            limpar();

            await listar();


            document
                .getElementById("modalidades")
                .scrollIntoView({
                    behavior: "smooth"
                });


        } catch (erro) {

            console.error(erro);

            alert(
                "Não foi possível conectar ao servidor."
            );

        }

    }
);


// ==========================================
// EDITAR
// ==========================================

async function editar(id) {

    try {

        const resposta =
            await fetch(`${API}/${id}`);


        if (!resposta.ok) {

            alert("Atleta não encontrado.");

            return;

        }


        const atleta =
            await resposta.json();


        document.getElementById("id").value =
            atleta.id;

        document.getElementById("nome").value =
            atleta.nome;

        document.getElementById("modalidade").value =
            atleta.modalidade;

        document.getElementById("idade").value =
            atleta.idade;

        document.getElementById("salarioMensal").value =
            atleta.salarioMensal;


        document.getElementById("textoBotao")
            .textContent =
            "Salvar alterações";


        document
            .getElementById("cadastro")
            .scrollIntoView({
                behavior: "smooth"
            });


    } catch (erro) {

        console.error(erro);

        alert("Erro ao buscar atleta.");

    }

}


// ==========================================
// EXCLUIR
// ==========================================

async function excluir(id) {

    const atleta =
        atletas.find(
            item => item.id === id
        );


    if (!atleta) {
        return;
    }


    const confirmar =
        confirm(
            `Deseja realmente excluir ${atleta.nome}?`
        );


    if (!confirmar) {
        return;
    }


    try {

        const resposta =
            await fetch(
                `${API}/${id}`,
                {
                    method: "DELETE"
                }
            );


        if (!resposta.ok) {

            alert(
                "Erro ao excluir atleta."
            );

            return;

        }


        alert(
            "Atleta excluído com sucesso!"
        );


        await listar();


    } catch (erro) {

        console.error(erro);

        alert(
            "Não foi possível conectar ao servidor."
        );

    }

}


// ==========================================
// LIMPAR FORMULÁRIO
// ==========================================

function limpar() {

    form.reset();

    document.getElementById("id").value = "";

    document.getElementById("textoBotao")
        .textContent =
        "Cadastrar atleta";

}


// ==========================================
// PESQUISA
// ==========================================

document
    .getElementById("pesquisa")
    .addEventListener(
        "input",
        function() {

            const termo =
                this.value
                    .toLowerCase()
                    .trim();


            const filtrados =
                atletas.filter(atleta =>

                    atleta.nome
                        .toLowerCase()
                        .includes(termo)

                    ||

                    atleta.modalidade
                        .toLowerCase()
                        .includes(termo)

                );


            mostrarTabela(filtrados);

        }
    );


// ==========================================
// INICIAR
// ==========================================

listar();