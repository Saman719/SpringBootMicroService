package com.example.SpringProject.repository;

import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.model.CardPrintRequestPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CardPrintRequestRepository extends JpaRepository<CardPrintRequest, CardPrintRequestPK>, CustomCardPrintRequestRepository {


    List<CardPrintRequest> findCardPrintRequestsByCardPrintRequestPK_IpAddress(String ipaddress);

    @Modifying
    @Transactional
    List<CardPrintRequest> deleteAllByCardPAN(String cardPAN);

    //Pagination
    Page<CardPrintRequest> findAllByCardPrintRequestPK_BranchCode(String branchCode, Pageable pageable);


    //native pageable query
    @Query(value = "SELECT * FROM t_cardprintrequest where c_personnelcode = :personnelcode",
    countQuery = "SELECT COUNT(*) FROM t_cardprintrequest",
    nativeQuery = true)
    Page<CardPrintRequest> getCardPrintRequestByPersonnelCode(@Param("personnelcode") String personnelCode, Pageable pageable);


    //Transactional
    @Modifying
    @Transactional
    @Query(value = "update CardPrintRequest c set c.cardPAN = :cardpan where c.cardPrintRequestPK.branchCode = :branchcode" )
    void updatePanNumberByBranchCode(@Param("cardpan") String cardPan, @Param("branchcode") String branchCode);

}
