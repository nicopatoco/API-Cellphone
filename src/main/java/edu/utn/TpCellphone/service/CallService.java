package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.dto.CallAddDto;
import edu.utn.TpCellphone.exceptions.CellphoneUnavailableException;
import edu.utn.TpCellphone.model.Call;
import edu.utn.TpCellphone.model.Cellphone;
import edu.utn.TpCellphone.repository.CallRepository;
import edu.utn.TpCellphone.repository.CellphoneRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {
    
    private final CallRepository CALL_REPOSITORY;
    private final CellphoneService CELLPHONE_SERVICE;
    
    public CallService(CallRepository callRepository, CellphoneService cellphoneService) {
        this.CALL_REPOSITORY = callRepository;
        this.CELLPHONE_SERVICE = cellphoneService;
    }
    
    public Optional<Call> getById(Integer idCall) {
        return CALL_REPOSITORY.findById(idCall);
    }
    
    public List<Call> getAll() {
        return CALL_REPOSITORY.findAll();
    }
    
    public void addCall(CallAddDto callDto) throws SQLException, CellphoneUnavailableException {
        if(CELLPHONE_SERVICE.isAvailable(callDto.getNumberOrigin()) && CELLPHONE_SERVICE.isAvailable(callDto.getNumberDestination()) ) {
            CALL_REPOSITORY.addCall(callDto.getNumberOrigin(), callDto.getNumberDestination(), callDto.getStartTime(), callDto.getEndTime());
        } else {
            throw new CellphoneUnavailableException();
        }
    }
}
