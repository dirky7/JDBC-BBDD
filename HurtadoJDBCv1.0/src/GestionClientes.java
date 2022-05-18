
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author Daniel Hurtado Perera
 */
public class GestionClientes {

    public static void main(String[] args) {
    	Scanner ent = new Scanner(System.in);
    	
    	DBManager.connectServidor();
    	
    	DBManager.mostrarBasesDeDatos();
    	
    	System.out.println("\n¿Cual es la base de datos a la que quieres conectarte?");
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
        System.out.println("2. Mostrar campos de una tabla detallada");
        System.out.println("3. Mostrar contenido de una tabla");
        
        System.out.println("4. Crear tabla");
        System.out.println("5. Modificar tabla");
        System.out.println("6. Borrar tabla");
        System.out.println("7. Añadir columna en una tabla");
        
        System.out.println("8. Volcar datos a un fichero");
        System.out.println("9. Importar archivo para insertar");
        System.out.println("10. Importar archivo para actualziar");
        System.out.println("11. Importar archivo para borrar");
        
        Scanner in = new Scanner(System.in);
            
        int opcion = pideInt("Elige una opción: ");
        
        switch (opcion) {
	        case 0:
	        	return true;
            case 1:
                opcionMostrarTablas();
                return false;
            case 2:
                opcionMostrarCamposTablaAmpliado();
                return false;
            case 3:
                opcionSelect();
                return false;
            case 4:
                opcionCrearTabla();
                return false;
            case 5:
            	opcionModificarTabla();
            	return false;
            case 6:
            	opcionBorrarTabla();
            	return false;
            case 7:
            	opcionAnadirColumna();
            	return false;
            case 8:
            	
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

    public static void opcionMostrarTablas() {
        System.out.println("Listado de tablas:");
        DBManager.printTablas();
    }

    public static void opcionMostrarCamposTablaAmpliado() {
        DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es tabla que quieres ver?");
    	
    	
        String nombreTabla = in.nextLine();

        DBManager.printTipoDatosTabla(nombreTabla);

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
    	
    	System.out.println("¿Cuantas columnas quieres que tenga la tabla?");
    	int columnas = in.nextInt();
    	
    	DBManager.crearTabla(nombre, columnas);
    }
    
    public static void opcionModificarTabla()
    {
    	DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es la tabla que quiere modificar?");
    	String nombreTabla = in.nextLine();
    	
    	
    	DBManager.printContenidoTabla(nombreTabla);
    	System.out.println("¿Cual va a ser la columna que quiere modificar?");
    	String nombreColumna = in.nextLine();
    	DBManager.modificarTabla(nombreTabla, nombreColumna);
    }
    
    public static void opcionBorrarTabla()
    {
    	DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es la tabla que se quiere eliminar?");
    	String nombreTabla = in.nextLine();
    	
    	System.out.println("¿Estas seguro?");
    	System.out.println("0. NO | 1. SI");
    	try
    	{
    		int borrar = in.nextInt();
    	
    	
	    	if(borrar == 1)
	    	{
	    		DBManager.borrarTabla(nombreTabla);
	    	}
	    	else
	    	{
	    		System.out.println("Cancelando borrado");
	    	}
    	}
    	catch(Exception ex)
    	{
    		System.err.println("Valor erroneo introducido. VOLVIENDO A MENU PRINCIPAL");
    	}
    	
    }
    
    
    public static void opcionAnadirColumna()
    {
    	DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es la tabla a la que quieres añadir una columna?");
    	String nombreTabla = in.nextLine();
    	
    	
    	System.out.println("¿Cual es el nombre de la nueva columna?");
    	String nombreColumna = in.nextLine();
    	DBManager.modificarTabla(nombreTabla, nombreColumna);
    }
    
    
    
    
}
