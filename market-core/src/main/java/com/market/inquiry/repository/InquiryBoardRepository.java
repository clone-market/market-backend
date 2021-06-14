package com.market.inquiry.repository;

import com.market.inquiry.entity.InquiryBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryBoardRepository extends JpaRepository<InquiryBoard, Long> {
}
