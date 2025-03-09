package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.VaccineMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineCombeDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineComboRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineDetails;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.*;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Vaccine addVaccine(VaccineRequest request) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName(request.getName());
        vaccine.setDescription(request.getDescription());
        vaccine.setManufacturer(request.getManufacturer());
        vaccine.setDosage(request.getDosage());
        vaccine.setContraindications(request.getContraindications());
        vaccine.setPrecautions(request.getPrecautions());
        vaccine.setInteractions(request.getInteractions());
        vaccine.setAdverseReactions(request.getAdverseReactions());
        vaccine.setStorageConditions(request.getStorageConditions());
        vaccine.setRecommended(request.getRecommended());
        vaccine.setPreVaccination(request.getPreVaccination());
        vaccine.setCompatibility(request.getCompatibility());
        vaccine.setQuantity(request.getQuantity());
        vaccine.setUnitPrice(request.getUnitPrice());
        vaccine.setSalePrice(request.getSalePrice());
//        vaccine.setExpirationDate(request.getExpirationDate());
        vaccine.setImagineUrl(request.getImagineUrl());
        vaccine.setStatus("true");


        return vaccineRepo.save(vaccine);
    }

    public List<ResponseVaccine> getAllVaccines() {
//        List<ResponseVaccine> vaccines = vaccineMapper.toResponseVaccine(vaccineRepo.findAll());
        return vaccineMapper.toResponseVaccine(vaccineRepo.findAll());
    }

    public List<ResponseVaccine> searchByName(String vaccineName) {
        List<Vaccine> vaccine = vaccineRepo.findByNameContainingIgnoreCase(vaccineName);
        return vaccineMapper.toResponseVaccine(vaccine);
    }

    @Transactional
    public VaccineCombo addVaccineCombo(VaccineComboRequest request) {
        VaccineCombo vaccineCombo = new VaccineCombo();
        vaccineCombo.setComboName(request.getComboName());
        vaccineCombo.setDescription(request.getDescription());
        vaccineCombo.setStatus(true);
        return vaccineCombos.save(vaccineCombo);
    }

    @Transactional
    public VaccineComboDetail addVaccineComboDetail(VaccineCombeDetailRequest request, int vaccineId, int comboId) {
        // Log để debug
        log.info("Adding VaccineComboDetail for vaccineId={}, comboId={}");

        // Tìm Vaccine và VaccineCombo theo ID
        Vaccine vaccine = vaccineRepo.findById(vaccineId)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with id: "));

        VaccineCombo vaccineCombo = vaccineCombos.findById(comboId)
                .orElseThrow(() -> new RuntimeException("Vaccine Combo not found with id: "));

        // Tạo ID cho VaccineComboDetail
//        VaccineComboDetailId key = new VaccineComboDetailId();
//        key.setVaccineId(request.getVaccineId());
//        key.setComboId(request.getComboId());
//
//        log.info("Created composite key: vaccineId={}, comboId={}", key.getVaccineId(), key.getComboId());


        // Tạo và cấu hình VaccineComboDetail - THAY ĐỔI Ở ĐÂY
        VaccineComboDetail detail = new VaccineComboDetail();
        detail.setVaccineId(vaccineId);  // Đặt ID trực tiếp thay vì thông qua đối tượng key
        detail.setComboId(comboId);      // Đặt ID trực tiếp thay vì thông qua đối tượng key
        detail.setVaccine(vaccine);
        detail.setCombo(vaccineCombo);
        detail.setDose(request.getDose());
        detail.setComboCategory(request.getComboCategory());
        detail.setSaleOff(request.getSaleOff());

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
//         // Tạo và  cấu hình VaccineComboDetail
//         VaccineComboDetail detail = new VaccineComboDetail();
//         detail.setId(key);
//         detail.setVaccine(vaccine);
//         detail.setCombo(vaccineCombo);
//         detail.setDose(request.getDose());
//         detail.setAgeGroup(request.getAgeGroup());
//         detail.setSaleOff(request.getSaleOff());

// //        vaccine.getVaccineComboDetails().add(detail);
// //        vaccineCombo.getVaccineComboDetails().add(detail);
// //        log.info("Added VaccineComboDetail to Vaccine and VaccineCombo", detail.getVaccine(), detail.getCombo());


//         // Lưu và trả về kết quả
// //        vaccineRepo.save(vaccine);
// //        vaccineCombos.save(vaccineCombo);
//         return vaccineComboDetail.saveAndFlush(detail);
//     }

//    public List<ResponseVaccineCombo> getVaccineCombos() {
//        List<VaccineCombo> combos = vaccineCombos.findAll();
//
//        // Kiểm tra giá trị total trước khi mapping
////        combos.forEach(combo -> System.out.println("Combo: " + combo.getId() + " | Total: " + combo.getTotal()));
//        //Đúng rồi nên chả cần kiểm tra nữa!!!
//
//        return vaccineMapper.toResponseVaccineCombo(combos);
//    }

    public List<ResponseVaccineCombo> getVaccineCombos(){
        return vaccineMapper.toResponseVaccineCombo(vaccineCombos.findAll());
    }


    //Gọi mapper và xử lý việc map các dữ liệu thì cứ để mapper lo, mình lo chơi bời thôi!
    public List<ResponseVaccineDetails> getAllVaccineCombosDetails() {
        return vaccineMapper.toResponseVaccineDetails(vaccineComboDetail.findAll());
    }

//        List<ResponseVaccineDetails> details = vaccineMapper.toResponseVaccineDetails(vaccineComboDetail.findAll());
//
//        for(ResponseVaccineDetails detail : details){
//            double price = detail.getVaccine().getSalePrice();
//            int doseNumber = detail.getDose();
//            detail.setTotalCombo(price * doseNumber);
//        }
//        return details;
//    }


//    @Transactional
//    private double getTotalPriceCombo(int id){
//        VaccineCombo vaccineCombo = vaccineCombos.findById(id).orElseThrow(() -> new RuntimeException("Vaccine Combo not found with id: " ));
//
//        double tolalP = vaccineCombo.getVaccineComboDetails().stream().mapToDouble(detail -> {
//            Vaccine vaccine = detail.getVaccine(); // Lấy thông tin vaccine
//            double price = vaccine.getSalePrice();
//            int doseNumber = detail.getDose(); // Lấy số mũi từ bảng trung gian
//            return price * doseNumber; // Tính giá tiền
//
//        }).sum();
//
//        return tolalP;
//    }


// Cái Method này thì chỉ cần trả về total Price mỗi khi có 1 Vaccine được add vào Combo
@Transactional
protected double getTotalPriceCombo(int id) {
    // **Tải lại dữ liệu để lấy danh sách mới nhất**
    VaccineCombo vaccineCombo = vaccineCombos.findById(id)
            .orElseThrow(() -> new RuntimeException("Vaccine Combo not found with id: " + id));

    double totalP = Optional.ofNullable(vaccineCombo.getVaccineComboDetails())
            .orElse(Collections.emptySet()) // Tránh NullPointerException
            .stream()
            .mapToDouble(detail -> detail.getVaccine().getSalePrice() * detail.getDose())
            .sum();

    return totalP;
}


}