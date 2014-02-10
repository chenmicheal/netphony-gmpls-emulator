package tid.emulator.pccPrueba;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.logging.Logger;

import tid.emulator.node.transport.lsp.LSPManager;
import tid.pce.client.ClientRequestManager;
import tid.pce.client.emulator.AutomaticTesterStatistics;
import tid.pce.pcepsession.GenericPCEPSession;
import tid.pce.pcepsession.PCEPSessionsInformation;
import tid.pce.pcepsession.PCEPValues;

public class RemoteLSPInitPCEPSessionServer extends GenericPCEPSession {
	
	private Logger log;
	private AutomaticTesterStatistics stats;
	private ClientRequestManagerPrueba crm;
	private Socket socket;
	
	private String peerNode_IPaddress;
	private boolean no_delay;
	private PCEPSessionsInformation pcepSessionManager;
	
	public RemoteLSPInitPCEPSessionServer(String ip, boolean no_delay, PCEPSessionsInformation pcepSessionManager,
			ClientRequestManagerPrueba crm) {
		super(pcepSessionManager);
		this.setFSMstate(PCEPValues.PCEP_STATE_IDLE);
		log=Logger.getLogger("PCCClient");
		this.peerNode_IPaddress=ip;
		this.crm= crm;
		this.keepAliveLocal=30;
		this.deadTimerLocal=120;
		timer=new Timer();
		this.no_delay=no_delay;
		this.pcepSessionManager=pcepSessionManager;
	}

	@Override
	public void run() {
		socket = null;
		boolean listening=true;
		
		try {
			while (listening){
				log.info("New PCEP Session listening on 2222!");
		       	//new RemoteLSPInitPCEPSession(socket, pcepSessionManager, crm).start();	
				this.sleep(1000);
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}

	@Override
	protected void endSession() {
		// TODO Auto-generated method stub
		
	}
}