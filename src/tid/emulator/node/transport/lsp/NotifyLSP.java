package tid.emulator.node.transport.lsp;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

import tid.emulator.node.transport.lsp.te.LSPTE;
import tid.pce.pcep.constructs.Path;
import tid.pce.pcep.constructs.StateReport;
import tid.pce.pcep.messages.PCEPReport;
import tid.pce.pcep.objects.Bandwidth;
import tid.pce.pcep.objects.ExplicitRouteObject;
import tid.pce.pcep.objects.LSP;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.SRP;
import tid.pce.pcep.objects.tlvs.LSPDatabaseVersionTLV;
import tid.pce.pcep.objects.tlvs.LSPIdentifiersTLV;
import tid.pce.pcep.objects.tlvs.SymbolicPathNameTLV;


/**
 * Clase que envia un PCEPReport cuando se le dice. Lo suyo seria en el futuro
 * poner los mensajes que se quieren enviar en una cola, y que haya un proceso que de vez en cuando
 * mire lo que hay en la cola y lo envie todo, de esta forma se pueden notificar varios LSPs en un
 * mismo mensaje
 * 
 * 
 * @author jaume
 *
 */

public class NotifyLSP 
{		
	LSPManager lspManager;
	
	Logger log;
	
	public NotifyLSP(LSPManager lspManager) 
	{
		this.lspManager = lspManager;
		log = lspManager.getLog();
	}
	
	public void notify(LSPTE lspte, boolean operational, boolean dFlag, boolean rFlag, boolean rSync) 
	{
		
		if (lspte == null)
		{
			lspManager.getLog().info("lspte is NULL!!");
			return;
			
		}
		
		lspte.setDelegated(true);
		lspte.setDelegatedAdress(lspManager.getPCESession().getPeerPCE_IPaddress());
						
		PCEPReport m_report = new PCEPReport();
		StateReport state_report = new StateReport();
		
		SRP rsp = new SRP();
		
		/* Reserved value because we are not responding to an update */
		rsp.setSRP_ID_number(0);
		
		SymbolicPathNameTLV symPathName= new SymbolicPathNameTLV();
		
		symPathName.setSymbolicPathNameID(ByteBuffer.allocate(8).putLong(lspManager.getNextSymbolicPatheIdentifier()).array());
		rsp.setSymPathName(symPathName);
		
		
		LSP lsp = new LSP();
		
		/* LSP is active */
		lsp.setaFlag(true);
		/* Delegate the LSP*/
		lsp.setdFlag(dFlag);
		/* No sync */
		lsp.setsFlag(rSync);
		/* LSP has been removed */
		lsp.setrFlag(rFlag);
		
		/* Is LSP operational? */
		lsp.setOpFlags(ObjectParameters.LSP_OPERATIONAL_UP);
		
		lsp.setLspId(lspte.getIdLSP().intValue());
		
		LSPIdentifiersTLV lspIdTLV = new LSPIdentifiersTLV();

		lspIdTLV.setTunnelID((int)lspte.getTunnelId());
		lspIdTLV.setTunnelSenderIPAddress(lspte.getIdSource());   	 
		lspIdTLV.setExtendedTunnelID(0);
		
		log.info("Address: "+ lspIdTLV.getTunnelSenderIPAddress());
		log.info("lspID: "+ lsp.getLspId());
		 
		lsp.setLspIdentifiers_tlv(lspIdTLV);
		 
		
		SymbolicPathNameTLV symbPathName = new SymbolicPathNameTLV();
		/* This id should be unique within the PCC */
		symbPathName.setSymbolicPathNameID(ByteBuffer.allocate(8).putLong(lsp.getLspId()).array());
		lsp.setSymbolicPathNameTLV_tlv(symbPathName);
		 
		 

		LSPDatabaseVersionTLV lspdDTLV = new LSPDatabaseVersionTLV();
		/* A change has been made so the database version is aumented */
		lspdDTLV.setLSPStateDBVersion(lspManager.getDataBaseVersion());
		
		state_report.setLSP(lsp);
		state_report.setRSP(rsp);
		 
		/* Set the path */
		Path path = new Path();
		
		ExplicitRouteObject auxERO = new ExplicitRouteObject();
		auxERO.setEROSubobjectList(lspte.getEro().getEroSubobjects());
		
		path.seteRO(auxERO);
		
		Bandwidth bw = new Bandwidth();
		
		bw.setBw(lspte.getBw());
		bw.setReoptimization(false);
		
		path.setBandwidth(bw);
		
		/* For the time being no metrics are added to the path object*/
		
		state_report.setPath(path);
		
		m_report.addStateReport(state_report);	
		log.info("Sending PCEPReport message");
		//lspManager.getPCESession().sendPCEPMessage(m_report);
		lspManager.getFastSession().sendPCEPMessage(m_report);
	
	}
}