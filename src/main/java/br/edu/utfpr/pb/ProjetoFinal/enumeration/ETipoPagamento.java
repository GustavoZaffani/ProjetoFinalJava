package br.edu.utfpr.pb.ProjetoFinal.enumeration;

public enum ETipoPagamento {

    A("A VISTA"),
    P("A PRAZO");

    private String pagamento;

    ETipoPagamento (String pagamento) {
        this.pagamento = pagamento;
    }

    public String getTipoPagamento() {
        return pagamento;
    }
}
