
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author Daniel Hurtado Perera
 */
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA CLIENTES
    //////////////////////////////////////////////////
    
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
    		ex.printStackTrace();
    	}
    }
    
    
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
    		ex.printStackTrace();
    	}
    	
    	
    }
    
    
    public static void printContenidoTabla(String tabla)
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
    			
    			System.out.print(nombreTabla + "\t");
    		}
    	}
    	catch(SQLException ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	System.out.println();
    }
    
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
    			System.out.println(nombreTabla + "\t" + tipoDato);
    		}
    	}
    	catch(SQLException ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	System.out.println();
    }
    
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
    		ex.printStackTrace();
    	}
    }
    
    public static void printSelect(String tabla)
    {
    	
    	printContenidoTabla(tabla);
    	
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
    				System.out.print(tupla+"\t");
    			}
    			System.out.println();
    		}
    		
    	}
    	catch(SQLException ex)
    	{
    		ex.printStackTrace();
    	}
    }
    
    

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
						System.out.println("Nombre del campo "+contador);
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
					catch(Exception ex)
					{
						ent.nextLine();
						ex.printStackTrace();
						contador -= 1;
					}
				}
			
			
			
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
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
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

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
			
		}
	}
	
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
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
