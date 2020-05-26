package unq.edu.remotetrainer.application.sevice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.mapper.RoutineMapper
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.persistence.entity.RoutineEntity
import unq.edu.remotetrainer.persistence.repository.RoutineRepository

@Service
class RoutineService @Autowired constructor(
    override val repository: RoutineRepository,
    override val mapper: RoutineMapper
): RemoteTrainerService<Routine, RoutineEntity>