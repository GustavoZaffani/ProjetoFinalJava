package br.edu.utfpr.pb.ProjetoFinal.enumeration;

public enum ETipoContato {
    RESIDENCIAL(11), CELULAR(22), COMERCIAL(33);

    private final Integer id;

    ETipoContato(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static ETipoContato findById(Integer id) {
        for (ETipoContato tc : ETipoContato.values()) {
            if (tc.getId().equals(id)) return tc;
        }
        throw new IllegalArgumentException("Tipo inválido");
    }
}
