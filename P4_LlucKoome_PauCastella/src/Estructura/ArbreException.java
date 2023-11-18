package Estructura;

public class ArbreException extends Exception{
    long serialVersionUID;

    ArbreException(String s){
        this.serialVersionUID = Long.parseLong(s);
    }
}
