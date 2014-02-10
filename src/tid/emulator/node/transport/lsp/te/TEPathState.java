package tid.emulator.node.transport.lsp.te;

import tid.rsvp.constructs.SenderDescriptor;
import tid.rsvp.objects.ERO;
import tid.rsvp.objects.LabelRequest;
import tid.rsvp.objects.PolicyData;
import tid.rsvp.objects.RSVPHop;
import tid.rsvp.objects.Session;
import tid.rsvp.objects.SessionAttribute;
import tid.rsvp.objects.TimeValues;
import tid.emulator.node.transport.lsp.PathState;

/**
 * TE Path State class implements the complete path state of an RSVP-TE path.
 * @author Fernando Mu�oz del Nuevo fmn@tid.es
 */

public class TEPathState extends PathState {

	private Session session;
	private RSVPHop previousHop;
	private TimeValues timeValues;
	private ERO explicitRoute;
	private LabelRequest labelRequest;
	private SessionAttribute sessionAttribute;
	private PolicyData policyData;
	private SenderDescriptor senderDescriptor;
		
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public RSVPHop getPreviousHop() {
		return previousHop;
	}
	public void setPreviousHop(RSVPHop previousHop) {
		this.previousHop = previousHop;
	}
	public TimeValues getTimeValues() {
		return timeValues;
	}
	public void setTimeValues(TimeValues timeValues) {
		this.timeValues = timeValues;
	}
	public ERO getExplicitRoute() {
		return explicitRoute;
	}
	public void setExplicitRoute(ERO explicitRoute) {
		this.explicitRoute = explicitRoute;
	}
	public LabelRequest getLabelRequest() {
		return labelRequest;
	}
	public void setLabelRequest(LabelRequest labelRequest) {
		this.labelRequest = labelRequest;
	}
	public SessionAttribute getSessionAttribute() {
		return sessionAttribute;
	}
	public void setSessionAttribute(SessionAttribute sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}
	public PolicyData getPolicyData() {
		return policyData;
	}
	public void setPolicyData(PolicyData policyData) {
		this.policyData = policyData;
	}
	public SenderDescriptor getSenderDescriptor() {
		return senderDescriptor;
	}
	public void setSenderDescriptor(SenderDescriptor senderDescriptor) {
		this.senderDescriptor = senderDescriptor;
	}
	
	
	
}