/*
    Laboratorio No. 3 - Recursive Descent Parsing
    CC4 - Compiladores

    Clase que sirve para representar un token

    Actualizado: agosto de 2021, Luis Cu
*/

public final class Token {
    // Tokens
    public static final int SEMI   = 0;  // ;
    public static final int PLUS   = 1;  // +
    public static final int MINUS  = 2;  // -
    public static final int MULT   = 3;  // *
    public static final int DIV    = 4;  // /
    public static final int MOD    = 5;  // %
    public static final int EXP    = 6;  // ^
    public static final int LPAREN = 7;  // (
    public static final int RPAREN = 8;  // )
    public static final int NUMBER = 9;  // number
    public static final int ERROR  = 10; // error
    public static final int UNARY  = 11; // ~ menos unario

    // Representación de cada token en texto
    private static final String[] tokens = {
        ";",
        "+",
        "-",
        "*",
        "/",
        "%",
        "^",
        "(",
        ")",
        "NUMBER",
        "ERROR",
        "~"
    };

    private int id;
    private String val;

    // Constructor para tokens con valor (como NUMBER)
    public Token(int id, String val) {
        this.id = id;
        this.val = val;
    }

    // Constructor para tokens sin valor
    public Token(int id) {
        this(id, null);
    }

    // Obtener valor numérico (si es un NUMBER)
    public double getVal() {
        return Double.parseDouble(this.val);
    }

    // Obtener el ID del token
    public int getId() {
        return this.id;
    }

    // Comparar token con un ID
    public boolean equals(int id) {
        return this.id == id;
    }

    // Mostrar el token como String
    public String toString() {
        if(this.id == Token.NUMBER && this.val != null) {
            return Token.tokens[this.id] + " : " + this.val;
        }
        return Token.tokens[this.id];
    }
}
