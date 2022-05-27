
import java.util.*;
import java.io.*;

/**
 *
 * @author Daniel Hurtado Perera
 */
@SuppressWarnings({ "resource", "unused" })

public class GestionClientes {
	
    public static void main(String[] args){
		Scanner ent = new Scanner(System.in);
    	
    	DBManager.connectServidor();
    	
    	try {
			opcionCambiarBaseDatos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        DBManager.loadDriver();
        

        boolean salir = false;
        do {
            salir = menuPrincipal();
        } while (!salir);

        DBManager.close();

    }

    
    /**
     * Menu principal del programa
     * @return Devuelve un booleano para el bucle del menu princiapl
     */
	public static boolean menuPrincipal() {
		
        System.out.println("\n");
        System.out.println("MENU PRINCIPAL");
        System.out.println("0. Salir");
        
        System.out.println("\t\t--------------MOSTRAR CONTENIDO--------------");
        System.out.println("1. Mostrar tablas");
        System.out.println("2. Mostrar campos de una tabla detallada");
        System.out.println("3. Mostrar contenido de una tabla");
        
        System.out.println("\t\t--------------ALTERACIÓN DE TABLAS--------------");
        System.out.println("4. Crear tabla");
        System.out.println("5. Modificar el tipo de dato de una tabla");
        System.out.println("6. Borrar tabla");
        System.out.println("7. Añadir columna en una tabla");
        
        System.out.println("\t\t--------------IMPORTADO DE FICHEROS Y PROCEDIMIENTOS ALMACENADOS--------------");
        System.out.println("8. Volcar datos a un fichero");
        System.out.println("9. Importar archivo para insertar");
        System.out.println("10. Importar archivo para actualziar");
        System.out.println("11. Importar archivo para borrar");
        System.out.println("12. Ver procedimientos disponibles");
        System.out.println("13. Ejecutar procedimiento");
        
        System.out.println("\t\t--------------CAMBIAR DE BASE DE DATOS--------------");
        System.out.println("20. Cambiar de base de datos");
        
		Scanner in = new Scanner(System.in);
            
        try
        {
        
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
                	opcionVolcarDatos();
                	return false;
                case 9:
                	opcionImportarInsertar();
                	return false;
                case 10:
                	opcionImportarActualizar();
                	return false;
                case 11:
                	opcionImportarBorrar();
                	return false;
                case 12:
                	verProc();
                	return false;
                case 13:
                	opcionProcedimiento();
                	return false;
                case 20:
                	opcionCambiarBaseDatos();
                	return false;
                default:
                    System.out.println("Opción elegida incorrecta");
                    return false;
            }
        	
        }
        catch(Exception ex)
        {
        	
        }
        
        return false;
        
    }
    
	/**
	 * Metodo que pide los numeros del menu principal
	 * @param mensaje 
	 * @return
	 */
    public static int pideInt(String mensaje){
        
        while(true) {
            try {
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                int valor = in.nextInt();
                
                System.out.println("\n\n");
                //in.nextLine();
                return valor;
            } catch (Exception e) {
                System.out.println("No has introducido un número entero. Vuelve a intentarlo.");
            }
        }
    }

    
    /**
     * Metodo que pregunta a cual de las base de datos listas quieres conectarte
     */
    public static void opcionCambiarBaseDatos() throws Exception
    {
    	Scanner ent = new Scanner(System.in);
    	
    	DBManager.mostrarBasesDeDatos();
    	
    	System.out.println("\n¿Cual es la base de datos a la que quieres conectarte?");
    	String bbdd = ent.nextLine();
    	
    	DBManager.connect(bbdd);
    }
    
