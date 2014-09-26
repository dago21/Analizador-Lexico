package analizadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Sofia Legal - David Gomez
 * 
*/
public class AnalizadorLexico {

    /**
     * @param args the command line arguments
     *
     */
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    FileWriter fichero = null;
    PrintWriter pw = null;

    public void escribir(String salida, String palabra, boolean cerrar) {

        try {
            if(salida.equals("")) {
                System.out.println(System.getProperty("java.class.path") + "\\salida.txt");
                fichero = new FileWriter(System.getProperty("java.class.path") + "\\salida.txt");
            } else {
                fichero = new FileWriter(salida);
            }
            pw = new PrintWriter(fichero);
            pw.println(palabra);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // cerrar fichero
                if (cerrar) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void leer(String fuente, String salida) throws FileNotFoundException {
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            if (fuente.equals("")) {
                archivo = new File("C:\\fuente.txt");
            } else {
                archivo = new File(fuente);
            }
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            String caracter = "";
            String valor = "";
            String errores = "";

            AnalizadorLexico t = new AnalizadorLexico();
            String archivo = "";
            int cont = 0;
            while ((linea = br.readLine()) != null) {
                cont++;
                System.out.println("linea " + linea);
                String finDeLinea = "";

                // recorre cada linea
                caracter = "";
                for (int i = 0; i < linea.length(); i++) {

                    // mientras no se espacio  ,  o "" concatena para obtener el token
                    if (linea.charAt(i) != ' ' && linea.charAt(i) != ',' && linea.charAt(i) != '"') {

                        caracter = caracter + linea.charAt(i);

                    } else if (linea.charAt(i) == ',') {
                        // si el caracter es , se guarda para concatenar al final
                        finDeLinea = "COMA";

                    }

                    //
                    if (linea.charAt(i) == '"' && i != linea.length() - 1) {

                        int contador = i + 1;
                        caracter = caracter + linea.charAt(i);
                        while (contador < linea.length() && linea.charAt(contador) != '"') {
                            System.out.println("token " + linea.charAt(contador));
                            System.out.println("Entra a while " + contador);
                            caracter = caracter + linea.charAt(contador);
                            contador++;
                        }
                        if (caracter != "") {
                            caracter = caracter + "\"";
                        }
                        i = contador;
                        System.out.println("Caracter " + caracter);

                    } else if (linea.charAt(i) == '"' && i == linea.length() - 1) {
                        valor = "Cadena erronea linea : -" + cont;
                        archivo = archivo + valor;

                    }

                    if ((linea.charAt(i) == ' ' || linea.charAt(i) == ',') && !caracter.equals("")) {

                        if ("[".equals(caracter)) {
                            valor = "L_CORCHETE";
                        } else if ("]".equals(caracter)) {
                            valor = "R_CORCHETE";
                        } else if ("{".equals(caracter)) {
                            valor = "L_LLAVE";
                        } else if ("}".equals(caracter)) {
                            valor = "R_LLAVE";
                        } else if (",".equals(caracter)) {
                            valor = ",";
                        } else if (":".equals(caracter)) {
                            valor = "DOS_PUNTOS";
                        } else if ("true".equals(caracter) || "TRUE".equals(caracter)) {
                            valor = "PR_TRUE";
                        } else if ("false".equals(caracter) || "FALSE".equals(caracter)) {
                            valor = "PR_FALSE";
                        } else if ("null".equals(caracter) || "NULL".equals(caracter)) {
                            valor = "PR_NULL";
                        } else if (caracter.charAt(0) == '"' && caracter.charAt(caracter.length() - 1) == '"') {

                            valor = "LITERAL_CADENA";
                        }

                        archivo = archivo + valor + " ";
                        // si se encontro una , se agrega al final
                        if (!finDeLinea.equals("")) {
                            archivo = archivo + " " + finDeLinea;

                        }
                        caracter = "";
                    }

                    if (i == linea.length() - 1 && caracter != "") {
                        if ("[".equals(caracter)) {
                            valor = "L_CORCHETE";
                        } else if ("]".equals(caracter)) {
                            valor = "R_CORCHETE";
                        } else if ("{".equals(caracter)) {
                            valor = "L_LLAVE";
                        } else if ("}".equals(caracter)) {
                            valor = "R_LLAVE";
                        } else if (",".equals(caracter)) {
                            valor = ",";
                        } else if (":".equals(caracter)) {
                            valor = "DOS_PUNTOS";
                        } else if ("true".equals(caracter) || "TRUE".equals(caracter)) {
                            valor = "PR_TRUE";
                        } else if ("false".equals(caracter) || "FALSE".equals(caracter)) {
                            valor = "PR_FALSE";
                        } else if ("null".equals(caracter) || "NULL".equals(caracter)) {
                            valor = "PR_NULL";
                        } else if (caracter.charAt(0) == '"' && caracter.charAt(caracter.length() - 1) == '"') {

                            valor = "LITERAL_CADENA";
                        } else {
                            valor = "ERROR linea ";
                        }

                        archivo = archivo + valor + " ";
                        // si se encontro una , se agrega al final
                        if (!finDeLinea.equals("")) {
                            archivo = archivo + " " + finDeLinea;

                        }
                        caracter = "";
                    }
                }

                // se escribe en el archivo
                archivo = archivo + "\n";
                //archivo = archivo + "EOF";
                t.escribir(salida,archivo, true);

            }
            archivo = archivo + "EOF";
            t.escribir(salida,archivo, true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        AnalizadorLexico t = new AnalizadorLexico();
        System.out.println("Ingrese direccion de fuente: ");
        String fuente = in.nextLine();
        System.out.println("Ingrese direccion del archivo salida: ");
        String salida = in.nextLine();
        if(salida.equals("")){
            System.out.println("Se creara el archivo en la carpeta build/classes del proyecto");
        }
        t.leer(fuente, salida);
        // TODO code application logic here
    }

}
