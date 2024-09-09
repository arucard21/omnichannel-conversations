package omnichannel_conversations.visit.entity;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import omnichannel_conversations.visit.Visitor;

@Entity
public class VisitEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String reason;
	private int amountOfVisitors;
//	private ZonedDateTime cancelationDate;
//	private List<Visitor> visitors;
//	private List<URI> host;
//	private List<URI> assigned;

	public VisitEntity() {}

	public VisitEntity(Long id, String reason, int amountOfVisitors, ZonedDateTime cancelationDate,
			List<Visitor> visitors, List<URI> host, List<URI> assigned) {
		this.id = id;
		this.reason = reason;
		this.amountOfVisitors = amountOfVisitors;
//		this.cancelationDate = cancelationDate;
//		this.visitors = visitors;
//		this.host = host;
//		this.assigned = assigned;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getAmountOfVisitors() {
		return amountOfVisitors;
	}
	public void setAmountOfVisitors(int amountOfVisitors) {
		this.amountOfVisitors = amountOfVisitors;
	}
//	public ZonedDateTime getCancelationDate() {
//		return cancelationDate;
//	}
//	public void setCancelationDate(ZonedDateTime cancelationDate) {
//		this.cancelationDate = cancelationDate;
//	}
//	public List<Visitor> getVisitors() {
//		return visitors;
//	}
//	public void setVisitors(List<Visitor> visitors) {
//		this.visitors = visitors;
//	}
//	public List<URI> getHost() {
//		return host;
//	}
//	public void setHost(List<URI> host) {
//		this.host = host;
//	}
//	public List<URI> getAssigned() {
//		return assigned;
//	}
//	public void setAssigned(List<URI> assigned) {
//		this.assigned = assigned;
//	}
}
