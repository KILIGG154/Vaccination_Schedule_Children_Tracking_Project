package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccine;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.ComboStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.VaccineMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.*;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;


@Slf4j
@Service
public class VaccineService {

    @Autowired
    private VaccineRepo vaccineRepo;

    @Autowired
    private VaccineComboRepo vaccineCombos;

    @Autowired
    private VaccineComboDetailRepo vaccineComboDetail;

    @Autowired
    private VaccineMapper vaccineMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VaccineCategoryRepo VCRepo;


    @Transactional
    public VaccineCategory addVaccineCategory(VaccineCategoryRequest request) {
        return VCRepo.save(vaccineMapper.toCreateVaccineCategory(request));
    }

    public List<VaccineCategory> getAllCategories() {
        return VCRepo.findAll();
    }

    @Transactional
    public Vaccine addVaccine(VaccineRequest request, Long categoryId) {

        VaccineCategory category = VCRepo.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Vaccine vaccine = vaccineMapper.toCreateVaccine(request);
        vaccine.setStatus(VaccineStatus.ACTIVE);
        vaccine.setCategoryId(category);


        return vaccineRepo.save(vaccine);
    }

    public List<ResponseVaccine> getAllVaccines() {
//        List<ResponseVaccine> vaccines = vaccineMapper.toResponseVaccine(vaccineRepo.findAll());
        return vaccineMapper.toResponseVaccineList(vaccineRepo.findAll());
    }
    
    public ResponseVaccine getVaccineById(int id) {
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));
        return vaccineMapper.toResponseVaccine(vaccine);
    }

    
    public List<ResponseVaccine> searchByName(String vaccineName) {
        List<Vaccine> vaccine = vaccineRepo.findByNameContainingIgnoreCase(vaccineName);
        return vaccineMapper.toResponseVaccineList(vaccine);
    }

    @Transactional
    public VaccineCombo addVaccineCombo(VaccineComboRequest request) {
        VaccineCombo vaccineCombo = new VaccineCombo();
        vaccineCombo.setComboName(request.getComboName());
        vaccineCombo.setDescription(request.getDescription());
        vaccineCombo.setComboCategory(request.getComboCategory());
        vaccineCombo.setSaleOff(request.getSaleOff());
        vaccineCombo.setDose(request.getDose());
        vaccineCombo.setStatus(ComboStatus.AVAILABLE);
        return vaccineCombos.save(vaccineCombo);
    }

    @Transactional
    public VaccineComboDetail addVaccineComboDetail(VaccineComboDetailRequest request, int vaccineId, int comboId) {
        // Log để debug
        log.info("Adding VaccineComboDetail for vaccineId={}, comboId={}");

        // Tìm vaccine và VaccineCombo theo ID
        Vaccine vaccine = vaccineRepo.findById(vaccineId)
                .orElseThrow(() -> new RuntimeException("vaccine not found with id: "));

        VaccineCombo vaccineCombo = vaccineCombos.findById(comboId)
                .orElseThrow(() -> new RuntimeException("vaccine Combo not found with id: "));


        // Tạo và cấu hình VaccineComboDetail - THAY ĐỔI Ở ĐÂY
        VaccineComboDetail detail = new VaccineComboDetail();
        detail.setVaccineId(vaccineId);  // Đặt ID trực tiếp thay vì thông qua đối tượng key
        detail.setComboId(comboId);      // Đặt ID trực tiếp thay vì thông qua đối tượng key
        detail.setVaccine(vaccine);
        detail.setCombo(vaccineCombo);
//        detail.setDose(request.getDose());
//        detail.setComboCategory(request.getComboCategory());
//        detail.setSaleOff(request.getSaleOff());

        //Phải lưu thằng Detail này trước, gọi method() tính tổng giá tiền mới được nha!!!
        vaccineComboDetail.save(detail);
        entityManager.flush();  // Đẩy dữ liệu xuống DB trước khi tính tổng

//        log.info("Saving VaccineComboDetail with vaccineId={}, comboId={}", detail.getVaccineId(), detail.getComboId());
        double totalP = getTotalPriceCombo(comboId);

        vaccineCombo.setTotal(totalP);
        vaccineCombos.save(vaccineCombo);

        entityManager.flush();  // Đẩy dữ liệu xuống DB trước khi trả về kết quả
        return detail;
    }

    public List<ResponseVaccineCombo> getVaccineCombos(){
        return vaccineMapper.toResponseVaccineComboList(vaccineCombos.findAll());
    }

    //Tim vaccine combo theo id
    public ComboDTO getVaccineComboById(int id) {
        log.info("Bắt đầu tìm vaccine combo với id: {}", id);
        
        try {
            VaccineCombo vaccineCombo = vaccineCombos.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
            log.info("Đã tìm thấy vaccine combo: {}", vaccineCombo);
            
            List<VaccineComboDetail> details = vaccineComboDetail.findByComboId(id);
            log.info("Số lượng chi tiết combo tìm thấy: {}", details != null ? details.size() : 0);
            
            if (details == null || details.isEmpty()) {
                log.warn("Không tìm thấy chi tiết nào cho combo id: {}", id);
                details = new ArrayList<>();
            }
            
            vaccineCombo.setVaccineComboDetails(details);
            
            ComboDTO result = new ComboDTO(vaccineCombo);
            log.info("Đã tạo ComboDTO thành công: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Lỗi khi tìm vaccine combo với id {}: {}", id, e.getMessage(), e);
            throw new AppException(ErrorCode.INVALID_KEY);
        }
    }


    //Gọi mapper và xử lý việc map các dữ liệu thì cứ để mapper lo, mình lo chơi bời thôi!
    public List<ResponseVaccineDetails> getAllVaccineCombosDetails() {
        return vaccineMapper.toResponseVaccineDetails(vaccineComboDetail.findAll());
    }

