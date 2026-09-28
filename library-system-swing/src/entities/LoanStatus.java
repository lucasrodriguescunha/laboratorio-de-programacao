package entities;

public enum LoanStatus {

    ACTIVE("Ativo"),
    FINISHED("Finalizado");

    private final String label;

    LoanStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
