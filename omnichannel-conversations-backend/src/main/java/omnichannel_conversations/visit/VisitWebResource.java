package omnichannel_conversations.visit;

import java.net.URI;
import java.util.List;

import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import omnichannel_conversations.visit.entity.VisitEntity;

@Path("visits")
@RequestScoped
@OpenAPIDefinition(info = @Info(title = "Visit endpoint", version = "1.0"))
public class VisitWebResource{
	@Inject
	VisitRepository repo;
	@Context
	UriInfo uriInfo;

	@Transactional
	public void ensureNonEmptyDatabase() {
		try {
			long databaseSize = repo.count();
			if(databaseSize == 0) {
				for(int i = 0; i < 5; i++) {
					VisitEntity entity = new VisitEntity(null, "some persisted reason", 2+i, null, null, null, null);
					repo.save(entity);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Retrieve a list of all visits") })
	@Transactional
	@Timed
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Visit> getVisits(){
		// must check this here since persistence cannot be used in the constructor or @PostConstruct method.
		// This is not something that would not be needed when using a database normally.
		ensureNonEmptyDatabase();
		return repo.findAll().stream()
				.map(entity -> toApiResource(uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build(), entity))
				.toList();
	}

	@APIResponses(value = { @APIResponse(responseCode = "201", description = "Create a new visit") })
	@Transactional
	@Timed
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createVisit(Visit visit){
		VisitEntity created = repo.save(toEntity(null, visit));
		return Response.created(uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build()).build();
	}

	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Retrieve a single visit") })
	@Transactional
	@Timed
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Visit getVisit(@PathParam("id") Long id){
		return toApiResource(uriInfo.getAbsolutePath(), repo.findById(id).orElseThrow(NotFoundException::new));
	}

	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Update a visit") })
	@Transactional
	@Timed
	@Path("{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Visit updateVisit(@PathParam("id") Long id, Visit visit){
		VisitEntity persisted = repo.findById(id).orElseThrow(NotFoundException::new);
		persisted.setAmountOfVisitors(visit.amountOfVisitors());
		persisted.setReason(visit.reason());
		return toApiResource(uriInfo.getAbsolutePath(), persisted);

	}
	@APIResponses(value = { @APIResponse(responseCode = "204", description = "Delete a visit") })
	@Transactional
	@Timed
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteVisit(@PathParam("id") Long id){
		repo.deleteById(id);
	}

	private Visit toApiResource(URI self, VisitEntity entity) {
		return new Visit(self, entity.getReason(), entity.getAmountOfVisitors(), null, null, null, null);
	}

	private VisitEntity toEntity(Long id, Visit apiResource) {
		return new VisitEntity(id, apiResource.reason(), apiResource.amountOfVisitors(), null, null, null, null);
	}
}