    /**
     * Metodo que imprime el listado de las tablas disponibles de una base de datos
     */
    public static void opcionMostrarTablas() {
        System.out.println("Listado de tablas:");
        DBManager.printTablas();
    }

    
    /**
     * Metodo que imprime el nombre de las columnas y el tipo de valor que sostienen de una tabla de cualquier base de datos
     */
    public static void opcionMostrarCamposTablaAmpliado() throws Exception {
        DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es tabla que quieres ver?");
    	
    	
        String nombreTabla = in.nextLine();

        DBManager.printTipoDatosTabla(nombreTabla);

    }
    
    
    /**
     * Metodo que pide el nombre de una tabla e imprime el SELECT * FROM [nombre de la tabla]
     */
    public static void opcionSelect() throws Exception {
    	DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es el contenido de la tabla que quieres ver?");
    	
    	String nombreTabla = in.nextLine();
    	
    	System.out.println(DBManager.printSelect(nombreTabla));
        
    }
    
    
    /**
     * Metodo que pide al usuario cual va a ser el nombre de la tabla que quiere crear y cuantas columnas quiere que tenga la tabla
     */
    public static void opcionCrearTabla()
    {    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual va a ser el nombre de la tabla?");
    	String nombre = in.nextLine();
    	
    	System.out.println("¿Cuantas columnas quieres que tenga la tabla?");
    	int columnas = in.nextInt();
    	
    	DBManager.crearTabla(nombre, columnas);
    }
    
    
    /**
     *  Metodo que pide al usuario cual es la tabla que quiere modificar el tipo de valor de una columna
     */
    public static void opcionModificarTabla()
    {
    	DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿Cual es la tabla que quiere modificar?");
    	String nombreTabla = in.nextLine();
    	
    	System.out.println(DBManager.printContenidoTabla(nombreTabla));
    	
    	System.out.println("¿Cual va a ser la columna que quiere modificar?");
    	String nombreColumna = in.nextLine();
    	DBManager.modificarTabla(nombreTabla, nombreColumna);
    }
    
    
    /**
     * Metodo que pide al usuario cual es el nombre de la tabla que quiere eliminar (DROP)
     */
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
    
    /**
     * Metodo para añadir una nueva columna a una tabla. Pide el nombre de la tabla a la que quiere añadir una columna y el nombre de la columna que quiere añadir
     */
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
    
