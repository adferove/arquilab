package co.arquilab.generals;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
	static String[] asCorreoDestino;
	
	
	//	private final static String CORREO_REMITE = "sistemaacademico@uis.edu.co";
	//	private final static String CLAVE_CORREO_REMITE = "s1au1S37";
	//	private final static String NOMBRE_SERVIDOR = "aplicaciones.uis.edu.co";
	//	private final static String USUARIO = "sistemaacademico";
	//	private final static int    PUERTO = 25;

	private final static String CORREO_REMITE = "web@arquilab.co";
	private final static String CLAVE_CORREO_REMITE = "aRQuiLaB2017";
	private final static String NOMBRE_SERVIDOR = "smtp.zoho.com";
	private final static String USUARIO = "web@arquilab.co";
	private final static int    PUERTO = 587;
	
	public static boolean sendEmail( String sAsunto, String sTexto, String footer,String sDestinatario[]){
		try	{

			Properties properties = new Properties();
			properties.setProperty("mail.smtp.host", NOMBRE_SERVIDOR);
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.port", String.valueOf(PUERTO));
			properties.setProperty("mail.smtp.user", USUARIO);
			properties.setProperty("mail.smtp.auth", "true");


			Session mailSesion = Session.getDefaultInstance(properties);
			mailSesion.setDebug(false);
			Message msg = new MimeMessage(mailSesion);

			msg.setFrom ( new InternetAddress( CORREO_REMITE ) );
			msg.setSubject ( sAsunto );
			msg.setSentDate ( new java.util.Date() );

			MimeMultipart multiParte = new MimeMultipart();
			
			sTexto += footer;

			//Agrega el texto del correo
			BodyPart texto = new MimeBodyPart();
			texto.setContent(sTexto, "text/html");
			multiParte.addBodyPart(texto);

			msg.setContent(multiParte);

			asCorreoDestino = sDestinatario;

			InternetAddress address[] = new InternetAddress[asCorreoDestino.length];
			for( int i = 0; i < asCorreoDestino.length; i++ ) {
				address[i] = new InternetAddress ( asCorreoDestino[i] );
			}         

			msg.setRecipients (Message.RecipientType.TO, address);

			Transport transport = mailSesion.getTransport("smtp");
			transport.connect(NOMBRE_SERVIDOR, PUERTO, USUARIO, CLAVE_CORREO_REMITE);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		}
		catch( MessagingException e ){
			return false ;
		}

		return true ;
	}
	
}
