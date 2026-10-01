// Aluno: Lucas Rodrigues Cunha
package entities;

/**
 * Enum com os cargos disponíveis no formulário.
 *
 * Cada constante guarda a descrição que aparece na tela. Para incluir um
 * cargo novo basta acrescentar uma constante aqui: o JComboBox é preenchido
 * com Role.values(), então o formulário não precisa ser alterado.
 */
public enum Role {
    DESENVOLVEDOR_BACKEND("Desenvolvedor Backend"),
    DESENVOLVEDOR_FRONTEND("Desenvolvedor Frontend"),
    DESENVOLVEDOR_FULLSTACK("Desenvolvedor Full Stack"),
    ANALISTA_DE_SISTEMAS("Analista de Sistemas"),
    ANALISTA_DE_SUPORTE("Analista de Suporte Técnico"),
    ARQUITETO_DE_SOFTWARE("Arquiteto de Software"),
    ENGENHEIRO_DE_DADOS("Engenheiro de Dados"),
    CIENTISTA_DE_DADOS("Cientista de Dados"),
    ENGENHEIRO_DEVOPS("Engenheiro DevOps"),
    ADMINISTRADOR_DE_REDES("Administrador de Redes"),
    ADMINISTRADOR_DE_BANCO_DE_DADOS("Administrador de Banco de Dados (DBA)"),
    ANALISTA_DE_SEGURANCA("Analista de Segurança da Informação"),
    ANALISTA_DE_QA("Analista de Qualidade (QA)"),
    SCRUM_MASTER("Scrum Master"),
    PRODUCT_OWNER("Product Owner"),
    GERENTE_DE_PROJETOS_TI("Gerente de Projetos de TI"),
    GERENTE_DE_TI("Gerente de TI (CTO/CIO)"),
    ESTAGIARIO_DE_TI("Estagiário de TI");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // O JComboBox usa o toString() para exibir cada item da lista.
    @Override
    public String toString() {
        return description;
    }
}
