package es.tid.emulator.node;

import java.net.Inet4Address;
import java.net.ServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.tid.emulator.node.transport.lsp.LSPManager;
import es.tid.pce.client.emulator.AutomaticTesterStatistics;
import es.tid.pce.pcepsession.PCEPSessionsInformation;

public class RemoteLSPInitPCEPSessionServer implements Runnable {
	
	private Logger log;
	private Inet4Address idRoadm;
	private LSPManager lspManager;
	private int nodeTechnology;
	private AutomaticTesterStatistics stats;
	private boolean isStateful;
	
	public RemoteLSPInitPCEPSessionServer(LSPManager lspManager, Inet4Address idRoadm, int nodeTechnology, boolean isStateful)
	{
		log=LoggerFactory.getLogger("ROADM");
		this.idRoadm=idRoadm;
		this.lspManager=lspManager;
		this.nodeTechnology=nodeTechnology;
		this.isStateful = isStateful;
	}

	@Override
	public void run() {
		    ServerSocket serverSocket = null;
		    boolean listening=true;
			try {
		      	  log.info("Listening PCEP on port 4189");	
		          serverSocket = new ServerSocket(4189);
			}
			catch (Exception e){
				log.error("Could not listen fast PCEP on port 4189");
				e.printStackTrace();
				return;
			}
			try {
		       	while (listening) {
		       		//Socket s, LSPManager lspManager, Inet4Address idRoadm, PCEPSessionsInformation pcepSessionManager)
		       		log.info("New PCEP Session Open with Client!");
		       		PCEPSessionsInformation pceSessionInf = new PCEPSessionsInformation();
		       		pceSessionInf.setStateful(isStateful);
		       		log.info("Session is stateful ? :"+ isStateful);
		       		new RemoteLSPInitPCEPSession(serverSocket.accept(), lspManager, idRoadm, pceSessionInf).start();	
		       	}
		       	serverSocket.close();
		    } catch (Exception e) {
		       	e.printStackTrace();
		    }				

	}

}
