package co.arquilab.businessLogic.contact;

import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.arquilab.ConsultarFuncionesAPI;
import co.arquilab.IConstants;
import co.arquilab.DAO.IDAOInsert;
import co.arquilab.classes.Contact;
import co.arquilab.generals.EmailSender;

@ManagedBean
@ViewScoped
public class ManageContact extends ConsultarFuncionesAPI{

	private static final long serialVersionUID = 6097327266445458490L;
	
	private Contact contact;

	public Contact getContact() {
		if(this.contact==null){
			this.contact = new Contact();
		}
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public void sendContact(){
		try{
			boolean error = false;

			if(this.contact.getName()==null || this.contact.getName().trim().equals("")){
				error = true;
			}

			if(this.contact.getEmail()==null || this.contact.getEmail().trim().equals("")){
				error = true;
			}

			if(this.contact.getComment()==null || this.contact.getComment().trim().equals("")){
				error = true;
			}

			if(this.contact.getPhoneNumber()==null || this.contact.getPhoneNumber().trim().equals("")){
				error = true;
			}

			if(!error){
				this.contact.setDateCreate(Calendar.getInstance().getTime());
				if(IDAOInsert.insertContact(this.contact)){
					String[] email = {"ventas@arquilab.co"};
					String[] emailBody = {this.contact.getName(),this.contact.getEmail(),this.contact.getPhoneNumber(),this.contact.getComment()};
					EmailSender.sendEmail(this.getMensaje("asunto"), this.getMensajeParametros("emailBody", emailBody), this.getMensaje("emailFooter"), email);
					this.contact = new Contact();
					this.mostrarMensajeGlobal("sentContact", "exito");

				}
			}else{
				this.mostrarMensajeGlobal("allFieldsAreObligatory", "error");
			}
		}catch(Exception e){
			IConstants.log.error(e.toString(),e);
		}
	}

}
