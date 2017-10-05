package co.arquilab.DAO;

import java.sql.CallableStatement;
import java.sql.SQLException;

import co.arquilab.Conexion;
import co.arquilab.IConstants;
import co.arquilab.classes.Contact;

public interface IDAOInsert {

	
	public static boolean insertContact(Contact pContact)throws Exception{
		boolean result = false;
		try{
			StringBuilder builder = new StringBuilder();
			builder.append(" INSERT INTO arquilab.contactos(nombre, email, comentario, fecha_crea, telefono) ");
			builder.append(" VALUES (?, ?, ?, ?, ?);                                                         ");
			
			Conexion     conexion = new Conexion();
			CallableStatement cs  = null;
			try{
				cs = conexion.getConnection().prepareCall(builder.toString());
				cs.setString(1, pContact.getName());
				cs.setString(2, pContact.getEmail());
				cs.setString(3, pContact.getComment());
				cs.setDate(4, new java.sql.Date( pContact.getDateCreate().getTime()));
				cs.setString(5, pContact.getPhoneNumber());
				
				int val = cs.executeUpdate();
				
				if(val > 0){
					result = true;
				}
				
			}catch(SQLException sq){
				IConstants.log.error(sq.toString(),sq);
			}finally {
				conexion.cerrarConexion();
				cs.close();
			}
			
		}catch(Exception e){
			throw new Exception(e);
		}
		return result;
	}
	
}
