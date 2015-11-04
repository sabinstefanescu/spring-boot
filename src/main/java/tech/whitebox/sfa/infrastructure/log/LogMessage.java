/**
 * 
 */
package tech.whitebox.sfa.infrastructure.log;

/**
 * @author razvan.reff
 *
 */
public class LogMessage {
	
	public Integer userId;
	public String logAction;
	public String logComments;
	public String className;
	
	
	public LogMessage(Integer userId, String logAction, String logComments,
			String className) {
		super();
		
		this.userId = userId;
		this.logAction = logAction;
		this.logComments = logAction + ":" + logComments;
		this.className = className;
	}


	public String toString() {
		return userId + " [" + logAction + "] " + logComments;
	}

	
}
