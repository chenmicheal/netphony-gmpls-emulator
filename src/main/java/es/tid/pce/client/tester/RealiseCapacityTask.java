package es.tid.pce.client.tester;


import java.util.LinkedList;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.tid.netManager.NetworkLSPManager;
import es.tid.pce.client.emulator.AutomaticTesterStatistics;
import es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth;
import es.tid.rsvp.objects.subobjects.EROSubobject;



public class RealiseCapacityTask  extends TimerTask {

	private Logger log;
	private BandwidthRequestedGeneralizedBandwidth GB;
	private AutomaticTesterStatistics stats;
	private LinkedList<EROSubobject> erolist;
	private NetworkLSPManager networkLSPManager;
	private boolean bidirectional; 
	private float bw;
	private boolean VirtualTELink=false;
	
	public RealiseCapacityTask(NetworkLSPManager networkLSPManager, LinkedList<EROSubobject> erolist,AutomaticTesterStatistics stats, boolean bidirectional, BandwidthRequestedGeneralizedBandwidth GB){
		log=LoggerFactory.getLogger("PCCClient");
		this.stats=stats;
		this.erolist=erolist;
		this.GB=GB;
		this.networkLSPManager=networkLSPManager;
		this.bidirectional=bidirectional;

	}
	
	public RealiseCapacityTask(NetworkLSPManager networkLSPManager, LinkedList<EROSubobject> erolist,AutomaticTesterStatistics stats, boolean bidirectional, BandwidthRequestedGeneralizedBandwidth GB,
			float bw, boolean VirtualTELink){
		log=LoggerFactory.getLogger("PCCClient");
		this.stats=stats;
		this.erolist=erolist;
		this.GB=GB;
		this.networkLSPManager=networkLSPManager;
		this.bidirectional=bidirectional;
		this.bw=bw;
		this.VirtualTELink=VirtualTELink;
	}
	
	@Override
	public void run() {
		log.info("Deleting LSP, releasing capacity "+erolist.toString());
		if (stats != null)
			stats.releaseNumberActiveLSP();
		if (VirtualTELink)
			networkLSPManager.removeLSP(erolist,bidirectional,GB,bw);
		else
			networkLSPManager.removeLSP(erolist,bidirectional,GB);

	}//End run
}
