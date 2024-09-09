package omnichannel_conversations.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.enterprise.context.ApplicationScoped;
import omnichannel_conversations.visit.entity.VisitEntity;

@ApplicationScoped
@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long> {}