    /**
     * Metodo que pide al usuario la tabla de la base de datos que quiere volcar en un fichero
     */
    public static void opcionVolcarDatos()
    {
    	DBManager.printTablas();
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("\n¿De que tabla quieres volcar los datos?");
    	String nombreTabla = in.nextLine();
    	
    	System.out.println("¿Nombre del fichero resultante?");
    	String fichero = in.nextLine();
    	
    	File resultadoDatos = new File(fichero);
    	
    	try {
			FileWriter escritor = new FileWriter(resultadoDatos.getAbsolutePath());
			
			escritor.write(DBManager.cabeceraFichero(nombreTabla));
			
			escritor.write(DBManager.printSelect(nombreTabla));
			
			escritor.close();
		}
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Metodo que pide al usuario el nombre del fichero que quiera importar los datos a una base de datos cualquiera
     */
    public static void opcionImportarInsertar()
    {
    	Scanner in = new Scanner(System.in);
    	ArrayList<String> sentencias = new ArrayList<String>();
    	String fichero, baseDatos, tabla, columnas, valores;
    	
    	System.out.println("¿Cual es el fichero que quiere importar para insertar?");
    	fichero = in.nextLine();
    	
    	File importacion = new File(fichero);
    	
    	try
    	{
    		Scanner lector = new Scanner(importacion);
    		
    		do
    		{
    			sentencias.add(lector.nextLine());
    		}
    		while(lector.hasNext());
    		
    		lector.close();
    		
    		for(int contador = 3; contador < sentencias.size(); contador++)
    		{
    			String[] valorCampo;
    			String valoresArreglado = "";
    			
    			baseDatos = sentencias.get(0);
    			
    			tabla = sentencias.get(1);
    			
    			columnas = sentencias.get(2);
    			
    			valores = sentencias.get(contador);
    			
    			
    			valorCampo = valores.split(",");
    			
    			
    			for(int contador2 = 0; contador2 < valorCampo.length; contador2++)
    			{
    				
    				if(contador2 == valorCampo.length-1)
    				{
    					valoresArreglado += "'"+valorCampo[contador2]+"'";
    				}
    				else
    				{
    					valoresArreglado += "'"+valorCampo[contador2]+"',";
    				}
    				
    				
    			}
    			
    			DBManager.importarInsert(baseDatos, tabla, columnas, valoresArreglado);
    			
    		}
    		
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }

    /**
     * Metodo que pide al usuario el nombre de un fichero que sea importar para actualizar valores a una base de datos
     */
    public static void opcionImportarActualizar()
    {
    	Scanner in = new Scanner(System.in);
    	ArrayList<String> sentencias = new ArrayList<String>();
    	String fichero, baseDatos, tabla, columnas, valores;
    	
    	System.out.println("¿Cual es el fichero que quiere importar para actualizar?");
    	fichero = in.nextLine();
    	
    	File importacion = new File(fichero);
    	
    	try
    	{
    		Scanner lector = new Scanner(importacion);
    		
    		do
    		{
    			sentencias.add(lector.nextLine());
    		}
    		while(lector.hasNext());
    		
    		lector.close();
    		
    		for(int contador = 3; contador < sentencias.size(); contador++)
    		{
    			String[] valorCampo;
    			String valoresArreglado = "";
    			
    			baseDatos = sentencias.get(0);
    			
    			tabla = sentencias.get(1);
    			
    			columnas = sentencias.get(2);
    			
    			valores = sentencias.get(contador);
    			
    			
    			valorCampo = valores.split(",");
    			
    			
    			for(int contador2 = 0; contador2 < valorCampo.length; contador2++)
    			{
    				
    				if(contador2 == valorCampo.length-1)
    				{
    					valoresArreglado += "'"+valorCampo[contador2]+"'";
    				}
    				else
    				{
    					valoresArreglado += "'"+valorCampo[contador2]+"',";
    				}
    				
    				
    			}
    			
    			DBManager.importarUpdate(baseDatos, tabla, columnas, valoresArreglado);
    			
    		}
    		
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
    
    /**
     * Metodo que pide al usuario un fichero para importar el borrado de los registros
     */
    public static void opcionImportarBorrar()
    {
    	Scanner in = new Scanner(System.in);
    	ArrayList<String> sentencias = new ArrayList<String>();
    	String fichero, baseDatos, tabla, columnas, valores;
    	
    	int contador2 = 0;
    	
    	System.out.println("¿Cual es el fichero que quiere importar para borrar?");
    	fichero = in.nextLine();
    	
    	File importacion = new File(fichero);
    	
    	try
    	{
    		Scanner lector = new Scanner(importacion);
    		
    		do
    		{
    			sentencias.add(lector.nextLine());
    		}
    		while(lector.hasNext());
    		
    		lector.close();
    		
			String[] valorCampo;
			String valoresArreglado = "";
			
			baseDatos = sentencias.get(0);
			
			tabla = sentencias.get(1);
			
			columnas = sentencias.get(2);
			
			
			valores = sentencias.get(3);
			
			
			valorCampo = valores.split(",");
			
			
			for(contador2 = 0; contador2 < valorCampo.length; contador2++)
			{
				System.out.println(contador2);
				if(contador2 == valorCampo.length-1)
				{
					valoresArreglado += "'"+valorCampo[contador2]+"'";
				}
				else
				{
					valoresArreglado += "'"+valorCampo[contador2]+"',";
				}
				
			}
			
			DBManager.importarDelete(baseDatos, tabla, columnas, valoresArreglado);
			
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
    
    
    /**
     * Metodo que imprime por pantalla los nombres de los procedimientos almacenados con los parametros que necesitan para ejecutarse
     */
    public static void verProc()
    {
    	System.out.println(DBManager.verProcesosAlmacenados());
    }
    
    /**
     * Metodo que pide el nombre del procedimiento almacenado que se desea ejecutar
     */
    public static void opcionProcedimiento()
    {
    	Scanner in = new Scanner(System.in);
    	
    	System.out.println("¿Cual de los siguens procedimientos quieres ejecutar?");
    	
    	verProc();
    	
    	String procedimiento = in.nextLine();
    	
    	System.out.println(DBManager.procedimiento(procedimiento));
    	
    	
    }
    
}
