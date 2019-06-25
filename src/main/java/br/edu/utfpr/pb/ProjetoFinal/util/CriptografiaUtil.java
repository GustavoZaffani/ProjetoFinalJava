package br.edu.utfpr.pb.ProjetoFinal.util;

import java.io.UnsupportedEncodingException;

public class CriptografiaUtil {

    /**
     * Irá percorrer cada caracter da senha, pegando o código ASCII do mesmo e somando mais 3,
     * concatenando com ';' para separar os códigos ASCII
     * Quando transformar tudo em ASCII, irá passar o resultado para um array de bytes
     */
    public static byte[] criptografa(String senha) {
        StringBuilder passEncode = new StringBuilder();
        char[] passwordToCharArray = senha.toCharArray();
        for (int i = 0; i < passwordToCharArray.length; i++) {
            int codAscii = passwordToCharArray[i] + 3;
            passEncode.append(codAscii);
            passEncode.append(";");
        }
        byte[] passwordEncode = String.valueOf(passEncode).getBytes();
        return passwordEncode;
    }

    /**
     * Fará o processo contrário...
     * */
    public static String descriptografa(byte[] array) {
        StringBuilder passwordDecode = new StringBuilder();
        try {
            String byteToString = new String(array, "UTF-8");
            String[] splitString = byteToString.split(";");
            for (int i = 0; i < splitString.length; i++) {
                int codAscii = Integer.parseInt(splitString[i]) - 3;
                char converter = (char) codAscii;
                passwordDecode.append(converter);
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro para converter byte para String: " + e.getMessage());
            e.printStackTrace();
        }
        return String.valueOf(passwordDecode);
    }
}
