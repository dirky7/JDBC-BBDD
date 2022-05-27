
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author Daniel Hurtado Perera
 */

@SuppressWarnings({ "resource", "unused" })

public class DBManager {

    // Conexión a la base de datos
    private static Connection conn = null;

    // Configuración de la conexión a la base de datos
    private static final String DB_HOST = "172.16.1.97";
    private static final String DB_PORT = "3306";
    private static String DB_NAME;
    private static String DB_URL;
    private static final String DB_USER = "username";
    private static final String DB_PASS = "password";
    private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

    // Configuración de la tabla Clientes
    //private static final String DB_CLI = "clientes";
    //private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
    //private static final String DB_CLI_ID = "id";
    //private static final String DB_CLI_NOM = "nombre";
    //private static final String DB_CLI_DIR = "direccion";

    //////////////////////////////////////////////////
    // MÉTODOS DE CONEXIÓN A LA BASE DE DATOS
    //////////////////////////////////////////////////
    
    private static String consulta;
    private static ArrayList <String> columnasTabla;
    
    
    /**
     * Intenta cargar el JDBC driver.
     * @return true si pudo cargar el driver, false en caso contrario
     */
    public static boolean loadDriver() {
        try {
            System.out.print("Cargando Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("OK!");
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta conectar con la base de datos.
     *
     * @return true si pudo conectarse, false en caso contrario
     */
    public static boolean connect(String bbdd) {
    	DB_NAME = bbdd;
    	DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    	
    	try {
            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public static boolean connectServidor() {
    	DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT;
    	
    	try {
            System.out.print("Conectando al servidor...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex+": Base de datos NO conectada");
            return false;
        }
    }
    
    /**
     * Comprueba la conexión y muestra su estado por pantalla
     *
     * @return true si la conexión existe y es válida, false en caso contrario
     */
    public static boolean isConnected() {
        // Comprobamos estado de la conexión
        try {
            if (conn != null && conn.isValid(0)) {
                System.out.println(DB_MSQ_CONN_OK);
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(DB_MSQ_CONN_NO);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public static void close() {
        try {
            System.out.print("Cerrando la conexión...");
            conn.close();
            System.out.println("OK!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Muestra todas las bases de datos en una lista
     */
    public static void mostrarBasesDeDatos()
    {
    	consulta = "SHOW DATABASES";
    	
    	try
    	{
    		PreparedStatement verBases = conn.prepareStatement(consulta);
    		ResultSet rs = verBases.executeQuery(consulta);
    		
    		System.out.println("Bases de datos del servidor "+DB_HOST);
    		
    		while(rs.next())
    		{
    			String nombreBase = rs.getString("Database");
    			System.out.println(nombreBase);
    		}
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("\n"+ex+"\n\t  -->  Error al mostrar las bases de datos\n");
    	}
    }
    
    
    /**
     * Imprime el nombre de las tablas de la base de datos seleccionada. Usado para mostrar el nombre de la tablas para elegir
     */
    
    public static void printTablas()
    {
    	consulta = "show full tables from " + DB_NAME;
    	
    	try
    	{
    		PreparedStatement imprimirTabla = conn.prepareStatement(consulta);
    		
    		ResultSet rs = imprimirTabla.executeQuery(consulta);
    		
    		System.out.println("Tablas de '"+DB_NAME+"':");
    		
    		while(rs.next())
    		{
    			String nombreTabla = rs.getString("Tables_in_"+DB_NAME);
    			
    			System.out.print("| "+nombreTabla+" ");
    		}
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("\n"+ex+"\n\t  -->  Error al mostrar las tablas de '"+DB_NAME+"'\n");
    	}
    	
    	
    }
    
    /**
     * Imprime el nombre de las columnas de un tabla pasada por parametros
     * @param tabla El parametro tabla para que se muestren las columnas
     * @return Devuelve el nombre de las tablas separadas por tabuladores
     */
    
    public static String printContenidoTabla(String tabla)
    {
    	consulta = "show columns from " + tabla;
    	
    	String resultado = "";
    	
    	try
    	{
    		PreparedStatement imprimirTabla = conn.prepareStatement(consulta);
    		
    		ResultSet rs = imprimirTabla.executeQuery(consulta);
    		
    		System.out.println("\nContenido de '"+tabla+"':");
    		
    		while(rs.next())
    		{
    			String nombreTabla = rs.getString("Field");
    			
    			resultado += nombreTabla + "\t\t";
    		}
    		
    		resultado += "\n";
    		
    	}
    	
    	catch(SQLException ex)
    	{
    		System.out.println("\n"+ex+"\n\t  -->  Error al mostrar las columnas de la tabla '"+tabla+"'\n");
    	}
    	
		return resultado;
    	
    }
    
    /**
     * Imprime el contenido de la tabla pasada por parametro
     * @param tabla Nombre de la tabla que se quiere ver
     */
    
    public static void printTipoDatosTabla(String tabla)
    {
    	consulta = "show columns from " + tabla;
    	
    	try
    	{
    		PreparedStatement imprimirTabla = conn.prepareStatement(consulta);
    		
    		ResultSet rs = imprimirTabla.executeQuery(consulta);
    		
    		System.out.println("\nContenido de '"+tabla+"':");
    		
    		while(rs.next())
    		{
    			String nombreTabla = rs.getString("Field");
    			String tipoDato = rs.getString("Type");
    			System.out.println(nombreTabla + "\t\t" + tipoDato);
    		}
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("\n"+ex+"\n\t  -->  Error al mostrar las columnas de la tabla '"+tabla+"'\n");
    	}
    	
    	System.out.println();
    }
    
    
    /**
     * Se obtiene el nombre de las columnas para imprimirlas en SELECTs por ejemplo. Usado para imprimir
     * @param tabla Nombre de la tabla de la que se quieren consultar las columnas
     */
    public static void obtenerTabla(String tabla)
    {
    	consulta = "show columns from " + tabla;
    	
    	columnasTabla = new ArrayList<String>();
    	
    	try
    	{
    		PreparedStatement imprimirTabla = conn.prepareStatement(consulta);
    		
    		ResultSet rs = imprimirTabla.executeQuery(consulta);
    		
    		
    		while(rs.next())
    		{
    			String nombreColumna = rs.getString("Field");
    			columnasTabla.add(nombreColumna);
    			
    		}
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("\n"+ex+"\n\t  -->  Error al mostrar las columnas\n");
    	}
    }
    
    
    /**
     * Mitica consulta SELECT de SQL.
     * @param tabla Nombre de la tabla a la que se quiere ver el SELECT
     * @return Devuelve en un String formateado para ver los datos de forma mas legible
     */
    public static String printSelect(String tabla)
    {
    	
    	String resultado;
    	resultado = (printContenidoTabla(tabla));
    	
    	obtenerTabla(tabla);
    	
    	consulta = "select * from " + tabla;
    	
    	
    	try
    	{
    		
    		PreparedStatement select = conn.prepareStatement(consulta);
    		
    		ResultSet rs = select.executeQuery(consulta);
    		
    		while(rs.next())
    		{
    			for(int contador = 0; contador < columnasTabla.size(); contador++)
    			{
    				String tupla = (rs.getString(columnasTabla.get(contador))+"");
    				resultado += tupla+"\t\t";
    			}
    			resultado += "\n";
    		}
    		
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("\n"+ex+"\n\t  -->  Error al mostrar los datos de la tabla '"+tabla+"'\n");
    	}
    	
    	
    	return resultado;
    }
    
    
    /**
     * Metodo para crear tablas.
     * @param nombreTabla Nombre de la tabla que se quiere crear
     * @param columnas Cuantas columnas se quieren crear dentro de la tabla
     */

	public static void crearTabla(String nombreTabla, int columnas) {
		consulta = "create table " + nombreTabla + "(ejemplo int)";
		String nombreColumna;
		Scanner ent = new Scanner(System.in);
		int opcionValor;
		
		try
		{
			PreparedStatement crear = conn.prepareStatement(consulta);
			PreparedStatement introducirColumna;
			PreparedStatement borrar;
			
			int rs = crear.executeUpdate();
			int contador = 0;
				for(contador = 0; contador < columnas; contador++)
				{
					try
					{
						System.out.println("Nombre del columna "+contador);
						nombreColumna = ent.nextLine();
						
						System.out.println("1. String | 2. Int");
						opcionValor = ent.nextInt();
						
						if(nombreColumna == "" || (opcionValor < 1 || opcionValor > 2))
						{
							throw new Exception("Valores errorneos introducidos");
						}
					
					
						ent.nextLine();
						
						switch(opcionValor)
						{
							case 1:
							{
								consulta = "alter table "+nombreTabla+" add "+nombreColumna+" varchar(75)";
								introducirColumna = conn.prepareStatement(consulta);
								rs = introducirColumna.executeUpdate();
								break;
							}
							case 2:
							{
								consulta = "alter table "+nombreTabla+" add "+nombreColumna+" int";
								introducirColumna = conn.prepareStatement(consulta);
								rs = introducirColumna.executeUpdate();
								break;
							}
						}
						
						if (contador == 0)
						{
							consulta = "alter table "+nombreTabla+" drop column ejemplo";
							borrar = conn.prepareStatement(consulta);
							rs = borrar.executeUpdate();
							
						}
					}
					
					catch(InputMismatchException ex)
					{
						ent.nextLine();
						System.out.println("\n"+ex+"\n\t  -->  Error al introducir columna\n");
						contador -= 1;
					}
					
					catch(Exception ex)
					{
						ent.nextLine();
						System.out.println("\n"+ex+"\n\t  -->  Error al introducir columna\n");
						contador -= 1;
					}
				}
			
		}
		catch(SQLException ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al introducir columna\n");
		}
		
	}
	
	/**
	 * Metodo para modificar el tipo de dato que tiene la columna de una tabla
	 * @param nombreTabla Nombre de la tabla que se quiere modificar
	 * @param nombreColumna Nombre de la columna a la que se quiere cambiar el tipo de dato
	 */
	
	public static void modificarTabla(String nombreTabla, String nombreColumna)
	{
		int opcionValor;
		Scanner ent = new Scanner(System.in);
		try
		{
			PreparedStatement modificarColumna;
			
			System.out.println("1. String | 2. Int");
			opcionValor = ent.nextInt();
			
			if(opcionValor < 1 || opcionValor > 2)
			{
				throw new Exception("Valor incorrecto introducido");
			}
			
			switch(opcionValor)
			{
				case 1:
				{
					consulta = "ALTER TABLE "+nombreTabla+" MODIFY COLUMN "+nombreColumna+" varchar(75)";
					modificarColumna = conn.prepareStatement(consulta);
					int rs = modificarColumna.executeUpdate();
					break;
				}
				case 2:
				{
					consulta = "ALTER TABLE "+nombreTabla+" MODIFY COLUMN "+nombreColumna+" int";
					modificarColumna = conn.prepareStatement(consulta);
					int rs = modificarColumna.executeUpdate();
					break;
				}
			}
		}
		catch(SQLException ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al modificar la columna\n");
		}
		catch(Exception ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al modificar la columna\n");
		}
	}

	
	/**
	 * Metodo para borrar una tabla de la base de datos
	 * @param nombreTabla Nombre de la tabla de la base de datos que se quiere borrar
	 */
	public static void borrarTabla(String nombreTabla)
	{
		consulta = "DROP TABLE "+nombreTabla;
		try
		{
			PreparedStatement borrar = conn.prepareStatement(consulta);
			int rs = borrar.executeUpdate();
			
			System.out.println("Se ha borrado "+nombreTabla);
		}
		catch(SQLException ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al eliminar la tabla\n");
		}
	}
	
	
	/**
	 * Metodo para añadir una nueva columna a una tabla
	 * @param nombreTabla Nombre de la tabla a la que se quiere añadir una columna
	 * @param nombreColumna Nombre de la columna que se va a añadir
	 */
	public static void anadirColumna(String nombreTabla, String nombreColumna)
	{
		int opcionValor;
		Scanner ent = new Scanner(System.in);
		try
		{
			PreparedStatement anadirColumna;
			
			System.out.println("1. String | 2. Int");
			opcionValor = ent.nextInt();
			
			if(opcionValor < 1 || opcionValor > 2)
			{
				throw new Exception("Valor incorrecto introducido");
			}
			
			switch(opcionValor)
			{
				case 1:
				{
					consulta = "ALTER TABLE "+nombreTabla+" ADD "+nombreColumna+" varchar(75)";
					anadirColumna = conn.prepareStatement(consulta);
					int rs = anadirColumna.executeUpdate();
					break;
				}
				case 2:
				{
					consulta = "ALTER TABLE "+nombreTabla+" ADD "+nombreColumna+" int";
					anadirColumna = conn.prepareStatement(consulta);
					int rs = anadirColumna.executeUpdate();
					break;
				}
			}
		}
		catch(SQLException ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al añadir columna\n");
		}
		catch(Exception ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al añadir columna\n");
		}
	}
	
	
	/**
	 * Metodo que imprime de forma bonita el nombre de la base de datos y de la tabla de un fichero
	 * @param nombreTabla Nombre de la tabla
	 * @return Devuelve un String con el nombre de la base de datos y la tabla
	 */
	
	public static String cabeceraFichero(String nombreTabla)
	{
		String resultado;
		
		resultado = "Base de datos: "+DB_NAME+"\n";
		resultado += "Tabla: "+nombreTabla+"\n";
		
		return resultado;
		
	}
	
	/**
	 * Metodo que inserta datos a una tabla de una base de datos que se ha pasado por un fichero
	 * @param nombreBaseDatos Nombre de la base de datos a la que se quiere insertar la información.
	 * @param nombreTabla Nombre de la tabla a la que se quiere insertar la informacion.
	 * @param columnas El nombre de las columnas de la tabla
	 * @param valores Es la informacion que se va a insertar a la tabla de la base de datos
	 */
	
	public static void importarInsert(String nombreBaseDatos, String nombreTabla, String columnas, String valores)
	{
		consulta = "INSERT INTO "+nombreBaseDatos+"."+nombreTabla+" ("+columnas+") VALUES ("+valores+");";
		
		try
		{
			PreparedStatement insert = conn.prepareStatement(consulta);
			
			int rs = insert.executeUpdate();
			
			
		}
		catch(SQLException ex)
		{	
			System.out.println("\n"+ex+"\n\t  -->  Error al insertar los datos a la base de datos '"+nombreBaseDatos+"'\n");
		}
	}
	
	
	/**
	 * Metodo que desde la importacion un archivo y actuliza los datos de una tabla de una base de datos
	 * @param nombreBaseDatos Nombre de la base de datos
	 * @param nombreTabla Nombre de la tabla a la que se van a modificar los datos
	 * @param columnas Nombre de las columnas de los datos que se van a modificar
	 * @param valores Informacion que se va a modificar
	 */
	
	public static void importarUpdate(String nombreBaseDatos, String nombreTabla, String columnas, String valores)
	{
		String columnasSeparadas[] = columnas.split(",");
		String valoresSeparados[] = valores.split(",");
		
		for(int contador = 1; contador < valoresSeparados.length; contador++)
		{
			consulta = "UPDATE "+nombreBaseDatos+"."+nombreTabla+" SET "+columnasSeparadas[contador]+" = "+valoresSeparados[contador]+" WHERE "+columnasSeparadas[0]+" = "+valoresSeparados[0];
			System.out.println(consulta);
			try
			{
				PreparedStatement insert = conn.prepareStatement(consulta);
				
				int rs = insert.executeUpdate();
				
				
			}
			catch(SQLException ex)
			{	
				System.out.println("\n"+ex+"\n\t  -->  Error al actualizar los datos de la base de datos '"+nombreBaseDatos+"'\n");
			}
		}
		
	}
	
	/**
	 * Metodo que desde la importacion un archivo, se borran registros de un tabla de una base de datos
	 * @param nombreBaseDatos
	 * @param nombreTabla
	 * @param columna
	 * @param valores
	 */
	
	public static void importarDelete(String nombreBaseDatos, String nombreTabla, String columna, String valores)
	{
		
		String valoresSeparados[] = valores.split(",");
		
		for(int contador = 0; contador < valoresSeparados.length; contador++)
		{
			consulta = "DELETE FROM "+nombreBaseDatos+"."+nombreTabla+" WHERE "+columna+" = "+valoresSeparados[contador];

			
			try
			{
				PreparedStatement insert = conn.prepareStatement(consulta);
				
				int rs = insert.executeUpdate();
				
				
			}
			catch(SQLException ex)
			{	
				System.out.println("\n"+ex+"\n\t  -->  Error al borrar registros de la base de datos '"+nombreBaseDatos+"'\n");
			}
		}
		
	}
	
	
	/**
	 * Metodo que lista los Procedimientos almacenados que tenga una base de datos
	 * @return Devuelve los nombres de los procedimientos almacenados y los tipos de datos necesitan para ejecutarse
	 */
	
	public static String verProcesosAlmacenados()
	{
		
		consulta = "SELECT name, param_list FROM mysql.proc WHERE Db = '"+DB_NAME+"' AND type = 'PROCEDURE'";
		
		String resultado = "Nombre \t\tParametros\n";
		
		try
		{
			PreparedStatement verProc = conn.prepareStatement(consulta);
			
			ResultSet rs = verProc.executeQuery();
			
			while(rs.next())
			{
				resultado += rs.getString("name")+" \t"+rs.getString("param_list")+"\n";
			}
			
			return resultado;
		}
		
		catch(SQLException ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al mostrar los procedimientos almacenados\n");
		}
		
		return null;
	}
	
	
	/**
	 * Metodo que comprueba si el procedimiento almacenado pasado por parametro, tiene parametros o no
	 * @param nombreProcedimiento Nombre del procedimiento que se va a comprobar
	 * @return Devuelve un booleano 'true' si el procedimiento no necesita parametros, booleano 'false' si el procedimiento necesita argumentos
	 */
	
	public static boolean comprobarProcedimiento(String nombreProcedimiento)
	{
		consulta = "SELECT name, param_list FROM mysql.proc WHERE Db = '"+DB_NAME+"' AND name = '"+nombreProcedimiento+"'";
		
		String resultado = "";
		
		try
		{
			PreparedStatement verProc = conn.prepareStatement(consulta);
			
			ResultSet rs = verProc.executeQuery();
			
			while(rs.next())
			{
				resultado += rs.getString("param_list");
			}
		}
		
		catch(SQLException ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al comprobar el procedimiento almacenado\n");
		}
		
		if(resultado == "")
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * Devuelve el resultado del procedimiento almacenado pasado por parametro en forma de lista
	 * @param nombreProcedimiento Nombre del procedimiento almacenado que se quiere consultar
	 * @return Devuelve el resultado del procedimiento almacenado en un String formateado.
	 */
	
	public static String procedimiento(String nombreProcedimiento)
	{
		if(comprobarProcedimiento(nombreProcedimiento) == true)
		{
			try
			{
				consulta = "{CALL "+nombreProcedimiento+"}";
				
				CallableStatement proc2 = conn.prepareCall(consulta);
				
				ResultSet rs2 = proc2.executeQuery();
				
				int limite = columnasProc(rs2);
				
				rs2.close();
				
				
				CallableStatement call = conn.prepareCall(consulta);
				
				ResultSet rs = call.executeQuery();
				
				String resultadoFinal = "";
				
				while(rs.next())
				{
					int contador = 1;
					
					while(contador <= limite)
					{
						String tupla = (rs.getString(contador));
						resultadoFinal += tupla+"\t";
						
						contador++;
					}
					
					contador = 1;
					
					resultadoFinal += "\n";
				}
				rs.close();
				
				return resultadoFinal;
				
				
			}
			catch(SQLException ex)
			{
				System.out.println("\n"+ex+"\n\t  -->  Error al ejecutar el procedimiento almacenado(Sin parametros)\n");
			}
		}
		
		else
		{
			try
			{
				Scanner ent = new Scanner (System.in);
				
				consulta = "SELECT name, param_list FROM mysql.proc WHERE Db = '"+DB_NAME+"' AND name = '"+nombreProcedimiento+"'";
				
				PreparedStatement miSentencia = conn.prepareStatement(consulta);
				
				ResultSet miResultado = miSentencia.executeQuery();

				String procedimientos = "";
				
				String consultaCall = "{CALL "+nombreProcedimiento+"(";
				
				while(miResultado.next())
				{
					procedimientos = miResultado.getString("param_list");
				}
				
				miResultado.close();
				
				String[] separarProc = procedimientos.split(", ");
				for(int contador = 0; contador < separarProc.length; contador ++)
				{
					System.out.println("Introduzca un dato llamado: "+separarProc[contador]);
					
					String pedir = ent.nextLine();
					
					if(contador == separarProc.length-1)
					{
						consultaCall += "'"+pedir+"')";
					}
					else
					{
						consultaCall += "'"+pedir+"',"; 
					}
				}
				
				
				consulta = consultaCall+"}";
				
				CallableStatement proc = conn.prepareCall(consulta);
				
				CallableStatement proc2 = conn.prepareCall(consulta);
				
				ResultSet rs2 = proc2.executeQuery();
				
				int limite = columnasProc(rs2);
				
				rs2.close();
				
				ResultSet rs = proc.executeQuery();
				
				
				String resultadoFinal = "";
				
				
				while(rs.next())
				{
					int contador = 1;
					
					while(contador <= limite)
					{
						String tupla = (rs.getString(contador));
						resultadoFinal += tupla+"\t";
						
						contador++;
					}
					
					
					resultadoFinal += "\n";
				}
				rs.close();
				
				return resultadoFinal;
				
			}
			
			catch(SQLException ex)
			{
				System.out.println("\n"+ex+"\n\t  -->  Error al ejecutar el procedimiento almacenado(con parametros)\n");
			}
		}
		
		return null;
	}
	
	
	/**
	 * Metodo para saber cuantas columnas devuelve el procedimiento almacenado
	 * @param rs ResultSet del procedimiento almacenado del que se quiere saber cuantas columnas tiene
	 * @return Devuelve el numero de columnas que tiene.
	 */
	
	public static int columnasProc(ResultSet rs)
	{
		int contador = 0;
		try
		{
			
			contador = rs.getMetaData().getColumnCount();
			
		}
		catch (SQLException ex)
		{
			System.out.println("\n"+ex+"\n\t  -->  Error al contar las columnas del procedimiento almacenado\n");
		}
		
		return contador;
	}
	
}
