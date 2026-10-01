// Aluno: Lucas Rodrigues Cunha
package entities;

/**
 * Enum com os estados brasileiros.
 *
 * O nome da constante é a sigla (MG, SP...) e o atributo name guarda o nome
 * por extenso. O JComboBox do formulário é preenchido com State.values().
 */
public enum State {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Texto exibido no JComboBox: nome por extenso e sigla, ex.: "Minas Gerais (MG)".
    @Override
    public String toString() {
        return name + " (" + name() + ")";
    }
}
