
import java.util.Scanner;

/**
 *
 * @author Daniel Hurtado Perera
 */
public class GestionClientes {

    public static void main(String[] args) {
    	Scanner ent = new Scanner(System.in);
    	
    	System.out.println("¿Cual es la base de datos a la que quieres conectarte?");
    	String bbdd = ent.nextLine();
    	
        DBManager.loadDriver();
        DBManager.connect(bbdd);

        boolean salir = false;
        do {
            salir = menuPrincipal();
        } while (!salir);

        DBManager.close();

    }

    public static boolean menuPrincipal() {
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("0. Salir");
        System.out.println("1. Mostrar tablas");
        System.out.println("2. Mostrar campos de una tabla");
        
        System.out.println("3. Mostrar contenido de una tabla");
        System.out.println("4. Crear tabla");
        
        Scanner in = new Scanner(System.in);
            
        int opcion = pideInt("Elige una opción: ");
        
        switch (opcion) {
	        case 0:
	        	return true;
            case 1:
                opcionMostrarTablas();
                return false;
            case 2:
                opcionMostrarCamposTabla();
                return false;
            case 3:
                opcionSelect();
                return false;
            case 4:
                opcionCrearTabla();
                return false;
            default:
                System.out.println("Opción elegida incorrecta");
                return false;
        }
        
    }
    
    public static int pideInt(String mensaje){
        
        while(true) {
            try {
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                int valor = in.nextInt();
                //in.nextLine();
                return valor;
            } catch (Exception e) {
                System.out.println("No has introducido un número entero. Vuelve a intentarlo.");
            }
        }
    }
    
    public static String pideLinea(String mensaje){
        
        while(true) {
            try {
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                String linea = in.nextLine();
                return linea;
            } catch (Exception e) {
                System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
            }
        }
    }

    public static void opcionMostrarTablas() {
        System.out.println("Listado de tablas:");
        DBManager.printTablas();
    }

    public static void opcionMostrarCamposTabla() {
        DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es tabla que quieres ver?");
    	
    	
        String nombreTabla = in.nextLine();

        DBManager.printContenidoTabla(nombreTabla);

    }

    public static void opcionSelect() {
    	DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es el contenido de la tabla que quieres ver?");
    	
    	String nombreTabla = in.nextLine();
    	
    	DBManager.printSelect(nombreTabla);
        
    }
    
    public static void opcionCrearTabla()
    {
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual va a ser el nombre de la tabla?");
    	String nombre = in.nextLine();
    	
    	System.out.println("\n¿Cuantas columnas quieres que tenga la tabla?");
    	int columnas = in.nextInt();
    	
    	DBManager.crearTabla(nombre, columnas);
    }
}