// Cái Method này thì chỉ cần trả về total Price mỗi khi có 1 Vaccine được add vào Combo
@Transactional
protected double getTotalPriceCombo(int id) {
    // **Tải lại dữ liệu để lấy danh sách mới nhất**
    VaccineCombo vaccineCombo = vaccineCombos.findById(id)
            .orElseThrow(() -> new RuntimeException("Vaccine Combo not found with id: " + id));

    double totalP = Optional.ofNullable(vaccineCombo.getVaccineComboDetails())
            .orElse(Collections.emptyList()) // Tránh NullPointerException
            .stream()
            .mapToDouble(detail -> detail.getVaccine().getSalePrice() *
             detail.getVaccine().getTotalDose() * (1 - vaccineCombo.getSaleOff()))
            .sum();

    return totalP;
}

    //Protocol Service in Vaccine Service

    @Autowired
    ProtocolRepo protocolRepo;

    @Autowired
    ProtocolDetailRepo protocolDetailRepo;

    @Autowired
    VaccineProtocolDoseRepo vProtocolRepo;

    @Transactional
    public ProtocolResponse addNewProtocol(ProtocolRequest request) {
        // Tạo Protocol
        Protocol protocol = new Protocol();
        protocol.setName(request.getName());
        protocol.setDescription(request.getDescription());

        // Lưu Protocol trước để có ID
        protocol = protocolRepo.save(protocol);

        // Thêm các ProtocolDetail
        for (ProtocolDetailRequest detailRequest : request.getDetails()) {
            ProtocolDetail detail = new ProtocolDetail();
            detail.setProtocol(protocol);
            detail.setDoseNumber(detailRequest.getDoseNumber());
            detail.setIntervalDays(detailRequest.getIntervalDays());

            protocol.addDetail(detail);
        }

        // Lưu lại Protocol với các detail
         protocolRepo.save(protocol);

        return mapToProtocolResponse(protocol);
    }

    public List<ProtocolResponse> getAllProtocols() {
        List<Protocol> protocols = protocolRepo.findAll();
        return protocols.stream()
                .map(this::mapToProtocolResponse)
                .collect(Collectors.toList());
    }


    public ProtocolResponse getProtocolById(Long protocolId) {
        Protocol protocol = protocolRepo.findById(protocolId)
                .orElseThrow(() -> new AppException(ErrorCode.PROTOCOL_NOT_FOUND));
        return mapToProtocolResponse(protocol);
    }


    public ResponseProtocolDose getProtocolDoseByVaccine(int vaccineId) {
        Vaccine vaccine = vaccineRepo.findById(vaccineId)
                .orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));

        List<VaccineProtocolDose> doses = vProtocolRepo.findByVaccineId(vaccineId);

        ResponseProtocolDose response = new ResponseProtocolDose();
        response.setVaccineId(vaccineId);
        response.setVaccineName(vaccine.getName());
        response.setDoseList(doses);

        return response;
    }

    @Transactional
    public List<VaccineProtocolDose> addVaccineToProtocol(Integer vaccineId, Long protocolId) {
        // Tìm vaccine và protocol
        Vaccine vaccine = vaccineRepo.findById(vaccineId)
                .orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));
                
        Protocol protocol = protocolRepo.findById(protocolId)
                .orElseThrow(() -> new AppException(ErrorCode.PROTOCOL_NOT_FOUND));
                
        // Lấy danh sách protocol details, đảm bảo sắp xếp theo doseNumber
        List<ProtocolDetail> protocolDetails = protocolDetailRepo.findByProtocolOrderByDoseNumber(protocol);
        
        // Kiểm tra số liều vaccine và số lượng chi tiết protocol
        int totalDose = vaccine.getTotalDose();
        
        if (totalDose > 5) {
            throw new AppException(ErrorCode.INVALID_DOSE_COUNT);
        }
        
        if (protocolDetails.size() < totalDose) {
            throw new AppException(ErrorCode.PROTOCOL_NOT_FOUND);
        }
        
        // Xóa các VaccineProtocolDose hiện có của vaccine này (nếu có)
        List<VaccineProtocolDose> existingDoses = vProtocolRepo.findByVaccineId(vaccineId);
        if (!existingDoses.isEmpty()) {
            vProtocolRepo.deleteAll(existingDoses);
        }
        
        // Tạo các VaccineProtocolDose mới
        List<VaccineProtocolDose> doses = new ArrayList<>();
        
        for (int i = 0; i < totalDose; i++) {
            ProtocolDetail detail = protocolDetails.get(i);
            
            VaccineProtocolDose dose = new VaccineProtocolDose();
            dose.setVaccine(vaccine);
            dose.setProtocolDetail(detail);
            VaccineProtocolDose savedDose = vProtocolRepo.save(dose);
            doses.add(savedDose);
        }
        
        return doses;
    }

    private ProtocolResponse mapToProtocolResponse(Protocol protocol) {
        ProtocolResponse response = new ProtocolResponse();
        response.setProtocolId(protocol.getProtocolId());
        response.setName(protocol.getName());
        response.setDescription(protocol.getDescription());

        List<ProtocolDetailResponse> detailResponses = protocol.getDetails().stream()
                .map(this::mapToProtocolDetailResponse)
                .collect(Collectors.toList());
        response.setDetails(detailResponses);

        return response;
    }

    private ProtocolDetailResponse mapToProtocolDetailResponse(ProtocolDetail detail) {
        ProtocolDetailResponse response = new ProtocolDetailResponse();
        response.setDetailId(detail.getDetailId());
        response.setDoseNumber(detail.getDoseNumber());
        response.setIntervalDays(detail.getIntervalDays());
        return response;
    }

    public Vaccine updateVaccineNecessary(int id, VaccineUpdate request){
        Vaccine vac = vaccineRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));

        if(request.getQuantity() != 0){
         vac.setQuantity(request.getQuantity());
        }
        if(request.getUnitPrice() != 0){
            vac.setUnitPrice(request.getUnitPrice());
        }
        if(request.getSalePrice() != 0){
            vac.setSalePrice(request.getSalePrice());
        }
        return vaccineRepo.save(vac);

    }

    public Vaccine activeVaccie(int id){
        Vaccine vac = vaccineRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));
        vac.setStatus(VaccineStatus.ACTIVE);
        return vaccineRepo.save(vac);
    }

    public Vaccine deactiveVaccine(int id){
        Vaccine vac = vaccineRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));
        vac.setStatus(VaccineStatus.INACTIVE);
        return vaccineRepo.save(vac);
    }

    public VaccineCombo activeCombo(int id){
        VaccineCombo combo = vaccineCombos.findById(id).orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));
        combo.setStatus(ComboStatus.AVAILABLE);
        return vaccineCombos.save(combo);
    }

    public VaccineCombo deactiveCombo(int id){
        VaccineCombo combo = vaccineCombos.findById(id).orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));
        combo.setStatus(ComboStatus.UNAVAILABLE);
        return vaccineCombos.save(combo);
    }

    public List<ComboDTO> findByCateCombo(String name){
        List<VaccineCombo> combo = vaccineCombos.findVaccineComboByComboCategoryContaining(name);
        if(combo.isEmpty()){
            throw new AppException(ErrorCode.VACCINE_NOT_FOUND);
        }
        List<ComboDTO> comboDTOs = combo.stream()
                .map(ComboDTO::new)
                .collect(Collectors.toList());
        return comboDTOs;
    }


}