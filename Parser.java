/*
    Laboratorio No. 3 - Recursive Descent Parsing
    CC4 - Compiladores

    Clase que representa el parser

    Actualizado: agosto de 2021, Luis Cu
*/

import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    // Puntero next que apunta al siguiente token
    private int next;
    // Stacks para evaluar en el momento
    private Stack<Double> operandos;
    private Stack<Token> operadores;
    // LinkedList de tokens
    private LinkedList<Token> tokens;

    // Funcion que manda a llamar main para parsear la expresion
    public boolean parse(LinkedList<Token> tokens) {
        this.tokens = tokens;
        this.next = 0;
        this.operandos = new Stack<Double>();
        this.operadores = new Stack<Token>();

        // Recursive Descent Parser
        // Imprime si el input fue aceptado
        System.out.println("Aceptada? " + S());

        // Shunting Yard Algorithm
        // Imprime el resultado de operar el input
        System.out.println("Resultado: " + this.operandos.peek());

        // Verifica si terminamos de consumir el input
        if(this.next != this.tokens.size()) {
            return false;
        }
        return true;
    }

    // Verifica que el id sea igual que el id del token al que apunta next
    // Si si avanza el puntero es decir lo consume.
    private boolean term(int id) {
        if(this.next < this.tokens.size() && this.tokens.get(this.next).equals(id)) {
            
            // Codigo para el Shunting Yard Algorithm
            
            if (id == Token.NUMBER) {
				// Encontramos un numero
				// Debemos guardarlo en el stack de operandos
				operandos.push( this.tokens.get(this.next).getVal() );

			} else if (id == Token.SEMI) {
				// Encontramos un punto y coma
				// Debemos operar todo lo que quedo pendiente
				while (!this.operadores.empty()) {
					popOp();
				}
				
			} else {
				// Encontramos algun otro token, es decir un operador
				// Lo guardamos en el stack de operadores
				// Que pushOp haga el trabajo, no quiero hacerlo yo aqui
				pushOp( this.tokens.get(this.next) );
			}

            this.next++;
            return true;
        }
        return false;
    }

    // Funcion que verifica la precedencia de un operador
    private int pre(Token op) {
        /* TODO: Su codigo aqui */

        /* Asignaré un nivel de precedencia a cada operador
        mayor número, mayor precedencia*

        /* PRECEDENCIA:
        ( )
        - unario
        ^
        * / %
        + -
         */

        /* El codigo de esta seccion se explicara en clase */

        switch(op.getId()) {
            case Token.LPAREN:
                return 5; 
            
            case Token.UNARY:
                return 4;
            
            case Token.EXP:
                return 3;
            
            case Token.MULT:
                return 2;
        
            case Token.DIV:
                return 2;
        
            case Token.MOD:
                return 2;
            
            case Token.PLUS:
                return 1;
            
            case Token.MINUS:
                return 1;
            
            default:
                return -1;
        }
    }

    private void popOp() {
        Token op = this.operadores.pop();

        /* TODO: Su codigo aqui */

        /* El codigo de esta seccion se explicara en clase */

        if (op.equals(Token.PLUS)) {
        	double b = this.operandos.pop();
        	double a = this.operandos.pop();
        	this.operandos.push(a + b);
        
        //Implementamos los operadores restantes
        }
        else if (op.equals(Token.MINUS)) {
        double b = this.operandos.pop();
        double a = this.operandos.pop();
        this.operandos.push(a - b);

        } 
        else if (op.equals(Token.MULT)) {
        	double b = this.operandos.pop();
        	double a = this.operandos.pop();
        	this.operandos.push(a * b);

        } 
        else if (op.equals(Token.DIV)) {
            double b = this.operandos.pop();
            double a = this.operandos.pop();
            System.out.println("div " + a + " / " + b);
            this.operandos.push(a / b);
        } 
        
        else if (op.equals(Token.MOD)) {
            double b = this.operandos.pop();
            double a = this.operandos.pop();
            this.operandos.push(a % b);
        } 
        
        else if (op.equals(Token.EXP)) {
            double b = this.operandos.pop();
            double a = this.operandos.pop();
            this.operandos.push(Math.pow(a, b));
        } 
        
        else if (op.equals(Token.UNARY)) {
            double a = this.operandos.pop();
            this.operandos.push(-a);
        } 
        else {
            System.out.println("Operador desconocido");
        
        }
    }

    private void pushOp(Token op) {
        /* TODO: Su codigo aqui */
        /* Casi todo el codigo para esta seccion se vera en clase */
    
        if(this.operadores.isEmpty()){
            this.operadores.push(op);
            return;
        }

            int precedenciaOp = pre(op);
   
            int precedenciaOpEnStack = pre(this.operadores.peek());

        	while(!this.operadores.isEmpty() && precedenciaOpEnStack >= precedenciaOp){
                popOp(); 
                
                if(this.operadores.isEmpty()){
                    break;
                }
                precedenciaOpEnStack = pre(this.operadores.peek());
            }
            
            this.operadores.push(op);

    }
    
    private boolean S() {
        return E() && term(Token.SEMI);
    }

    private boolean E() {
        if(!T()){
            return false;
        }
        return EPrime();
    }


    private boolean EPrime() {
        int pos = next;
        if(term(Token.PLUS)){
            if(T() && EPrime()){
                return true;
            }
            next = pos;
        }
        pos = next;
        if(term(Token.MINUS)){
            if(T() && EPrime()){
                return true;
            }
            next = pos;
        }
        return true;
    }


    private boolean T(){
        if(!F()){
            return false;
        }
        return TPrime();
    }

    private boolean TPrime(){
        int pos = next;
        if(term(Token.MULT)){
            if(F() && TPrime()){
                return true;
            }
            next = pos;
        }
        pos = next;

        if(term(Token.DIV)){
            if(F() && TPrime()){
                return true;
            }
            next = pos;
        }
        pos = next;

        if(term(Token.MOD)){
            if(F() && TPrime()){
                return true;
            }
            next = pos;
        }
        return true;
    }

    private boolean F(){
        if(!G()){
            return false;
        }
        return FPrime();
    }

    private boolean FPrime(){
        int pos = next;
        if(term(Token.EXP)){
            if(G() && FPrime()){
                return true;
            }
            next = pos;
        }
        return true;
    }

    private boolean G(){
        int pos = next;
        if(term(Token.MINUS)){
            if(G()){
                return true;
            }
            next = pos; 
        }

        pos = next;
        if(term(Token.LPAREN)){
            if(E() && term(Token.RPAREN)){
                return true;
            }
            next = pos;
        }

        pos = next;
        if(term(Token.NUMBER)){
            return true;
        }
        return false;

    }

}
