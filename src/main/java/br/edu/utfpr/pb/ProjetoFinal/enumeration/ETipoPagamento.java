package br.edu.utfpr.pb.ProjetoFinal.enumeration;

public enum ETipoPagamento {

    A("A vista"),
    P("A prazo");

    private String pagamento;

    ETipoPagamento (String pagamento) {
        this.pagamento = pagamento;
    }

    public String getTipoPagamento() {
        return pagamento;
    }

    @Override
    public String toString() {
        return pagamento;
    }
}
